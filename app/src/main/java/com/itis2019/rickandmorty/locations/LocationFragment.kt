package com.itis2019.rickandmorty.locations

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis2019.rickandmorty.Injection
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Location
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : MvpAppCompatFragment(), LocationView {

    private var isLoading = false
    private var isLastPage = false

    @InjectPresenter
    lateinit var locationPresenter: LocationPresenter

    @ProvidePresenter
    fun provideLocationPresenter(): LocationPresenter =
        LocationPresenter(Injection.provideRickAndMortyRepository(activity?.applicationContext))

    private val adapter = LocationAdapter { position: Int -> }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        val manager = GridLayoutManager(activity, 2)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_locations)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var currentPage = 1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage && pastVisibleItems + visibleItemCount >= totalItemCount) {
                        isLoading = true
                        locationPresenter.onLoadNextPage(++currentPage)
                    }
                }
            }
        })
        return view
    }

    override fun setItems(items: List<Location>) =
        adapter.submitList(items)

    override fun navigateToInfoActivity(location: Location) {
    }

    override fun setFlagIsLastPage(flag: Boolean) {
        isLastPage = flag
    }

    override fun setFlagIsLoading(flag: Boolean) {
        isLoading = flag
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun showError(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }
}
