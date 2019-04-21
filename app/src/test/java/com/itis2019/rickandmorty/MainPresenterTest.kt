package com.itis2019.rickandmorty

import com.itis2019.rickandmorty.ui.main.MainPresenter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Router

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @InjectMocks
    @Spy
    private lateinit var presenter: MainPresenter

    @Mock
    private lateinit var router: Router

    @Test
    fun whenSettingsPressed() {
        presenter.onSettingsClicked()

        verify(router, times(1)).navigateTo(Screens.SettingsScreen)
    }

    @Test
    fun whenBackPressed() {
        presenter.onBackPressed()

        verify(router, times(1)).exit()
    }
}
