package com.itis2019.rickandmorty.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import com.itis2019.rickandmorty.entities.Status
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
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_character, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(activity, 2)
        rv_characters.layoutManager = manager
        rv_characters.adapter = adapter

        rv_characters.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val pastVisibleItems = manager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage && pastVisibleItems + visibleItemCount >= totalItemCount) {
                        isLoading = true
                        characterPresenter.loadNextPage()
                    }
                }
            }
        })
    }

    override fun onResume() {
        navigatorHolder.setNavigator(getNavigator())
        super.onResume()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_characters, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.alive -> {
                characterPresenter.onFilterClicked(Status.ALIVE)
                true
            }
            R.id.dead -> {
                characterPresenter.onFilterClicked(Status.DEAD)
                true
            }
            R.id.unknown -> {
                characterPresenter.onFilterClicked(Status.UNKNOWN)
                true
            }
            R.id.all -> {
                characterPresenter.onFilterClicked(null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun setItems(items: List<Character>?) = adapter.submitList(items)

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
            Snackbar.make(container_characters, message, Snackbar.LENGTH_LONG).show()
    }

    private fun getNavigator(): Navigator =
        object : SupportAppNavigator(activity, childFragmentManager, R.id.container_characters) {

            override fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? =
                if ((command as Forward).screen is Screens.CharacterInfoScreen) {
                    val transitionName = ViewCompat.getTransitionName(imageView) ?: ""
                    activityIntent.putExtra(MainActivity.EXTRA_IMAGE, transitionName)
                    val optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity as AppCompatActivity, imageView, transitionName)
                    optionsCompat.toBundle() ?: Bundle()
                } else null
        }
}
