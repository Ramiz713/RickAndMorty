package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

class FourthPresenter(private val router: Router) : MvpPresenter<FourthView>() {

    fun onButtonPressed() = router.finishChain()
}
