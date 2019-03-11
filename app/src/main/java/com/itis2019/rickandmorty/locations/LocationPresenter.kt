package com.itis2019.rickandmorty.locations

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class LocationPresenter(private val repository: Repository) : MvpPresenter<LocationView>() {

    private var locationList = ArrayList<Location>()

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @SuppressLint("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        repository.getLocationsPage(pageCount)
            .doOnSuccess {
                viewState.setFlagIsLoading(false)
                if (pageCount != 1) repository.cacheLocations(it)
                else repository.rewriteCacheLocations(it)
            }
            .onErrorResumeNext {
                viewState.showError(it.message ?: "")
                Single.just(repository.getCachedLocations())
            }
            .subscribeSingleOnIoObserveOnUi()
            .doOnSubscribe { viewState.showProgress() }
            .doAfterTerminate { viewState.hideProgress() }
            .subscribeBy {
                locationList.addAll(it)
                viewState.setItems(locationList)
            }
    }
}
