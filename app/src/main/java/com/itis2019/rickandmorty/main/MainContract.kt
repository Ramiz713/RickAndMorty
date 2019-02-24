package com.itis2019.rickandmorty.main

import android.app.Activity
import android.widget.ImageView
import com.itis2019.rickandmorty.model.Character

interface MainContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError(message: String)
        fun setItems(items: List<Character>)
    }

    interface Presenter {
        fun onViewAttach()
        fun onItemClicked(activity: Activity, position: Int, image: ImageView)
    }
}
