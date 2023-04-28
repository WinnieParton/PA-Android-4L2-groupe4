package com.example.pa_android

import android.os.Bundle
import android.telecom.DisconnectCause.REJECTED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

class FriendsFragment(user: User) : Fragment() {
    val userInfo = user
    private lateinit var rv: RecyclerView

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
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
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        view.findViewById<FloatingActionButton?>(R.id.add_home_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_home_action_text).visibility = View.GONE
        mAddAlarmFab.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE

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
        getList()
//


    }

    private  fun getList(){
        val view = requireView()
        val dataSearch = mutableListOf<User>()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listFriendSend(userInfo.id)
                val newdata = JsonParser().parse(response.toString()).asJsonArray
                for (jsonElement in newdata) {
                    val it = jsonElement.asJsonObject
                    val f = JsonParser().parse(it.get("friend").toString()).asJsonObject
                    if(it.get("status").asString == "ACCEPTED")
                        dataSearch.add(
                            User(
                                f.get("id").asString,
                                f.get("name").asString,
                                f.get("email").asString,
                                f.get("role").asString,
                                it.get("status").asString
                            )
                        )
                }

            /*    val response1 = ApiClient.listFriendReceived(userInfo.id)
                val newdata1 = JsonParser().parse(response1.toString()).asJsonArray
                for (jsonElement in newdata1) {
                    val it = jsonElement.asJsonObject
                    val f = JsonParser().parse(it.get("user").toString()).asJsonObject
                    if(it.get("status").asString == "ACCEPTED")
                        dataSearch.add(
                            User(
                                f.get("id").asString,
                                f.get("name").asString,
                                f.get("email").asString,
                                f.get("role").asString,
                                it.get("status").asString
                            )
                        )
                }
*/
                withContext(Dispatchers.Main) {
                    if(dataSearch.size == 0)
                        view.findViewById<TextView>(R.id.no_game).visibility =  View.VISIBLE
                    else view.findViewById<TextView>(R.id.no_game).visibility =  View.GONE

                    getUser(dataSearch, view)
                }
            } catch (e: Exception) {
                println("${e.message}")
            }
        }
    }
    private fun getUser(uses: List<User>, view: View) {

        rv = view.findViewById(R.id.list_user_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(uses, listener, "list")
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
}