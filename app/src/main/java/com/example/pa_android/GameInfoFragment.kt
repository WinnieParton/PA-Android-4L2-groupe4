package com.example.pa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameInfoFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView

    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        view.findViewById<FloatingActionButton?>(R.id.add_alarm_fab).visibility=View.GONE
        view.findViewById<TextView?>(R.id.add_alarm_action_text).visibility=View.GONE
        mAddPersonFab = view.findViewById(R.id.add_person_fab)

        addPersonActionText =view.findViewById(R.id.add_person_action_text)
        mHomeFab  =view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)

        mAddPersonFab.visibility = View.GONE
        addPersonActionText.visibility = View.GONE
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false

        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddPersonFab.show()
                addPersonActionText.visibility = View.VISIBLE
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                view.findViewById<FloatingActionButton?>(R.id.add_alarm_fab).show()
                view.findViewById<TextView?>(R.id.add_alarm_action_text).visibility=View.VISIBLE
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                view.findViewById<FloatingActionButton?>(R.id.add_alarm_fab).hide()
                view.findViewById<TextView?>(R.id.add_alarm_action_text).visibility=View.GONE
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToResearchUserFragment()
            )

        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToHomeFragment()
            )
        }

    }
}