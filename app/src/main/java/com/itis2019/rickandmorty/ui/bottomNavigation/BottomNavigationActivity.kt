package com.itis2019.rickandmorty.ui.bottomNavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis2019.rickandmorty.App
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.BottomPresenter
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.BottomView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class BottomNavigationActivity : AppCompatActivity(), BottomView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    @InjectPresenter
    lateinit var presenter: BottomPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val navigator = SupportAppNavigator(this, R.id.container_main)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                presenter.onHomePressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                presenter.onDashboardPressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                presenter.onNotificationsPressed()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        App.component.inject(this)
        presenter.onHomePressed()
        bnv_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResumeFragments() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)

    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
