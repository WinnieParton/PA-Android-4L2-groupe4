package com.example.pa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FriendInfoFragment(friend: List<User>, user: User)  : Fragment() {
    private lateinit var rv: RecyclerView
    val userInfo = user
    val friends = friend
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getList()
    }

    private  fun getList(){
        val view = requireView()
        if(friends.isEmpty())
            view.findViewById<TextView>(R.id.no_game).visibility =  View.VISIBLE
        else view.findViewById<TextView>(R.id.no_game).visibility =  View.GONE
        getUser(friends, view)
    }

    private fun getUser(uses: List<User>, view: View) {
        rv = view.findViewById(R.id.list_user_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(uses, listener, "list")
    }

    private  fun answer(user: User, status: String){

        GlobalScope.launch(Dispatchers.Default) {
            try {
                ApiClient.answerFriend(UpdateFriendData(user.id, status),userInfo.id)
                withContext(Dispatchers.Main) {
                    getList()
                }
            } catch (e: Exception) {
                println("Error connecting to server: ${e.message}")
            }
        }
    }

    private val listener = UsersAdapter.OnClickListener { user, status ->

        if(status =="ACCEPTED" || status =="REJECTED"){
            answer(user, status)
        }else
        // Add action to navigate
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToFragmentDetailUser(user)
            )

    }
}