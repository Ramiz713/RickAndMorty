package com.itis2019.rickandmorty.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.characters.CharacterFragment
import com.itis2019.rickandmorty.locations.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    companion object {
        const val EXTRA_CHARACTER_ITEM = "Character item"
        const val EXTRA_INTERVAL_BETWEEN_PAGES = "Interval"
        const val APP_PREFERENCES = "PaginationInfo"
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

    override fun showDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Pagination size")
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        dialog.setView(editText)
            .setMessage("You can set the interval between pages(for characters)")
            .setPositiveButton("Confirm") { dialogInterface: DialogInterface, _: Int ->
                savePageNumber(dialogInterface, editText)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun savePageNumber(dialogInterface: DialogInterface, editText: EditText) {
        val number = editText.text.toString().toInt()
        val paginationInfo = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        if (number != 0)
            paginationInfo.edit().putInt(EXTRA_INTERVAL_BETWEEN_PAGES, number).apply()
        dialogInterface.dismiss()
    }
}
