package com.jhainusa.testsxperts.uii

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jhainusa.testsxperts.MainActivity
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.databinding.FragmentProfileBinding
import com.jhainusa.testsxperts.databinding.FragmentSettingBinding

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding=FragmentProfileBinding.inflate(layoutInflater)
        binding.yourTestSeries.setOnClickListener{
            val fragmentB = TestSeries()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView4,fragmentB)
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

}
