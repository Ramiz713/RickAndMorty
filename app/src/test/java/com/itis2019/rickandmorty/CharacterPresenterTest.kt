package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.Character
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
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Spy
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

    @InjectMocks
    @Spy
    private lateinit var presenter: CharacterPresenter

    @Before
    fun setUp() {
        Mockito.reset(mockViewState, presenter, mockRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        val mockView = mock(CharacterView::class.java)
        val charactersList = ArrayList<Character>()
        doReturn(Single.just(charactersList)).`when`(mockRepository).getCharactersPage(1)

        presenter.attachView(mockView)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).setItems(charactersList)
        verify(mockViewState, timeout(100)).setFlagIsLoading(false)
        verify(mockViewState, timeout(100)).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        val charactersList = ArrayList<Character>()
        doReturn(Single.just(charactersList)).`when`(mockRepository).getCharactersPage(1)

        presenter.onLoadNextPage(1)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).setItems(charactersList)
        verify(mockViewState, timeout(100)).setFlagIsLoading(false)
        verify(mockViewState, timeout(100)).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val expectedError = Throwable(TITLE_ERROR)
        val charactersList = ArrayList<Character>()
        doReturn(Single.error<Character>(expectedError)).`when`(mockRepository).getCharactersPage(1)
        doReturn(charactersList).`when`(mockRepository).getCachedCharacters()

        presenter.onLoadNextPage(1)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).showError(TITLE_ERROR)
        verify(mockViewState, timeout(100)).setItems(charactersList)
        verify(mockViewState, timeout(100)).hideProgress()
    }
}
