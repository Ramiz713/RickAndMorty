package com.itis2019.rickandmorty.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.widget.ImageView
import com.itis2019.rickandmorty.info.CharacterInfoActivity
import com.itis2019.rickandmorty.model.Character
import com.itis2019.rickandmorty.model.RickAndMortyApiService
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi

class MainPresenter(var mainView: MainContract.View) : MainContract.Presenter {
    private var charactersList = ArrayList<Character>()

    @SuppressLint("CheckResult")
    override fun onViewAttach() {
        RickAndMortyApiService.create().getCharactersList()
            .map { it.results }
            .doOnSuccess {
                charactersList.addAll(it)
            }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { mainView.showProgress() }
            .doAfterTerminate { mainView.hideProgress() }
            .subscribe(
                { mainView.setItems(it) },
                { error -> mainView.showError(error.toString()) }
            )
    }

    override fun onItemClicked(activity: Activity, position: Int, image: ImageView) {
        val intent = Intent(activity, CharacterInfoActivity::class.java)
        intent.putExtra("Character item", charactersList[position])
        val transitionName = ViewCompat.getTransitionName(image) ?: ""
        intent.putExtra("image", transitionName)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, transitionName)
        activity.startActivity(intent, optionsCompat.toBundle())
    }
}
