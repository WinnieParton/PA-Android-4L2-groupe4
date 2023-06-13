package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GameFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView
    private val user: GameFragmentArgs by navArgs()
    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null

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
        view.findViewById<FloatingActionButton?>(R.id.add_alarm_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_alarm_action_text).visibility = View.GONE
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        mHomeFab = view.findViewById(R.id.add_home_fab)
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
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToResearchUserFragment(user.user)
            )

        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToHomeFragment(user.user)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToLoginFragment()
            )
        }
//
        val dataSearch = mutableListOf<Game>()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listGames()
                for (game in response.get("games").asJsonArray) {
                    val it = game.asJsonObject
                    dataSearch.add(
                        Game(
                            it.get("id").asInt, it.get("miniature").asString, it.get("name").asString,
                            it.get("description").asString,it.get("minPlayers").asInt,it.get("maxPlayers").asInt
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    if (dataSearch.size == 0)
                        view.findViewById<TextView>(R.id.no_game).visibility = View.VISIBLE
                    else view.findViewById<TextView>(R.id.no_game).visibility = View.GONE
                    getGame(dataSearch, view)
                }
            } catch (e: Exception) {
                println("${e.message}")
            }
        }
    }

    private fun getGame(games: List<Game>, view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_game_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GamesAdapter(games, listener, "my_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameInfoFragment(game,user.user)
        )
    }
}