package com.itis2019.rickandmorty.ui.bottomNavigation.mvp

import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.Screens
import ru.terrakok.cicerone.Router

class BottomPresenter(private val router: Router) : MvpPresenter<BottomView>() {

    fun onHomePressed() = router.newRootChain(Screens.FirstFragmentScreen)

    fun onDashboardPressed() = router.newRootChain(Screens.SecondFragmentScreen)

    fun onNotificationsPressed() = router.newRootChain(Screens.ThirdFragmentScreen)

    fun onBackPressed() = router.exit()
}
