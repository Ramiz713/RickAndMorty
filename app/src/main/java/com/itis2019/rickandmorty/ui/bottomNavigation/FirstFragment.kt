package com.itis2019.rickandmorty.ui.bottomNavigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis2019.rickandmorty.App
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.FirstPresenter
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.FirstView
import javax.inject.Inject

class FirstFragment : Fragment(), FirstView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FirstPresenter

    @ProvidePresenter
    fun providePresenter(): FirstPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val button = view.findViewById<Button>(R.id.btn_fragment_to_4)
        button.setOnClickListener { presenter.onButtonPressed() }
        return view
    }
}
