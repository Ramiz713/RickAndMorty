package com.itis2019.rickandmorty.characters

import android.annotation.SuppressLint
import android.app.Application
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.model.AppDatabase
import com.itis2019.rickandmorty.model.RickAndMortyApiService
import com.itis2019.rickandmorty.model.character.Character
import com.itis2019.rickandmorty.subscribeCompletableOnIoObserveOnUi
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi
import io.reactivex.Completable

@InjectViewState
class CharacterPresenter(app: Application?) : MvpPresenter<CharacterView>() {

    private var charactersList = ArrayList<Character>()
    private val application = app
    private val apiService = RickAndMortyApiService.create()
    private val characterDao = AppDatabase.getInstance(application)?.characterDao()

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @SuppressLint("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        apiService.getCharactersList(pageCount)
            .map {
                if (it.info.next.isEmpty())
                    viewState.setFlagIsLastPage(true)
                it.results
            }
            .doOnSuccess {
                viewState.setFlagIsLoading(false)
                if (pageCount == 1) characterDao?.deleteAll()
                characterDao?.insertAll(it)
            }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { viewState.showProgress() }
            .doAfterTerminate { viewState.hideProgress() }
            .subscribe(
                {
                    charactersList.addAll(it)
                    viewState.setItems(charactersList)
                },
                { error -> handleError(error) }
            )
    }

    @SuppressLint("CheckResult")
    private fun handleError(error: Throwable) {
        viewState.showError(error.toString())
        Completable.fromCallable { charactersList.addAll(characterDao?.getAll() ?: ArrayList()) }
            .subscribeCompletableOnIoObserveOnUi()
            .subscribe { viewState.setItems(charactersList) }
    }

    fun onClickedItem(position: Int) =
        viewState.navigateToInfoActivity(charactersList[position])
}
