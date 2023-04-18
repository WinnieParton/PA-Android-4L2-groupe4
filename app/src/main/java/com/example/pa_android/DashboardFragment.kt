package com.example.pa_android

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DashboardFragment : Fragment() {
    private var games: List<Game> = listOf(
        Game(
            1,
            "https://gamesgamescdn.com/system/static/thumbs/slider_image/73499/original_EP-uphill-rush-12-462x250.jpg?1676468575",
            "Schouchin toxa",
            "oil spray"
        ),
        Game(
            2,
            "https://gamesgamescdn.com/system/static/thumbs/slider_image/70730/original_1499set000_easter-2023_462x250_en.jpg?1681111279",
            "uppliqh ddd",
            "orrrr"
        ),
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

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton

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
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        mAddPersonFab = view.findViewById(R.id.add_person_fab)

        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)

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

                true
            } else {
                mAddAlarmFab.hide()
                mAddPersonFab.hide()
                addAlarmActionText.visibility = View.GONE
                addPersonActionText.visibility = View.GONE

                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment()
            )
            //Toast.makeText(requireContext(), "Person Added", Toast.LENGTH_SHORT).show()
        }

        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameFragment()
            )
        }
//
        val relativeLayout: RelativeLayout = view.findViewById(R.id.homeSearchBar)
        val drawable1 = GradientDrawable()
        drawable1.cornerRadius = 15f // set the corner radius
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        drawable1.setColor(color) // set the color using a resource
        relativeLayout.background = drawable1
        /*view.findViewById<ImageView>(R.id.id_image_setting).setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToProfilFragment()
            )
        }*/
        relativeLayout.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment()
            )
        }
        getGame(view)
    }

    private fun getGame(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_game_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GamesAdapter(games, listener, "all_game")

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToGameInfoFragment(game)
        )
    }
}