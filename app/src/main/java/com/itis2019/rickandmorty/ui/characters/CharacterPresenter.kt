package com.itis2019.rickandmorty.ui.characters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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
            .map {
                if (it.pageInformation.next.isEmpty())
                    viewState.setIsLastPage()
                it.results
            }
            .doOnSuccess { repository.cacheCharacters(it) }
            .onErrorResumeNext {
                viewState.showError(it.message ?: "")
                charactersList.clear()
                Single.just(repository.getCachedCharacters())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .doAfterTerminate { viewState.hideProgress() }
            .doAfterTerminate { viewState.setIsNotLoading() }
            .subscribeBy {
                charactersList.addAll(it)
                viewState.setItems(charactersList.toList())
            }
    }

    fun onClickedItem(position: Int) =
        router.navigateTo(Screens.CharacterInfoScreen(charactersList[position]))
}
