package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fabVisible = false


            // initializing variables of floating
            // action button on below line.
        val addFAB: FloatingActionButton = view.findViewById(R.id.idFABAdd)
        val homeFAB: FloatingActionButton = view.findViewById(R.id.idFABHome)
        val settingsFAB: FloatingActionButton = view.findViewById(R.id.idFABSettings)

            // on below line we are initializing our
            // fab visibility boolean variable
            fabVisible = false

            // on below line we are adding on click listener
            // for our add floating action button
            addFAB.setOnClickListener {
                // on below line we are checking
                // fab visible variable.
                if (!fabVisible) {

                    // if its false we are displaying home fab
                    // and settings fab by changing their
                    // visibility to visible.
                    homeFAB.show()
                    settingsFAB.show()

                    // on below line we are setting
                    // their visibility to visible.
                    homeFAB.visibility = View.VISIBLE
                    settingsFAB.visibility = View.VISIBLE

                    // on below line we are checking image icon of add fab
                    addFAB.setImageDrawable(resources.getDrawable(R.drawable.radius_validate))

                    // on below line we are changing
                    // fab visible to true
                    fabVisible = true
                } else {

                    // if the condition is true then we
                    // are hiding home and settings fab
                    homeFAB.hide()
                    settingsFAB.hide()

                    // on below line we are changing the
                    // visibility of home and settings fab
                    homeFAB.visibility = View.GONE
                    settingsFAB.visibility = View.GONE

                    // on below line we are changing image source for add fab
                    addFAB.setImageDrawable(resources.getDrawable(R.drawable.circle_image))

                    // on below line we are changing
                    // fab visible to false.
                    fabVisible = false
                }
            }

            // on below line we are adding
            // click listener for our home fab
            homeFAB.setOnClickListener {
                // on below line we are displaying a toast message.
                Toast.makeText(requireContext(), "Home clicked..", Toast.LENGTH_LONG).show()
            }

            // on below line we are adding on
            // click listener for settings fab
            settingsFAB.setOnClickListener {
                // on below line we are displaying a toast message
                Toast.makeText(requireContext(), "Settings clicked..", Toast.LENGTH_LONG).show()
            }
        }

}