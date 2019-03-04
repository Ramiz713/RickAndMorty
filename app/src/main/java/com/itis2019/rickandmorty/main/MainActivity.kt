package com.itis2019.rickandmorty.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.characters.CharacterFragment
import com.itis2019.rickandmorty.locations.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    companion object {
        const val EXTRA_CHARACTER_ITEM = "Character item"
        const val EXTRA_IMAGE = "Image"
    }

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager(view_pager)
        tabs.setupWithViewPager(view_pager)
    }

    private fun setViewPager(pager: ViewPager) {
        val adapter = TabPagerAdapter(supportFragmentManager)
        val fragmentOne = CharacterFragment()
        val fragmentTwo = LocationFragment()
        adapter.addFragment(fragmentOne, "Characters")
        adapter.addFragment(fragmentTwo, "Locations")
        pager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.pagination_size -> {
                mainPresenter.paginationSizeItemClicked()
                false
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun showDialog() =
        AlertDialog.Builder(this)
            .setTitle("Pagination size")
            .setView(R.layout.dialog_pagination_size)
            .setMessage("You can change the size of pagination")
            .setPositiveButton("Confirm", null)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
}
