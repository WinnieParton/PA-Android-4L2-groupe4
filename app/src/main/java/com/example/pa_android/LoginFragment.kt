package com.example.pa_android

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.forget_password_text_title)
            .applyUnderlineText(getString(R.string.forget_password))

        view.findViewById<TextView>(R.id.signup_text)
            .applyUnderlineText(getString(R.string.not_account))

        view.findViewById<TextView>(R.id.forget_password_text_title).setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
            )
        }
        view.findViewById<TextView>(R.id.signup_text).setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            )
        }
        val editText = view.findViewById<EditText>(R.id.password)
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = editText.compoundDrawablesRelative[2] // Index 2 is for end drawable
                if (drawable != null && event.rawX >= editText.right - drawable.bounds.width()) {
                    // Clicked on drawableEnd
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    )
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

    }
}