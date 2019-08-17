package com.itis2019.rickandmorty.ui.main

import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(private val router: Router) : MvpPresenter<MainView>() {

    fun onBackPressed() = router.exit()
}
