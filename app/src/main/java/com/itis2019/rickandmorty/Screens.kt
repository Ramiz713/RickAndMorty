package com.itis2019.rickandmorty

import android.content.Context
import android.content.Intent
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.ui.info.CharacterInfoActivity
import com.itis2019.rickandmorty.ui.main.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    data class CharacterInfoScreen(val character: Character) : SupportAppScreen() {

        override fun getActivityIntent(context: Context?): Intent {
            val intent = Intent(context, CharacterInfoActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_CHARACTER_ITEM, character)
            return intent
        }
    }
}
