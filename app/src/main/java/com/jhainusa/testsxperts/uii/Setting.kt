package com.jhainusa.testsxperts.uii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.databinding.ActivityYourTestBinding
import com.jhainusa.testsxperts.databinding.FragmentSettingBinding

class Setting : Fragment() {
    val userId= FirebaseAuth.getInstance().currentUser?.uid
    var db= FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        db.collection("Users").document(userId!!)
            .get()
            .addOnSuccessListener{
                binding.emailuser.text=it.getString("email")
                binding.settingname.text=it.getString("name")

            }
        return binding.root

    }
}