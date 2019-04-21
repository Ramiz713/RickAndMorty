package com.itis2019.rickandmorty.ui.characters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import ru.terrakok.cicerone.Router

@InjectViewState
class CharacterPresenter(private val repository: Repository, private val router: Router) :
    MvpPresenter<CharacterView>() {

    private var charactersList = ArrayList<Character>()

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @Suppress("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        repository.getCharactersPage(pageCount)
            .doOnSuccess {
                viewState.setFlagIsLoading(false)
                if (pageCount != 1) repository.cacheCharacters(it)
                else repository.rewriteCacheCharacters(it)
            }
            .map {
                charactersList.addAll(it)
                charactersList.toList()
            }
            .onErrorResumeNext {
                viewState.showError(it.message ?: "")
                Single.just(repository.getCachedCharacters())
            }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { viewState.showProgress() }
            .doAfterTerminate { viewState.hideProgress() }
            .subscribeBy {
                viewState.setItems(it)
            }
    }

    fun onClickedItem(position: Int) =
        router.navigateTo(Screens.CharacterInfoScreen(charactersList[position]))
}
