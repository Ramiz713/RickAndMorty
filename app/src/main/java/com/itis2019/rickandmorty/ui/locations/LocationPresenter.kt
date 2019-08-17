package com.itis2019.rickandmorty.ui.locations

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class LocationPresenter(private val repository: Repository) : MvpPresenter<LocationView>() {

    private var locationList = ArrayList<Location>()
    private var currentPage = 1

    override fun onFirstViewAttach() = loadNextPage()

    @SuppressLint("CheckResult")
    fun loadNextPage() {
        repository.getLocationsPage(currentPage++)
            .map {
                if (it.pageInformation.next.isEmpty())
                    viewState.setIsLastPage()
                it.results
            }
            .onErrorResumeNext {
                viewState.showError(it.message ?: "")
                locationList.clear()
                Single.just(repository.getCachedLocations())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .doAfterTerminate { viewState.hideProgress() }
            .doAfterTerminate { viewState.setIsNotLoading() }
            .subscribeBy {
                locationList.addAll(it)
                viewState.setItems(locationList.toList())
            }
    }
}
