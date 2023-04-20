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
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailUserFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var addAlarmActionText: TextView
    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView
    private var isAllFabsVisible: Boolean? = null
    private val userInfo: DetailUserFragmentArgs by navArgs()
    private var games: List<Game> = listOf(
        Game(
            3,
            "https://www.gamereactor.fr/media/90/_3229073.jpg",
            "Three fighter",
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)"
        ),
        Game(
            4,
            "https://images.pexels.com/photos/6689072/pexels-photo-6689072.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "Sangoku Vegan",
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)"
        )
    )
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
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                mAddAlarmFab.hide()
                addAlarmActionText.visibility = View.GONE
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToResearchUserFragment()
            )

        }
        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToGameFragment()
            )
        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                DetailUserFragmentDirections.actionFragmentDetailUserToHomeFragment()
            )
        }
        view.findViewById<TextView>(R.id.id_name_profil).text = userInfo.user.username
        view.findViewById<TextView>(R.id.id_number_profil).text = "E-MAIL: "+userInfo.user.email
        getGame(view)
    }

    private fun getGame(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_game_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GamesAdapter(games, listener, "my_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        findNavController().navigate(
            DetailUserFragmentDirections.actionFragmentDetailUserToGameInfoFragment(game)
        )
    }
}