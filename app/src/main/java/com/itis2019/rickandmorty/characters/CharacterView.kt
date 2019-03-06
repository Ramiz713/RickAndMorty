package com.itis2019.rickandmorty.characters

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis2019.rickandmorty.entities.Character

@StateStrategyType(AddToEndSingleStrategy::class)
interface CharacterView : MvpView {
    fun setItems(items: List<Character>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToInfoActivity(character: Character)
    fun setFlagIsLastPage(flag: Boolean)
    fun setFlagIsLoading(flag: Boolean)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
}
