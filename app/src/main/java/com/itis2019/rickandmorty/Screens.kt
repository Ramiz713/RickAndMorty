package com.itis2019.rickandmorty

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.CharacterLocation
import com.itis2019.rickandmorty.ui.bottomNavigation.*
import com.itis2019.rickandmorty.ui.info.CharacterInfoActivity
import com.itis2019.rickandmorty.ui.main.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent =
            Intent(context, MainActivity::class.java)
    }

    data class CharacterInfoScreen(val character: Character) : SupportAppScreen() {
        constructor() : this(
            Character(
                "", ArrayList(),
                "", 0, "",
                CharacterLocation("", ""),
                "", CharacterLocation("", ""),
                "", "", "", ""
            ) //kostyl :C
        )

        override fun getActivityIntent(context: Context?): Intent {
            val intent = Intent(context, CharacterInfoActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_CHARACTER_ITEM, character)
            return intent
        }
    }

    object SettingsScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent =
            Intent(context, BottomNavigationActivity::class.java)
    }

    object FirstFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = FirstFragment()
    }

    object SecondFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SecondFragment()
    }

    object ThirdFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = ThirdFragment()
    }

    object FourthFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = FourthFragment()
    }

    data class FifthFragmentScreen(val count: Int) : SupportAppScreen() {
        override fun getFragment(): Fragment = FifthFragment.newInstance(count)
    }

    object SixthFragmentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SixthFragment()
    }
}
