package com.jhainusa.testsxperts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jhainusa.testsxperts.databinding.ActivityMainBinding
import com.jhainusa.testsxperts.uii.Chatbot
import com.jhainusa.testsxperts.uii.Profile
import com.jhainusa.testsxperts.uii.Setting
import com.jhainusa.testsxperts.uii.TestSeries
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val userId= FirebaseAuth.getInstance().currentUser?.uid
    var db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navbar=binding.bottomBar
        navbar.onTabSelected = { tab ->
            when (tab.id) {
                R.id.profileFragment -> loadFragment(Profile())
                R.id.Chatbotfragment -> loadFragment(Chatbot())
                R.id.testSeriesFragment -> loadFragment(TestSeries())
                R.id.settingsFragment -> loadFragment(Setting())

            }
        }


    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView4, fragment)
            .commit()
    }
}