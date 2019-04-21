package com.itis2019.rickandmorty.ui.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.itis2019.rickandmorty.R

class SeventhFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh, container, false)
        val textView = view.findViewById<TextView>(R.id.tv_safe_args)
        arguments?.let {
            val safeArgs = SeventhFragmentArgs.fromBundle(it)
            textView.text = safeArgs.title
        }
        return view
    }
}
