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

    private val adapter = LocationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_location, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(activity, 2)
        rv_locations.layoutManager = manager
        rv_locations.adapter = adapter

        rv_locations.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage && pastVisibleItems + visibleItemCount >= totalItemCount) {
                        isLoading = true
                        locationPresenter.loadNextPage()
                    }
                }
            }
        })
    }

    override fun setItems(items: List<Location>) = adapter.submitList(items)

    override fun setIsLastPage() {
        isLastPage = true
    }

    override fun setIsNotLoading() {
        isLoading = false
    }

    override fun showProgress() = progress_bar.run { visibility = View.VISIBLE }

    override fun hideProgress() = progress_bar.run { visibility = View.GONE }

    override fun showError(message: String) {
        if (userVisibleHint)
            Snackbar.make(container_locations, message, Snackbar.LENGTH_SHORT).show()
    }
}
