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
    private val character = Character(
        "", ArrayList(),
        "", 0, "",
        CharacterLocation("", ""),
        "", CharacterLocation("", ""),
        "", Status.ALIVE, "", ""
    )
    private val charactersList = listOf(character)
    private val page = Page(PageInformation(1, "", 1, ""), charactersList)


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
        doReturn(Single.just(page)).`when`(mockRepository).getCharactersPage(1)

        presenter.attachView(mockView)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).setIsNotLoading()
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        doReturn(Single.just(page)).`when`(mockRepository).getCharactersPage(2)

        presenter.onLoadNextPage(2)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).setIsNotLoading()
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val expectedError = Throwable(TITLE_ERROR)
        doReturn(Single.error<Character>(expectedError)).`when`(mockRepository).getCharactersPage(2)
        doReturn(charactersList).`when`(mockRepository).getCachedCharacters()

        presenter.onLoadNextPage(2)

        verify(mockViewState).showProgress()
        verify(mockViewState).showError(TITLE_ERROR)
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenItemClicked() {
        doReturn(Single.just(page)).`when`(mockRepository).getCharactersPage(1)

        presenter.onLoadNextPage(1)
        presenter.onClickedItem(0)

        verify(router, times(1)).navigateTo(Screens.CharacterInfoScreen(character))
    }
}
