package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import com.itis2019.rickandmorty.entities.PageInformation
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.locations.LocationPresenter
import com.itis2019.rickandmorty.ui.locations.LocationView
import com.itis2019.rickandmorty.ui.locations.`LocationView$$State`
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

@RunWith(MockitoJUnitRunner::class)
class LocationPresenterTest {

    private val TITLE_ERROR = "error"
    private val location = Location("", "", 0, "", emptyList(), "", "")
    private val locationsList = listOf(location)
    private val page = Page(PageInformation(1, "", 1, ""), locationsList)

    @Mock
    lateinit var mockRepository: Repository

    @Mock
    lateinit var mockViewState: `LocationView$$State`

    @InjectMocks
    @Spy
    private lateinit var presenter: LocationPresenter

    @Before
    fun setUp() {
        reset(mockViewState, presenter, mockRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        val mockView = mock(LocationView::class.java)
        doReturn(Single.just(page)).`when`(mockRepository).getLocationsPage(1)

        presenter.attachView(mockView)

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(locationsList)
        verify(mockViewState).setIsNotLoading()
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        doReturn(Single.just(page)).`when`(mockRepository).getLocationsPage(1)

        presenter.loadNextPage()

        verify(mockViewState).showProgress()
        verify(mockViewState).setItems(locationsList)
        verify(mockViewState).setIsNotLoading()
        verify(mockViewState).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val expectedError = Throwable(TITLE_ERROR)
        doReturn(Single.error<Location>(expectedError)).`when`(mockRepository).getLocationsPage(1)
        doReturn(locationsList).`when`(mockRepository).getCachedLocations()

        presenter.loadNextPage()

        verify(mockViewState).showProgress()
        verify(mockViewState).showError(TITLE_ERROR)
        verify(mockViewState).setItems(locationsList)
        verify(mockViewState).hideProgress()
    }
}
