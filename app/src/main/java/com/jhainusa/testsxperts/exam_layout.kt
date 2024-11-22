package com.jhainusa.testsxperts

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jhainusa.testsxperts.databinding.ActivityExamLayoutBinding
import com.jhainusa.testsxperts.databinding.ActivityLevelwiseBinding
import com.jhainusa.testsxperts.uii.CourseTest

class exam_layout : AppCompatActivity() {
    private lateinit var binding:ActivityExamLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityExamLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentd = Intent(this@exam_layout,CourseTest::class.java)
        val t = intent.getStringExtra("Subject")
        intentd.putExtra("Subject",t)
        binding.easy.setOnClickListener {
            intentd.putExtra("easy","easy")
            startActivity(intentd)
        }
        binding.medium.setOnClickListener {
            intentd.putExtra("medium","medium")
            startActivity(intentd)
        }
        binding.hard.setOnClickListener {
            intentd.putExtra("hard","hard")
            startActivity(intentd)
        }
        binding.pyqbtn.setOnClickListener {
            intentd.putExtra("pyq","PYQ")
            startActivity(intentd)
        }
        binding.chapterwisetbtn.setOnClickListener {
            intentd.putExtra("chapterwise","ChapterWise Questions")
            startActivity(intentd)
        }
    }
}