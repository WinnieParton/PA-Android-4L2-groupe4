package com.example.pa_android

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projetfinaljeu.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        view.findViewById<TextView>(R.id.buttoncancel).setOnClickListener {
            findNavController().navigate(
                SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            )
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_register = view.findViewById<TextView>(R.id.buttonvalide)
        button_register.setOnClickListener {
            button_register.isEnabled=false
            val passwordEditText = view.findViewById<EditText>(R.id.editTextTextPassword)
            val usernameEditText = view.findViewById<EditText>(R.id.editTextUsername)
            val emailEditText = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
            val verifyEditText = view.findViewById<EditText>(R.id.editTextVerificationPassword)

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()
            val verifypassword = verifyEditText.text.toString()

            val emailPattern = Regex(pattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
            val drawable = GradientDrawable()
            drawable.setStroke(2, ContextCompat.getColor(requireContext(),R.color.secondary)) // 2 is the width of the border and Color.BLUE is the color
            val drawableok = GradientDrawable()
            drawableok.setStroke(2, ContextCompat.getColor(requireContext(),R.color.third)) // 2 is the width of the border and Color.BLUE is the color

            if (!email.matches(emailPattern)) {
                // email is invalid
                emailEditText.error=getString(R.string.invalid_email)
                emailEditText.background = drawable
                button_register.isEnabled=true

            } else if (password.length < 8) {
                // password is too short
                emailEditText.error=null
                emailEditText.background = drawableok

                passwordEditText.error=getString(R.string.invalid_password)
                passwordEditText.background = drawable
                button_register.isEnabled=true

            }else if (password != verifypassword){
                passwordEditText.error=null
                passwordEditText.background = drawableok

                verifyEditText.error=getString(R.string.invalid_password_confir)
                verifyEditText.background = drawable
                button_register.isEnabled=true

            }else {
                verifyEditText.error=null
                verifyEditText.background = drawableok
                emailEditText.error=null
                emailEditText.background = drawableok
                passwordEditText.error=null
                passwordEditText.background = drawableok
                signInWithEmailAndPassword(email, password, username, view)
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String, username: String, view: View){
        val button_register = view.findViewById<TextView>(R.id.buttonvalide)
        val progress_bar_home = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        progress_bar_home.visibility=View.VISIBLE
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.signup(SignupData(username, email, password,"PLAYER"))
                withContext(Dispatchers.Main) {
                    if (response.get("id").asString != null) {
                        Toast.makeText(requireContext(), "Successfull authentication", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            SignupFragmentDirections.actionSignupFragmentToHomeFragment(
                                User(
                                    response.get("id").asString,
                                    response.get("name").asString,
                                    response.get("email").asString,
                                    response.get("role").asString,
                                    ""
                                )
                            )
                        )
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.message_login), Toast.LENGTH_SHORT).show()
                        button_register.isEnabled = true
                        progress_bar_home.visibility = View.GONE
                    }

                }
            } catch (e: Exception) {
                println("Error connecting to server: ${e.message}")
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Error connecting to server: ${e.message}", Toast.LENGTH_SHORT).show()
                    button_register.isEnabled=true
                    progress_bar_home.visibility = View.GONE
                }

            }
        }
    }
}