package com.jhainusa.testsxperts.uii

import MyAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.TextSnapshot
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.R.id.testFragment
import com.jhainusa.testsxperts.databinding.ActivityMainBinding
import com.jhainusa.testsxperts.databinding.FragmentTestSeriesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TestSeries : Fragment() {

    private lateinit var binding: FragmentTestSeriesBinding
    lateinit var database: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var Adapter: MyAdapter
    private lateinit var testList: MutableList<testCardView>
    val userId= FirebaseAuth.getInstance().currentUser?.uid
    var db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestSeriesBinding.inflate(layoutInflater)
        db.collection("Users").document(userId!!)
            .get()
            .addOnSuccessListener{
                binding.userNameText.text=it.getString("name")
            }
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        testList = mutableListOf()
        Adapter=MyAdapter(testList)
        recyclerView.adapter=Adapter
        database = FirebaseDatabase.getInstance().getReference("Tests")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val tempList = mutableListOf<testCardView>()
                    for (i in snapshot.children) {
                        val test = i.getValue(testCardView::class.java)
                        if (test != null) {
                            tempList.add(test)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        testList.clear()
                        testList.addAll(tempList)
                        Adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load posts", Toast.LENGTH_SHORT).show()
            }
        })

        Adapter.setItemCLickListener(object :MyAdapter.onItemClickListener{
            override fun onItemCLick(position: Int) {
                val intent = Intent(requireContext(),Your_test::class.java)
                intent.putExtra("Subject",testList[position].testname)
                startActivity(intent)
            }
        })
        return binding.root
    }
}