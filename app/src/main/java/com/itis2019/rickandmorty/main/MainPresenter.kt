package com.itis2019.rickandmorty.main

import android.annotation.SuppressLint
import com.itis2019.rickandmorty.model.Character
import com.itis2019.rickandmorty.model.RickAndMortyApiService
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi

class MainPresenter(var mainView: MainContract.View) : MainContract.Presenter {
    private var charactersList = ArrayList<Character>()

    @SuppressLint("CheckResult")
    override fun onViewAttach() {
        RickAndMortyApiService.create().getCharactersList()
            .map { it.results }
            .doOnSuccess {
                charactersList.addAll(it)
            }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { mainView.showProgress() }
            .doAfterTerminate { mainView.hideProgress() }
            .subscribe(
                { mainView.setItems(it) },
                { error -> mainView.showError(error.toString()) }
            )
    }

    override fun getOnClickedItem(position: Int): Character =
        charactersList[position]
}
