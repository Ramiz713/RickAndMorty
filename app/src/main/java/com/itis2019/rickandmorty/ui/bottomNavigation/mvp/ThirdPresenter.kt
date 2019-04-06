package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class ThirdPresenter(private val router: Router) : MvpPresenter<ThirdView>() {

    fun onButtonPressed() = router.navigateTo(Screens.SixthFragmentScreen)
}
