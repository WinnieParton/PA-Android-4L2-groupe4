package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ResearchUserFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private var users: List<User> = listOf(
        User(
            "koumwinnie@gmail.com",
            "Winnie Parton",
            1,
            "winnie123456789",
            "https://icon2.cleanpng.com/20180319/vwq/kisspng-computer-icons-user-profile-avatar-profile-transparent-png-5ab03f3dba6729.3105587215214999657635.jpg",
            null
        ),
        User(
            "arthur@gmail.com",
            "Arthur",
            2,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/81/570/png-transparent-profile-logo-computer-icons-user-user-blue-heroes-logo-thumbnail.png",
            "no"
        ),
        User(
            "monel@gmail.com",
            "Monel",
            3,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
            "ok"
        ),
        User(
            "winnie@gmail.com",
            "Winnie",
            4,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/312/283/png-transparent-man-s-face-avatar-computer-icons-user-profile-business-user-avatar-blue-face-heroes-thumbnail.png",
            null
        ),
        User(
            "koum@gmail.com",
            "Koum",
            5,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
            "ok"
        )
    )
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var addAlarmActionText: TextView
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_user, container, false)
    }

    /*override fun onResume() {
        super.onResume()
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.show()
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.close)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
         view.findViewById<FloatingActionButton?>(R.id.add_person_fab).visibility=View.GONE

        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        view.findViewById<TextView>(R.id.add_person_action_text).visibility=View.GONE
        mHomeFab  =view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)

        mAddAlarmFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false
        mHomeFab  =view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)
        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddAlarmFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                true
            } else {
                mAddAlarmFab.hide()
                addAlarmActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                false
            }).also { isAllFabsVisible = it }
        })


        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                ResearchUserFragmentDirections.actionResearchUserFragmentToGameFragment()
            )
        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                ResearchUserFragmentDirections.actionResearchUserFragmentToHomeFragment()
            )
        }
//
        view.findViewById<TextView>(R.id.nb_result)
            .applyUnderlineTextPart(getString(R.string.nb_result) + 2)

        getUser(view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        progressBar.visibility = View.GONE
    }

    private fun getUser(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_user_search_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(users, listener, "search")
    }

    private val listener = UsersAdapter.OnClickListener { user ->

        if(user.status == "ok")
            Toast.makeText(requireContext(), "Person Already Added", Toast.LENGTH_SHORT).show()
        else if(user.status == "no")
            Toast.makeText(requireContext(), "You Can Add This Person", Toast.LENGTH_SHORT).show()
        else Toast.makeText(requireContext(), "Person Added", Toast.LENGTH_SHORT).show()
        // Add action to navigate
        //findNavController().navigate(
        //GameHomeFragmentDirections.actionGameHomeFragmentToGameDetailFragment(game, userArgs.userArgs)
        //)

    }
}