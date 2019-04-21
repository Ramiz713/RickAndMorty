package com.itis2019.rickandmorty.ui.characters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis2019.rickandmorty.entities.Character

@StateStrategyType(AddToEndSingleStrategy::class)
interface CharacterView : MvpView {
    fun setItems(items: List<Character>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setFlagIsLastPage(flag: Boolean)
    fun setFlagIsLoading(flag: Boolean)
    fun showProgress()
    fun hideProgress()
    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)
}
