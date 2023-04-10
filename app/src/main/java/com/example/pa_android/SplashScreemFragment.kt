package com.example.pa_android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController


class SplashScreemFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screem, container, false)
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.signup_btton).setBackgroundResource(R.drawable.radius_cancel)

        view.findViewById<TextView>(R.id.login_button).setOnClickListener {
            findNavController().navigate(
                SplashScreemFragmentDirections.actionSpashscreemFragmentToLoginFragment()
            )
        }

        view.findViewById<TextView>(R.id.signup_btton).setOnClickListener {
            findNavController().navigate(
                SplashScreemFragmentDirections.actionSpashscreemFragmentToSignupFragment()
            )
        }
    }
}