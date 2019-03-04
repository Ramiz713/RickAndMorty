package com.itis2019.rickandmorty.characters

import com.arellomobile.mvp.MvpView
import com.itis2019.rickandmorty.model.character.Character

interface CharacterView : MvpView {
    fun setItems(items: List<Character>)
    fun navigateToInfoActivity(character: Character)
    fun setFlagIsLastPage(flag: Boolean)
    fun setFlagIsLoading(flag: Boolean)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
}