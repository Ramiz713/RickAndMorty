package com.itis2019.rickandmorty.ui.locations

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis2019.rickandmorty.entities.Location

@StateStrategyType(AddToEndSingleStrategy::class)
interface LocationView : MvpView {

    fun setItems(items: List<Location>)

    fun setIsLastPage()

    fun setIsNotLoading()

    fun showProgress()

    fun hideProgress()

    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)
}
