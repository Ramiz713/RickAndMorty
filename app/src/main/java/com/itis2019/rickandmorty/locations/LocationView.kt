package com.itis2019.rickandmorty.locations

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis2019.rickandmorty.entities.Location

@StateStrategyType(AddToEndSingleStrategy::class)
interface LocationView : MvpView {
    fun setItems(items: List<Location>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToInfoActivity(location: Location)

    fun setFlagIsLastPage(flag: Boolean)
    fun setFlagIsLoading(flag: Boolean)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
}
