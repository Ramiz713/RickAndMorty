package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.characters.CharacterPresenter
import com.itis2019.rickandmorty.characters.CharacterView
import com.itis2019.rickandmorty.characters.`CharacterView$$State`
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.repository.Repository
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

@RunWith(MockitoJUnitRunner::class)
class CharacterPresenterTest {

    private val TITLE_ERROR = "error"

    @Mock
    lateinit var mockRepository: Repository

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
        val charactersList: ArrayList<Character> = ArrayList()
        doReturn(Single.just(charactersList)).`when`(mockRepository).getCharactersPage(1)

        presenter.attachView(mockView)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).setFlagIsLoading(false)
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        val charactersList: List<Character> = ArrayList()
        doReturn(Single.just(charactersList)).`when`(mockRepository).getCharactersPage(1)

        presenter.onLoadNextPage(1)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).setFlagIsLoading(false)
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val expectedError = Throwable(TITLE_ERROR)
        val charactersList: List<Character> = ArrayList()
        doReturn(Single.error<Character>(expectedError)).`when`(mockRepository).getCharactersPage(1)
        doReturn(charactersList).`when`(mockRepository).getCachedCharacters()

        presenter.onLoadNextPage(1)

        verify(mockViewState).showProgress()
        verify(mockViewState).showError(TITLE_ERROR)
        verify(mockViewState).setItems(charactersList)
        verify(mockViewState).hideProgress()
    }
}
