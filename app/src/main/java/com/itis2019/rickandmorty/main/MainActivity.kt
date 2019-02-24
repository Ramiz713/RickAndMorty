package com.itis2019.rickandmorty.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.info.CharacterInfoActivity
import com.itis2019.rickandmorty.model.Character
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter = MainPresenter(this)
    private val adapter = CharacterAdapter { position: Int, image: ImageView
        ->
        navigateToInfoActivity(presenter.getOnClickedItem(position), image)
    }

    companion object {
        const val EXTRA_CHARACTER_ITEM = "Character item"
        const val EXTRA_IMAGE = "Image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_characters.layoutManager = GridLayoutManager(this, 3)
        rv_characters.adapter = adapter
        presenter.onViewAttach()
    }

    private fun navigateToInfoActivity(character: Character, image: ImageView) {
        val intent = Intent(this, CharacterInfoActivity::class.java)
        intent.putExtra(EXTRA_CHARACTER_ITEM, character)
        val transitionName = ViewCompat.getTransitionName(image) ?: ""
        intent.putExtra(EXTRA_IMAGE, transitionName)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image, transitionName)
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun setItems(items: List<Character>) =
        adapter.submitList(items)

    override fun showError(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
