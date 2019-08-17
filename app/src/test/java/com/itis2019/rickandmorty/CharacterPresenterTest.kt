package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.*
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.characters.CharacterPresenter
import com.itis2019.rickandmorty.ui.characters.CharacterView
import com.itis2019.rickandmorty.ui.characters.`CharacterView$$State`
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Router

@RunWith(MockitoJUnitRunner::class)
class CharacterPresenterTest {

    private val TITLE_ERROR = "error"
    private val aliveCharacter = Character(
        "", ArrayList(),
        "", 0, "",
        CharacterLocation("", ""),
        "", CharacterLocation("", ""),
        "", Status.ALIVE, "", ""
    )
    private val deadCharacter = Character(
        "", ArrayList(),
        "", 1, "",
        CharacterLocation("", ""),
        "", CharacterLocation("", ""),
        "", Status.DEAD, "", ""
    )
    private val firstPage = Page(PageInformation(1, "", 1, ""), listOf(aliveCharacter))
    private val secondPage = Page(PageInformation(2, "", 1, ""), listOf(deadCharacter))
    private val firstPageCharacters = firstPage.results
    private val secondPageCharacters = secondPage.results


    @Mock
    lateinit var mockRepository: Repository

    @Mock
    lateinit var router: Router

    @Mock
    lateinit var mockViewState: `CharacterView$$State`

    @InjectMocks
    @Spy
    private lateinit var presenter: CharacterPresenter

    @Before
    fun setUp() {
        reset(mockViewState, presenter, mockRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        val mockView = mock(CharacterView::class.java)
        doReturn(Single.just(firstPage)).`when`(mockRepository).getCharactersPage(1)

        presenter.attachView(mockView)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(firstPage.results)
        verify(mockViewState).setIsNotLoading()
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        val mockView = mock(CharacterView::class.java)
        doReturn(Single.just(firstPage)).`when`(mockRepository).getCharactersPage(1)
        doReturn(Single.just(secondPage)).`when`(mockRepository).getCharactersPage(2)

        presenter.attachView(mockView)
        presenter.loadNextPage()

        verify(mockViewState, times(2)).showProgress()
        verify(mockViewState).setItems(firstPage.results)
        verify(mockViewState).setItems(listOf(aliveCharacter, deadCharacter))
        verify(mockViewState, times(2)).setIsNotLoading()
        verify(mockViewState, times(2)).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val expectedError = Throwable(TITLE_ERROR)
        doReturn(Single.error<Character>(expectedError)).`when`(mockRepository).getCharactersPage(1)
        doReturn(firstPage.results).`when`(mockRepository).getCachedCharacters()

        presenter.loadNextPage()

        verify(mockViewState).showProgress()
        verify(mockViewState).showError(TITLE_ERROR)
        verify(mockViewState).setItems(firstPage.results)
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenItemClicked() {
        doReturn(Single.just(firstPage)).`when`(mockRepository).getCharactersPage(1)

        presenter.loadNextPage()
        presenter.onClickedItem(0)

        verify(router, times(1)).navigateTo(Screens.CharacterInfoScreen(aliveCharacter))
    }

    @Test
    fun whenFilterAccepted() {
        val mockView = mock(CharacterView::class.java)
        doReturn(Single.just(firstPage)).`when`(mockRepository).getCharactersPage(1)
        doReturn(Single.just(secondPage)).`when`(mockRepository).getCharactersPage(2)

        presenter.attachView(mockView)
        presenter.loadNextPage()
        presenter.onFilterClicked(Status.DEAD)

        verify(mockViewState, times(2)).showProgress()
        verify(mockViewState).setItems(firstPage.results)
        verify(mockViewState).setItems(listOf(aliveCharacter, deadCharacter))
        verify(mockViewState).setItems(secondPage.results)
        verify(mockViewState, times(2)).setIsNotLoading()
        verify(mockViewState, times(2)).hideProgress()
    }
}
