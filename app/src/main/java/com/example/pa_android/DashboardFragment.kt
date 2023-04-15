package com.example.pa_android

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
        rv.adapter = GamesAdapter(games, listener)

    }

    private val listener = GamesAdapter.OnClickListener { game ->
        // Add action to navigate
        //findNavController().navigate(
        //GameHomeFragmentDirections.actionGameHomeFragmentToGameDetailFragment(game, userArgs.userArgs)
        //)

    }
}