package com.itis2019.rickandmorty.ui.characters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Status
import com.itis2019.rickandmorty.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.terrakok.cicerone.Router

@InjectViewState
class CharacterPresenter(private val repository: Repository, private val router: Router) :
    MvpPresenter<CharacterView>() {

    private var charactersList = mutableListOf<Character>()
    private var filteredCharactersList: List<Character> = charactersList

    private var currentPage = 1
    private var filterByStatus: Status? = null

    override fun onFirstViewAttach() = loadNextPage()

    @Suppress("CheckResult")
    fun loadNextPage() {
        repository.getCharactersPage(currentPage++)
            .map {
                if (it.pageInformation.next.isEmpty())
                    viewState.setIsLastPage()
                it.results
            }
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
                updateCharacterList()
            }
    }

    fun onClickedItem(position: Int) =
        router.navigateTo(Screens.CharacterInfoScreen(filteredCharactersList[position]))

    fun onFilterClicked(status: Status?) {
        filterByStatus = status
        viewState.setItems(null)
        updateCharacterList()
    }

    private fun updateCharacterList() {
        filteredCharactersList =
            filterByStatus?.let { charactersList.filter { item -> item.status == it } }
                ?: charactersList.toList()
        viewState.setItems(filteredCharactersList)
    }
}
