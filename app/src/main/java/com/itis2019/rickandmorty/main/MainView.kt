package com.itis2019.rickandmorty.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDialog()
}
