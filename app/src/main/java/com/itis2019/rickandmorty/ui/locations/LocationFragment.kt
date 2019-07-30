package com.itis2019.rickandmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.itis2019.rickandmorty.App
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Location
import kotlinx.android.synthetic.main.fragment_location.*
import javax.inject.Inject

class LocationFragment : MvpAppCompatFragment(), LocationView {

    private var isLoading = false
    private var isLastPage = false

    @Inject
    @InjectPresenter
    lateinit var locationPresenter: LocationPresenter

    @ProvidePresenter
    fun provideLocationPresenter(): LocationPresenter = locationPresenter

    private val adapter = LocationAdapter { position: Int -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

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

    override fun setIsLastPage() {
        isLastPage = true
    }

    override fun setIsNotLoading() {
        isLoading = false
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
