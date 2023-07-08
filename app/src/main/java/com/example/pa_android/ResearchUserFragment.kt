package com.example.pa_android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonElement
import kotlinx.coroutines.*

class ResearchUserFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private val user: ResearchUserFragmentArgs by navArgs()

    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var addAlarmActionText: TextView
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private var isAllFabsVisible: Boolean? = null
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_user, container, false)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        view.findViewById<FloatingActionButton?>(R.id.add_person_fab).visibility = View.GONE
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        view.findViewById<TextView>(R.id.add_person_action_text).visibility = View.GONE
        mHomeFab = view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)

        mAddAlarmFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false
        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddAlarmFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddAlarmFab.hide()
                addAlarmActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()
                false
            }).also { isAllFabsVisible = it }
        })


        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                ResearchUserFragmentDirections.actionResearchUserFragmentToGameFragment(user.user)
            )
        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                ResearchUserFragmentDirections.actionResearchUserFragmentToHomeFragment(user.user)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                ResearchUserFragmentDirections.actionResearchUserFragmentToLoginFragment()
            )
        }
//
        val clickbutton = view.findViewById<ImageView>(R.id.iconsearchbutton)
        val searchEditText = view.findViewById<EditText>(R.id.text_research_input)
        val constraint = view.findViewById<ConstraintLayout>(R.id.constraint_id)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        progressBar.visibility = View.GONE

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        val dataSearch = mutableListOf<User>()
        getUser(dataSearch, view)

        clickbutton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            constraint.visibility = View.GONE
            view.findViewById<ConstraintLayout>(R.id.constraint_id_recy).visibility=View.GONE
            dataSearch.clear()
            GlobalScope.launch(Dispatchers.Default) {
                try {
                    val response = ApiClient.researchByName(user.user.id, searchEditText.text.trim().toString())

                    for (usr in response) {
                        val it = usr.asJsonObject
                        val friendsArray = it.get("friends").asJsonArray

                        var containsId = false

                        for (friend in friendsArray) {
                            if (friend.isJsonObject) {
                                val friendObject = friend.asJsonObject
                                if (friendObject.get("id").asInt == 15) {
                                    containsId = true
                                    break
                                }
                            }
                        }

                        dataSearch.add(
                            User(
                                it.get("id").asString,
                                it.get("name").asString,
                                it.get("email").asString,
                                it.get("role").asString,
                                if(containsId) "ACCEPTED" else ""
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                        view.findViewById<ConstraintLayout>(R.id.constraint_id_recy).visibility=View.VISIBLE
                        getUser(dataSearch, view)
                    }
                } catch (e: Exception) {
                    activity?.runOnUiThread {
                        progressBar.visibility = View.GONE
                        constraint.visibility = View.VISIBLE
                        Toast.makeText(
                            requireContext(),
                            "Not found "+searchEditText.text.toString()+". Please write all name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    println("Error connecting to server: ${e.message}")
                }
            }
        }
    }

    private fun getUser(users: List<User>,view: View) {
        if(users.size ==0)
            view.findViewById<ConstraintLayout>(R.id.constraint_id_recy).visibility = View.GONE
        rv = view.findViewById(R.id.list_user_search_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(users, listener, "search")
    }

    private val listener = UsersAdapter.OnClickListener { us, status ->
        GlobalScope.launch(Dispatchers.Default) {
            try {
                ApiClient.addFriend(AddFriendData(user.user.id), us.id)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Person Added", Toast.LENGTH_SHORT).show()
                }
                val users: List<User> = listOf()
                getUser(users, requireView())
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "User already added", Toast.LENGTH_SHORT
                    ).show()
                }
                println("Error connecting to server: ${e.message}")
            }
        }

    }
}