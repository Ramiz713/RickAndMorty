package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class FifthPresenter(private val router: Router) : MvpPresenter<FifthView>() {

    fun onButtonPressed(count: Int) = router.navigateTo(Screens.FifthFragmentScreen(count))
}
