package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class FirstPresenter(private val router: Router) : MvpPresenter<FirstView>() {

    fun onButtonPressed() = router.navigateTo(Screens.FourthFragmentScreen)
}
