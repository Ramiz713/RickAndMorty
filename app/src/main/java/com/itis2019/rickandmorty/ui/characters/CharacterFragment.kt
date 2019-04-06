package com.itis2019.rickandmorty.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.itis2019.rickandmorty.App
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.Screens
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_character.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import javax.inject.Inject

class CharacterFragment : MvpAppCompatFragment(), CharacterView {

    private lateinit var imageView: ImageView
    private var isLoading = false
    private var isLastPage = false

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    @InjectPresenter
    lateinit var characterPresenter: CharacterPresenter

    @ProvidePresenter
    fun provideCharacterPresenter(): CharacterPresenter = characterPresenter

    private val adapter = CharacterAdapter { position: Int, image: ImageView ->
        imageView = image
        characterPresenter.onClickedItem(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        val manager = GridLayoutManager(activity, 3)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_characters)
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
                        characterPresenter.onLoadNextPage(++currentPage)
                    }
                }
            }
        })
        return view
    }

    override fun onResume() {
        navigatorHolder.setNavigator(getNavigator())
        super.onResume()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
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

    override fun showError(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }

    private fun getNavigator(): Navigator =
        object : SupportAppNavigator(activity, childFragmentManager, R.id.container_characters) {

            override fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle {
                val forward = command as Forward
                if (forward.screen.screenKey == Screens.CharacterInfoScreen(Character()).screenKey) {
                    val transitionName = ViewCompat.getTransitionName(imageView) ?: ""
                    activityIntent.putExtra(MainActivity.EXTRA_IMAGE, transitionName)
                    val optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity as AppCompatActivity, imageView, transitionName)
                    return optionsCompat.toBundle() ?: Bundle()
                }
                return Bundle()
            }
        }
}
