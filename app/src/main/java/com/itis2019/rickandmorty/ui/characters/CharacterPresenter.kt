package com.itis2019.rickandmorty.ui.characters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.CoroutineContextProvider
import com.itis2019.rickandmorty.Screens
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.terrakok.cicerone.Router

@InjectViewState
class CharacterPresenter(
    private val repository: Repository,
    private val router: Router,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) :
    MvpPresenter<CharacterView>() {

    private var charactersList = ArrayList<Character>()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(contextPool.Default + job)

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @Suppress("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        viewState.showProgress()
        scope.launch {
            try {
                val result = repository.getCharactersPageAsync(pageCount).await().results
                charactersList.addAll(result)
                if (pageCount != 1) repository.cacheCharacters(result)
                else repository.rewriteCacheCharacters(result)
            } catch (throwable: Throwable) {
                viewState.showError(throwable.message ?: "")
                charactersList.addAll(repository.getCachedCharacters())
            }
            withContext(contextPool.Main) {
                viewState.setItems(charactersList)
                viewState.hideProgress()
                viewState.setFlagIsLoading(false)
            }
        }
    }

    fun onClickedItem(position: Int) =
        router.navigateTo(Screens.CharacterInfoScreen(charactersList[position]))
}
