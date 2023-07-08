package com.example.pa_android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


class DetailUserFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var addAlarmActionText: TextView
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView
    private var isAllFabsVisible: Boolean? = null
    private val userInfo: DetailUserFragmentArgs by navArgs()
    private var games = mutableListOf<Game>()
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)
        // FAB button
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        mHomeFab = view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addPersonActionText.visibility = View.GONE
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false
        mAddAlarmFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddPersonFab.show()
                addPersonActionText.visibility = View.VISIBLE
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                mAddAlarmFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                mAddAlarmFab.hide()
                addAlarmActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToResearchUserFragment(userInfo.user)
            )

        }
        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToGameFragment(userInfo.user)
            )
        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToHomeFragment(userInfo.user)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToLoginFragment()
            )
        }
        view.findViewById<TextView>(R.id.text_research_input).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val filteredGames = games.filter { it.name.contains(s.toString(), ignoreCase = true) }
                getGame(view, filteredGames)
            }
        })

        view.findViewById<TextView>(R.id.id_name_profil).text = userInfo.user.name
        view.findViewById<TextView>(R.id.id_number_profil).text = "E-MAIL: " + userInfo.user.email
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listClassementGameUser(userInfo.user.id)

                for (jsonElement in response.get("rankings").asJsonArray) {
                    val it = jsonElement.asJsonObject
                    games.add(
                        Game(
                            it.get("game").asJsonObject.get("id").asInt,
                            it.get("game").asJsonObject.get("miniature").asString,
                            it.get("game").asJsonObject.get("name").asString,
                            it.get("game").asJsonObject.get("description").asString,
                            it.get("game").asJsonObject.get("minPlayers").asInt,
                            it.get("game").asJsonObject.get("maxPlayers").asInt,
                            it.get("score").asInt,
                            it.get("id").asInt
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    getGame(view, games)
                }
            } catch (e: Exception) {
                println("Error connecting to server: ${e.message}")
            }
        }
    }

    private fun getGame(view: View, gams:List<Game>) {
        rv = view.findViewById<RecyclerView>(R.id.list_game_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GamesAdapter(gams, listener, "my_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        findNavController().navigate(
            DetailUserFragmentDirections.actionFragmentDetailUserToGameInfoFragment(game,userInfo.user)
        )
    }
}