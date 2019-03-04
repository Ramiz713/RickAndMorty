package com.itis2019.rickandmorty.locations

import com.arellomobile.mvp.MvpView
import com.itis2019.rickandmorty.model.location.Location

interface LocationView : MvpView {
    fun setItems(items: List<Location>)
    fun navigateToInfoActivity(location: Location)
    fun setFlagIsLastPage(flag: Boolean)
    fun setFlagIsLoading(flag: Boolean)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
}
