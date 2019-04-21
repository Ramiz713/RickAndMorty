package com.itis2019.rickandmorty.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis2019.rickandmorty.App
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.ui.characters.CharacterFragment
import com.itis2019.rickandmorty.ui.locations.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter = mainPresenter

    companion object {
        const val EXTRA_CHARACTER_ITEM = "Character item"
        const val EXTRA_IMAGE = "Image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tb_movie_list)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.settings -> {
                mainPresenter.onSettingsClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun onBackPressed() {
        mainPresenter.onBackPressed()
    }
}
