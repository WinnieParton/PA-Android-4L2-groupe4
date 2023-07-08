package com.example.pa_android

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projetfinaljeu.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT

class LoginFragment : Fragment() {
    private var loginData: LoginData=LoginData(null,null)
    private lateinit var sharedPreferences: SharedPreferences
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("YourPreferencesName", Context.MODE_PRIVATE)
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
        val passwordEditText = view.findViewById<EditText>(R.id.editTextTextPassword)
        val progress_bar_home = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        progress_bar_home.visibility=View.GONE
        val emailEditText = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        passwordEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = passwordEditText.compoundDrawablesRelative[2] // Index 2 is for end drawable
                if (drawable != null && event.rawX >= passwordEditText.right - drawable.bounds.width()) {
                        passwordEditText.isEnabled=false
                        val email = emailEditText.text.toString()
                        val password = passwordEditText.text.toString()

                        val emailPattern = Regex(pattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                        val drawable = GradientDrawable()
                        drawable.setStroke(2, ContextCompat.getColor(requireContext(),R.color.secondary)) // 2 is the width of the border and Color.BLUE is the color
                        val drawableok = GradientDrawable()
                        drawableok.setStroke(2, ContextCompat.getColor(requireContext(),R.color.third)) // 2 is the width of the border and Color.BLUE is the color

                        if (!email.matches(emailPattern)) {
                            // email is invalid
                            emailEditText.error=getString(R.string.invalid_email)
                            emailEditText.background = drawable
                            passwordEditText.isEnabled=true

                        } else if (password.length < 3) {
                            // password is too short
                            emailEditText.error=null
                            emailEditText.background = drawableok

                            passwordEditText.error=getString(R.string.invalid_password)
                            passwordEditText.background = drawable
                            passwordEditText.isEnabled=true

                        } else {
                            passwordEditText.isEnabled=false
                            passwordEditText.error=null
                            passwordEditText.background = drawableok
                            emailEditText.error=null
                            emailEditText.background = drawableok
                            login(email, password, view)
                        }


                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                loginData= LoginData(s.toString(), loginData.password)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                loginData= LoginData(loginData.email, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                emailEditText.error = null
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun login(email: String, password: String, view: View){
        val progress_bar_home = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        val passwordEditText = view.findViewById<EditText>(R.id.editTextTextPassword)
        progress_bar_home.visibility=View.VISIBLE
        GlobalScope.launch(Dispatchers.Default) {
            try {
                ApiClient.setSharedPreferences(sharedPreferences)
                val response = ApiClient.login(LoginData(email, password))
                withContext(Dispatchers.Main) {
                    if (response.get("token").asString != null) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: DecodedJWT = JWT.decode(response.get("token").asString)

                        val id: String? = user.getClaim("id").asInt().toString()
                        val name: String? = user.getClaim("name").asString()
                        val role: String? = user.getClaim("role").asString()
                        val email: String? = user.getClaim("sub").asString()
                        if(id !=null && name !=null&& email !=null&& role !=null){
                            saveTokenToLocalStorage(response.get("token").asString)
                            Toast.makeText(requireContext(), "Successfull authentication", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                    User(id, name, email, role, "" )
                                )
                            )
                        }

                    } else {
                        println(getString(R.string.message_login))
                        passwordEditText.isEnabled=true
                        Toast.makeText(requireContext(), getString(R.string.message_login), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    if(e.message?.contains("HTTP 403") == true){
                        Toast.makeText(requireContext(), getString(R.string.message_login), Toast.LENGTH_SHORT).show()
                    }else {
                    Toast.makeText(
                        requireContext(),
                        "Error connecting to server",
                        Toast.LENGTH_SHORT
                    ).show()
                    }
                    passwordEditText.isEnabled = true
                    progress_bar_home.visibility = View.GONE
                }
            }
        }
    }

    // Function to store the token in SharedPreferences
    private fun saveTokenToLocalStorage(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
}