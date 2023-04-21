package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfilFragment : Fragment() {
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        view.findViewById<FloatingActionButton?>(R.id.add_alarm_fab).visibility = View.GONE
        view.findViewById<FloatingActionButton?>(R.id.add_person_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_logout_action_text).visibility = View.GONE
        view.findViewById<FloatingActionButton?>(R.id.add_logout_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_alarm_action_text).visibility = View.GONE
        view.findViewById<TextView>(R.id.add_person_action_text).visibility = View.GONE
        mHomeFab = view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false
        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                true
            } else {
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                false
            }).also { isAllFabsVisible = it }
        })

        mHomeFab.setOnClickListener {
            findNavController().navigate(
                ProfilFragmentDirections.actionProfilFragmentToHomeFragment()
            )
        }
        view.findViewById<TextView>(R.id.buttoncancel).setOnClickListener {
            findNavController().navigate(
                ProfilFragmentDirections.actionProfilFragmentToHomeFragment()
            )
        }

    }

}