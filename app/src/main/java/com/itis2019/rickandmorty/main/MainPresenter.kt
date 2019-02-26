package com.itis2019.rickandmorty.main

import android.annotation.SuppressLint
import com.itis2019.rickandmorty.model.Character
import com.itis2019.rickandmorty.model.RickAndMortyApiService
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi

class MainPresenter(var mainView: MainContract.View) : MainContract.Presenter {
    private var charactersList = ArrayList<Character>()
    private lateinit var apiService: RickAndMortyApiService
    private var pageCount = 1

    override fun onFirstViewAttach() {
        apiService = RickAndMortyApiService.create()
        onLoadNextPage()
    }

    @SuppressLint("CheckResult")
    override fun onLoadNextPage() {
        apiService.getCharactersList(pageCount)
            .map { it.results }
            .doOnSuccess { pageCount++ }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { mainView.showProgress() }
            .doAfterTerminate { mainView.hideProgress() }
            .subscribe(
                {
                    charactersList.addAll(it)
                    mainView.setItems(charactersList)
                },
                { error -> mainView.showError(error.toString()) }
            )
    }

    override fun onClickedItem(position: Int) =
        mainView.navigateToInfoActivity(charactersList[position])
}
