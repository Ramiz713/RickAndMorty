package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Page
import com.itis2019.rickandmorty.entities.PageInformation
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.characters.CharacterPresenter
import com.itis2019.rickandmorty.ui.characters.CharacterView
import com.itis2019.rickandmorty.ui.characters.`CharacterView$$State`
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Router

@RunWith(MockitoJUnitRunner::class)
class CharacterPresenterTest {

    private val TITLE_ERROR = "error"

    @Mock
    lateinit var mockRepository: Repository

    @Mock
    lateinit var router: Router

    @Mock
    lateinit var mockViewState: `CharacterView$$State`

    private lateinit var presenter: CharacterPresenter

    @Before
    fun setUp() {
        presenter = CharacterPresenter(mockRepository, router, TestContextProvider())
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        runBlocking {
            val mockView = mock(CharacterView::class.java)
            val charactersList = ArrayList<Character>()
            doReturn(GlobalScope.async { Page(PageInformation(0, "", 0, ""), charactersList) })
                .`when`(mockRepository).getCharactersPageAsync(1)

            presenter.attachView(mockView)

            verify(mockViewState).showProgress()
            verify(mockViewState).setItems(charactersList)
            verify(mockViewState).setFlagIsLoading(false)
            verify(mockViewState).hideProgress()
        }
    }

    @Test
    fun whenPageLoadedSuccess() {
        runBlocking {
            val charactersList = ArrayList<Character>()
            doReturn(GlobalScope.async { Page(PageInformation(0, "", 0, ""), charactersList) })
                .`when`(mockRepository).getCharactersPageAsync(2)

            presenter.onLoadNextPage(2)

            verify(mockViewState).showProgress()
            verify(mockViewState).setItems(charactersList)
            verify(mockViewState).setFlagIsLoading(false)
            verify(mockViewState).hideProgress()
        }
    }

    @Test
    fun whenPageLoadedWithError() {
        runBlocking {
            val expectedError = Throwable(TITLE_ERROR)
            val charactersList = ArrayList<Character>()
            doReturn(GlobalScope.async { throw expectedError })
                .`when`(mockRepository).getCharactersPageAsync(1)
            doReturn(charactersList).`when`(mockRepository).getCachedCharacters()

            presenter.onLoadNextPage(1)

            verify(mockViewState).showProgress()
            verify(mockViewState).showError(TITLE_ERROR)
            verify(mockViewState).setItems(charactersList)
            verify(mockViewState).hideProgress()
        }
    }

    @Test
    fun whenItemClicked() {
        runBlocking {
            val charactersList = ArrayList<Character>()
            charactersList.add(Character())
            doReturn(GlobalScope.async { Page(PageInformation(0, "", 0, ""), charactersList) })
                .`when`(mockRepository).getCharactersPageAsync(1)

            presenter.onLoadNextPage(1)
            presenter.onClickedItem(0)

            verify(router, times(1)).navigateTo(Screens.CharacterInfoScreen(Character()))
        }
    }
}
