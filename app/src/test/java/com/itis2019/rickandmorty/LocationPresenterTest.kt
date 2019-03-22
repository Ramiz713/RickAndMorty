package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.Location
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
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationPresenterTest {

    private val TITLE_ERROR = "error"

    @Mock
    lateinit var mockRepository: Repository

    @Mock
    lateinit var mockViewState: `LocationView$$State`

    @InjectMocks
    @Spy
    private lateinit var presenter: LocationPresenter

    @Before
    fun setUp() {
        Mockito.reset(mockViewState, presenter, mockRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        val mockView = mock(LocationView::class.java)
        val locationsList: ArrayList<Location> = ArrayList()
        doReturn(Single.just(locationsList)).`when`(mockRepository).getLocationsPage(1)

        presenter.attachView(mockView)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).setItems(locationsList)
        verify(mockViewState, timeout(100)).setFlagIsLoading(false)
        verify(mockViewState, timeout(100)).hideProgress()
    }

    @Test
    fun whenPageLoadedSuccess() {
        val locationsList: ArrayList<Location> = ArrayList()
        doReturn(Single.just(locationsList)).`when`(mockRepository).getLocationsPage(2)

        presenter.onLoadNextPage(2)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).setItems(locationsList)
        verify(mockViewState, timeout(100)).setFlagIsLoading(false)
        verify(mockViewState, timeout(100)).hideProgress()
    }

    @Test
    fun whenPageLoadedWithError() {
        val locationsList: ArrayList<Location> = ArrayList()
        val expectedError = Throwable(TITLE_ERROR)
        doReturn(Single.error<Location>(expectedError)).`when`(mockRepository).getLocationsPage(1)
        doReturn(locationsList).`when`(mockRepository).getCachedLocations()

        presenter.onLoadNextPage(1)

        verify(mockViewState, timeout(100)).showProgress()
        verify(mockViewState, timeout(100)).showError(TITLE_ERROR)
        verify(mockViewState, timeout(100)).setItems(locationsList)
        verify(mockViewState, timeout(100)).hideProgress()
    }
}
