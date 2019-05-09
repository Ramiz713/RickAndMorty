package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import com.itis2019.rickandmorty.entities.PageInformation
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.locations.LocationPresenter
import com.itis2019.rickandmorty.ui.locations.LocationView
import com.itis2019.rickandmorty.ui.locations.`LocationView$$State`
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationPresenterTest {

    private val TITLE_ERROR = "error"

    @Mock
    lateinit var mockRepository: Repository

    @Mock
    lateinit var mockViewState: `LocationView$$State`

    private lateinit var presenter: LocationPresenter

    @Before
    fun setUp() {
        presenter = LocationPresenter(mockRepository, TestContextProvider())
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttach() {
        runBlocking {
            val mockView = mock(LocationView::class.java)
            val locationsList = ArrayList<Location>()
            doReturn(GlobalScope.async { Page(PageInformation(0, "", 0, ""), locationsList) })
                .`when`(mockRepository).getLocationsPageAsync(1)

            presenter.attachView(mockView)

            verify(mockViewState).showProgress()
            verify(mockViewState).setItems(locationsList)
            verify(mockViewState).setFlagIsLoading(false)
            verify(mockViewState).hideProgress()
        }
    }

    @Test
    fun whenPageLoadedSuccess() {
        runBlocking {
            val locationsList = ArrayList<Location>()
            doReturn(GlobalScope.async { Page(PageInformation(0, "", 0, ""), locationsList) })
                .`when`(mockRepository).getLocationsPageAsync(2)
            presenter.onLoadNextPage(2)

            verify(mockViewState).showProgress()
            verify(mockViewState).setItems(locationsList)
            verify(mockViewState).setFlagIsLoading(false)
            verify(mockViewState).hideProgress()
        }
    }

    @Test
    fun whenPageLoadedWithError() {
        runBlocking {
            val expectedError = Throwable(TITLE_ERROR)
            val locationsList = ArrayList<Location>()
            doReturn(GlobalScope.async { throw expectedError })
                .`when`(mockRepository).getLocationsPageAsync(1)
            doReturn(locationsList).`when`(mockRepository).getCachedLocations()

            presenter.onLoadNextPage(1)

            verify(mockViewState).showProgress()
            verify(mockViewState).showError(TITLE_ERROR)
            verify(mockViewState).setItems(locationsList)
            verify(mockViewState).hideProgress()
        }
    }
}
