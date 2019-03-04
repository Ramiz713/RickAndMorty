package com.itis2019.rickandmorty.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    fun paginationSizeItemClicked() =
        viewState.showDialog()
}
