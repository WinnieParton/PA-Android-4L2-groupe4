package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameInfoClassementFragment : Fragment() {
    private var ranking: List<Ranking> = listOf(
        Ranking(
            1,
            User(
                "122",
                "Winnie Ali",
                "koum@gmail.com",
                "winnie123456789"
            ),
            700
        ),
        Ranking(
            2,
            User(
                "1222",
                "koum@gmail.com",
                "Mohamed Youss",
                "winnie123456789"
            ),
            900
        ),
        Ranking(
            3,
            User(
                "1922",
                "koum@gmail.com",
                "Anatol Bagh",
                "winnie123456789"
            ),
            750
        ),
    )
    private lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_info_classement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRanking(view)
    }

    private fun getRanking(view: View) {
        val sortedRanking = ranking.sortedByDescending { it.score }
        rv = view.findViewById<RecyclerView>(R.id.list_classement_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = RankingsAdapter(sortedRanking, listener)

    }

    private val listener = RankingsAdapter.OnClickListener { ranking ->
        // Add action to navigate
        findNavController().navigate(
            GameInfoClassementFragmentDirections.actionGameInfoClassementFragmentToFragmentDetailUser(
                ranking.user!!
            )
        )
    }
}
