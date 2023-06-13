package com.example.pa_android

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardFragment(user: User) : Fragment() {
    val userInfo = user
    private lateinit var rv: RecyclerView

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton

    // These are taken to make visible and invisible along with FABs
    private lateinit var addAlarmActionText: TextView
    private lateinit var addPersonActionText: TextView
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    //
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.id_name_profil).text=userInfo.name
        view.findViewById<TextView>(R.id.id_number_profil).text="E-MAIL: "+userInfo.email

        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        mAddPersonFab = view.findViewById(R.id.add_person_fab)

        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE

        mAddAlarmFab.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE
        view.findViewById<FloatingActionButton?>(R.id.add_home_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_home_action_text).visibility = View.GONE

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
        val relativeLayout: RelativeLayout = view.findViewById(R.id.homeSearchBar)
        val drawable1 = GradientDrawable()
        drawable1.cornerRadius = 15f // set the corner radius
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        drawable1.setColor(color) // set the color using a resource
        relativeLayout.background = drawable1
        view.findViewById<ImageView>(R.id.id_setting).setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfilFragment(userInfo)
            )
        }
        relativeLayout.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment(userInfo)
            )
        }
        val dataSearch = mutableListOf<Game>()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listGames()
                val responseFriend = ApiClient.listMyFriend(userInfo.id)
                val countFriend = responseFriend.get("requests").asJsonArray.size()
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
                    view.findViewById<TextView>(R.id.id_value_friend).text= countFriend.toString()
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
        rv.adapter = GamesAdapter(games, listener, "all_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToGameInfoFragment(game,userInfo)
        )
    }
}