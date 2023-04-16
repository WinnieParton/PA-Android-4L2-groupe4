package com.example.pa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GameFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView

    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    private var games: List<Game> = listOf(

        Game(
            3,
            "https://www.gamereactor.fr/media/90/_3229073.jpg",
            "eeee fr",
            "orrrr"
        ),
        Game(
            4,
            "https://images.pexels.com/photos/6689072/pexels-photo-6689072.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "erererz",
            "kkkkk"
        )
    )
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
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
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
               GameFragmentDirections.actionGameFragmentToResearchUserFragment()
            )

        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToHomeFragment()
            )
        }

//
        getGame(view)
    }

    private fun getGame(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_game_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GamesAdapter(games, listener,"my_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        //findNavController().navigate(
        //GameHomeFragmentDirections.actionGameHomeFragmentToGameDetailFragment(game, userArgs.userArgs)
        //)

    }
}