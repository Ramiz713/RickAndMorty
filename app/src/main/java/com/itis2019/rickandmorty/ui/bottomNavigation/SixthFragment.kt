package com.itis2019.rickandmorty.ui.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.itis2019.rickandmorty.R

class SixthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sixth, container, false)

        view.findViewById<Button>(R.id.btn_get_title).setOnClickListener {
            val text = view.findViewById<EditText>(R.id.edit_text_sage_args).text.toString()
            val action = SixthFragmentDirections.actionSixthFragmentToSeventhFragment(title = text)
            findNavController(this).navigate(action)
        }
        return view
    }
}
