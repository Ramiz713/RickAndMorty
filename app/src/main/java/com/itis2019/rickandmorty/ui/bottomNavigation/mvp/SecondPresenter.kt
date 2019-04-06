package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class SecondPresenter(private val router: Router) : MvpPresenter<SecondView>() {

    fun onButtonPressed() = router.navigateTo(Screens.FifthFragmentScreen(1))
}
