package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameInfoClassementFragment(id: Int) : Fragment() {
    val gameId: Int = id
    private var ranking = mutableListOf<Ranking>()
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
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listClassementGame(gameId)
                for (jsonElement in response.get("globalRanking").asJsonArray) {
                    val it = jsonElement.asJsonObject
                    ranking.add(
                        Ranking(
                            it.get("id").asInt,
                            User(
                                it.get("user").asJsonObject.get("id").asString,
                                it.get("user").asJsonObject.get("name").asString,
                                it.get("user").asJsonObject.get("email").asString,
                                it.get("user").asJsonObject.get("role").asString,
                                ""
                            ),
                            it.get("score").asInt
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    getRanking(view)
                }
            } catch (e: Exception) {
                println("Error connecting to server: ${e.message}")
            }
        }
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
            GameInfoFragmentDirections.actionGameInfoFragmentToFragmentDetailUser(
                ranking.user!!
            )
        )
    }
}
