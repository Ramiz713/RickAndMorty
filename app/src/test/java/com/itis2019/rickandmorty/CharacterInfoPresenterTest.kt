package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.info.CharacterInfoPresenter
import com.itis2019.rickandmorty.info.CharacterInfoView
import com.itis2019.rickandmorty.info.`CharacterInfoView$$State`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterInfoPresenterTest {

    @Mock
    lateinit var mockViewState: `CharacterInfoView$$State`

    @InjectMocks
    @Spy
    private lateinit var presenter: CharacterInfoPresenter

    @Before
    fun setUp() = presenter.setViewState(mockViewState)

    @Test
    fun whenPageLoadedSuccess() {
        val mockView = mock(CharacterInfoView::class.java)

        presenter.attachView(mockView)

        verify(mockViewState).bindData()
    }
}
