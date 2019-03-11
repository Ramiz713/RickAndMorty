package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.main.MainPresenter
import com.itis2019.rickandmorty.main.`MainView$$State`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var mockViewState: `MainView$$State`

    @InjectMocks
    @Spy
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() = presenter.setViewState(mockViewState)

    @Test
    fun whenShowDialogExpected() {
        presenter.paginationSizeItemClicked()

        verify(mockViewState).showDialog()
    }
}
