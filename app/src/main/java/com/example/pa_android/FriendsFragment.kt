package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FriendsFragment(user: User) : Fragment() {
    val userInfo = user
    private lateinit var rv: RecyclerView
    private lateinit var users: MutableList<User>

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // These are taken to make visible and invisible along with FABs
    private lateinit var addAlarmActionText: TextView
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        view.findViewById<FloatingActionButton?>(R.id.add_home_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_home_action_text).visibility = View.GONE
        mAddAlarmFab.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE

        isAllFabsVisible = false

        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddAlarmFab.show()
                mAddPersonFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addPersonActionText.visibility = View.VISIBLE
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddAlarmFab.hide()
                mAddPersonFab.hide()
                addAlarmActionText.visibility = View.GONE
                addPersonActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment(userInfo)
            )
        }

        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameFragment(userInfo)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
        }
//
        getUser(view)

    }

    private fun getUser(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_user_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(users, listener, "list")
    }

    private val listener = UsersAdapter.OnClickListener { user ->
        // Add action to navigate
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFragmentDetailUser(user)
        )

    }
}