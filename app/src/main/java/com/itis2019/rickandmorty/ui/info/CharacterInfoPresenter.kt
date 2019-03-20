package com.itis2019.rickandmorty.ui.info

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class CharacterInfoPresenter : MvpPresenter<CharacterInfoView>() {

    override fun onFirstViewAttach() = viewState.bindData()
}
