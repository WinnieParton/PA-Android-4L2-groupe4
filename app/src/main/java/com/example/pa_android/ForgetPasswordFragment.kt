package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class ForgetPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forget_password, container, false)

        view.findViewById<TextView>(R.id.buttoncancl).setOnClickListener {
            findNavController().navigate(
                ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToLoginFragment()
            )
        }
        view.findViewById<TextView>(R.id.buttonvalid).setOnClickListener {

        }
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}