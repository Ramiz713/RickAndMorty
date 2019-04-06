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
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.FifthPresenter
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.FifthView
import javax.inject.Inject

class FifthFragment : Fragment(), FifthView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FifthPresenter

    @ProvidePresenter
    fun providePresenter(): FifthPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fifth, container, false)
        val button = view.findViewById<Button>(R.id.btn_level)
        var count = arguments?.getInt(ARG_COUNT) ?: 0
        button.text = getString(R.string.button_level, count.toString())
        button.setOnClickListener { presenter.onButtonPressed(++count) }
        return view
    }

    companion object {
        private const val ARG_COUNT = "count"

        @JvmStatic
        fun newInstance(count: Int) =
            FifthFragment().apply {
                arguments = Bundle(1).apply {
                    putInt(ARG_COUNT, count)
                }
            }
    }
}
