package com.itis2019.rickandmorty.main

import com.itis2019.rickandmorty.model.Character

interface MainContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError(message: String)
        fun setItems(items: List<Character>)
        fun navigateToInfoActivity(character: Character)
    }

    interface Presenter {
        fun onFirstViewAttach()
        fun onClickedItem(position: Int)
        fun onLoadNextPage()
    }
}
