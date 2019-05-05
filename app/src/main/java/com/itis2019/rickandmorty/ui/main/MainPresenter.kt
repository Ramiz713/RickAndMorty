package com.itis2019.rickandmorty.ui.main

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class MainPresenter(private val router: Router) : MvpPresenter<MainView>() {

    fun onSettingsClicked() = router.navigateTo(Screens.SettingsScreen)

    fun onBackPressed() =
        router.exit()
}
