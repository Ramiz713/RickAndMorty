package com.itis2019.rickandmorty.ui.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.itis2019.rickandmorty.R

private const val ARG_COUNT = "count"

class FifthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fifth, container, false)
        val button = view.findViewById<Button>(R.id.btn_level)
        var count = arguments?.getInt(ARG_COUNT) ?: 0
        button.text = getString(R.string.button_level, count.toString())

        button.setOnClickListener {
            val bundle = Bundle().apply { putInt(ARG_COUNT, ++count) }
            findNavController(this).navigate(R.id.action_fifthFragment_self, bundle)
        }
        return view
    }
}
