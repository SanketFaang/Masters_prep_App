package com.jhainusa.testsxperts.uii

import MyAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.databinding.ActivityYourTestBinding
import com.jhainusa.testsxperts.databinding.FragmentTestSeriesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Your_test : AppCompatActivity() {
    private lateinit var binding: ActivityYourTestBinding
    lateinit var database: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var Adapter: MyAdapter
    private lateinit var testList: MutableList<testCardView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val testCard = intent.getStringExtra("Subject")
        binding = ActivityYourTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        testList = mutableListOf()
        Adapter=MyAdapter(testList)
        recyclerView.adapter=Adapter
        database = FirebaseDatabase.getInstance().getReference("Tests/${testCard}/Subjects")
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
                Toast.makeText(this@Your_test, "Failed to load posts", Toast.LENGTH_SHORT).show()
            }
        })

        Adapter.setItemCLickListener(object :MyAdapter.onItemClickListener{
            override fun onItemCLick(position: Int) {
                val intent = Intent(this@Your_test,CourseTest::class.java)
                intent.putExtra("Subject",testList[position].testname)
                startActivity(intent)
            }
        })

    }
}