package com.itis2019.rickandmorty.characters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.info.CharacterInfoActivity
import com.itis2019.rickandmorty.main.MainActivity
import com.itis2019.rickandmorty.main.MainActivity.Companion.APP_PREFERENCES
import com.itis2019.rickandmorty.main.MainActivity.Companion.EXTRA_INTERVAL_BETWEEN_PAGES
import com.itis2019.rickandmorty.model.character.Character
import kotlinx.android.synthetic.main.fragment_character.*

class CharacterFragment : MvpAppCompatFragment(), CharacterView {

    private lateinit var imageView: ImageView
    private var isLoading = false
    private var isLastPage = false

    @InjectPresenter
    lateinit var characterPresenter: CharacterPresenter

    @ProvidePresenter
    fun provideCharacterPresenter(): CharacterPresenter =
        CharacterPresenter(activity?.application)

    private val adapter = CharacterAdapter { position: Int, image: ImageView ->
        imageView = image
        characterPresenter.onClickedItem(position)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        val manager = GridLayoutManager(activity, 3)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_characters)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var currentPage = 1
            private var defaultInterval = 1
            private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
                defaultInterval = sharedPreferences.getInt(EXTRA_INTERVAL_BETWEEN_PAGES, 0)
            }
            private val sharedPreferences = activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                ?.registerOnSharedPreferenceChangeListener(listener)

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val pastVisibleItems = manager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage && pastVisibleItems + visibleItemCount >= totalItemCount) {
                    currentPage += defaultInterval
                    isLoading = true
                    characterPresenter.onLoadNextPage(currentPage)
                }
            }
        })
        return view
    }

    override fun navigateToInfoActivity(character: Character) {
        val intent = Intent(activity, CharacterInfoActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_CHARACTER_ITEM, character)
        val transitionName = ViewCompat.getTransitionName(imageView) ?: ""
        intent.putExtra(MainActivity.EXTRA_IMAGE, transitionName)
        val optionsCompat = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity as AppCompatActivity, imageView, transitionName)
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


    override fun setFlagIsLastPage(flag: Boolean) {
        isLastPage = flag
    }

    override fun setFlagIsLoading(flag: Boolean) {
        isLoading = flag
    }

    override fun showError(message: String) =
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}
