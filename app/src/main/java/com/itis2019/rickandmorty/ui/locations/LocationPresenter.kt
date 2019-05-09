package com.itis2019.rickandmorty.ui.locations

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.repository.Repository
import kotlinx.coroutines.*

@InjectViewState
class LocationPresenter(private val repository: Repository) : MvpPresenter<LocationView>() {

    private var locationList = ArrayList<Location>()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onFirstViewAttach() = onLoadNextPage(1)

    @SuppressLint("CheckResult")
    fun onLoadNextPage(pageCount: Int) {
        viewState.showProgress()
        scope.launch {
            try {
                val result = repository.getLocationsPageAsync(pageCount).await().results
                locationList.addAll(result)
                if (pageCount != 1) repository.cacheLocations(result)
                else repository.rewriteCacheLocations(result)
            } catch (throwable: Throwable) {
                viewState.showError(throwable.localizedMessage)
                locationList.addAll(repository.getCachedLocations())
            }
            withContext(Dispatchers.Main) {
                viewState.setItems(locationList)
                viewState.hideProgress()
                viewState.setFlagIsLoading(false)
            }
        }
    }
}
