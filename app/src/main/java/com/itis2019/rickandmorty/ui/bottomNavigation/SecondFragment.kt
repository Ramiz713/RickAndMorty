package com.itis2019.rickandmorty.ui.bottomNavigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.itis2019.rickandmorty.R

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        view.findViewById<Button>(R.id.btn_fragment_to_5).setOnClickListener {
            findNavController(this).navigate(R.id.action_navigation_dashboard_to_fifthFragment)
        }
        return view
    }
}
