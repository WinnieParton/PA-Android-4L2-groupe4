package com.example.pa_android

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_container)

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setupBottomNavigationBar(user: User) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DashboardFragment(user))
                        .commit()
                    true
                }
                R.id.sms -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MessageListFragment(user))
                        .commit()
                    true
                }
                R.id.friend -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FriendsFragment(user))
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Replace the initial container view with the desired fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DashboardFragment(user))
            .commit()
    }

}

fun TextView.applyUnderlineText(text: String) {
    val spannable = SpannableString(text)
    spannable.setSpan(UnderlineSpan(), 0, text.length, 0)
    setText(spannable)
}

fun TextView.makeBoldText(text: String) {
    val spannable = SpannableString(text)
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        text.length,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    setText(spannable)
}

fun getCurrentFormattedDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE)
    return dateFormat.format(currentDate)
}