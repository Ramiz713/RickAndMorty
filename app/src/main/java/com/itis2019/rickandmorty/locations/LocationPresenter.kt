package com.itis2019.rickandmorty.locations

import android.annotation.SuppressLint
import android.app.Application
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.model.AppDatabase
import com.itis2019.rickandmorty.model.RickAndMortyApiService
import com.itis2019.rickandmorty.model.location.Location
import com.itis2019.rickandmorty.subscribeCompletableOnIoObserveOnUi
import com.itis2019.rickandmorty.subscribeSingleOnIoObserveOnUi
import io.reactivex.Completable

@InjectViewState
class LocationPresenter(app: Application?) : MvpPresenter<LocationView>() {
    private var locationList = ArrayList<Location>()
    private val apiService = RickAndMortyApiService.create()
    private val locationDao = AppDatabase.getInstance(app)?.locationDao()

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @SuppressLint("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        apiService.getLocationsList(pageCount)
            .map {
                if (it.info.next.isEmpty())
                    viewState.setFlagIsLastPage(true)
                it.results
            }
            .doOnSuccess {
                viewState.setFlagIsLoading(false)
                if (pageCount == 1) locationDao?.deleteAll()
                locationDao?.insertAll(it)
            }
            .subscribeSingleOnIoObserveOnUi()
            .subscribe(
                {
                    locationList.addAll(it)
                    viewState.setItems(locationList)
                },
                { error -> handleError(error) }
            )
    }

    @SuppressLint("CheckResult")
    private fun handleError(error: Throwable) {
        viewState.showError(error.toString())
        Completable.fromCallable { locationList.addAll(locationDao?.getAll() ?: ArrayList()) }
            .subscribeCompletableOnIoObserveOnUi()
            .subscribe { viewState.setItems(locationList) }
    }
}
