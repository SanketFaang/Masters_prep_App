package com.jhainusa.testsxperts.uii

import QuestionAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jhainusa.testsxperts.Question
import com.jhainusa.testsxperts.R
class TestsGivenFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: Button
    private lateinit var submitButton: Button
    private lateinit var prevButton: Button
    private val questionList = mutableListOf<Question>()
    private val userAnswers = mutableMapOf<Int, String>() // Track user answers
    private var currentPosition = 0 // Track current question position

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tests_given, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        nextButton = view.findViewById(R.id.nextButton)
        submitButton = view.findViewById(R.id.submitButton)
        prevButton = view.findViewById(R.id.prevButton)
        val data = arguments?.getString("key")
        fetchQuestions(data!!)


        nextButton.setOnClickListener {
            goToNextPage()
        }

        prevButton.setOnClickListener {
            goToPrevPage()
        }

        submitButton.setOnClickListener {
            submitExam()
        }

        return view
    }

    private fun fetchQuestions(data:String) {
        val db = FirebaseFirestore.getInstance()
        db.collection(data)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question = document.toObject<Question>()
                    questionList.add(question)
                }
                setupViewPager()
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error fetching questions", e)
            }
    }

    private fun setupViewPager() {
        val adapter = QuestionAdapter(questionList) { position, answer ->
            userAnswers[position] = answer // Save user's answer
        }
        viewPager.adapter = adapter
    }

    private fun goToNextPage() {
        if (currentPosition < questionList.size - 1) {
            currentPosition++
            viewPager.setCurrentItem(currentPosition, true) // Move to the next question
        } else {
            Toast.makeText(requireContext(), "You have reached the last question.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToPrevPage() {
        if (currentPosition > 0) {
            currentPosition--
            viewPager.setCurrentItem(currentPosition, true) // Move to the previous question
        } else {
            Toast.makeText(requireContext(), "You are on the first question.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitExam() {
        if (userAnswers.size < questionList.size) {
            Toast.makeText(
                requireContext(),
                "Please answer all questions before submitting!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Calculate Score
        var score = 0
        questionList.forEachIndexed { index, question ->
            if (userAnswers[index] == question.correct_answer) {
                score++
            }
        }

        val intent= Intent(requireContext(),Results_Activity::class.java)
        intent.putExtra("Answered",score)
        intent.putExtra("Total_Questions",questionList.size)
        intent.putExtra("Not_answered",(questionList.size-score))
        intent.putExtra("correct",score)
        intent.putExtra("incorrect",(questionList.size-score))
        startActivity(intent)
    }
}