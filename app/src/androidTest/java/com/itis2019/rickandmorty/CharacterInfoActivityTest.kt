package com.itis2019.rickandmorty

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.CharacterLocation
import com.itis2019.rickandmorty.entities.Status
import com.itis2019.rickandmorty.ui.info.CharacterInfoActivity
import com.itis2019.rickandmorty.ui.main.MainActivity.Companion.EXTRA_CHARACTER_ITEM
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterInfoActivityTest {

    @get:Rule
    val activity = ActivityTestRule(CharacterInfoActivity::class.java, false, false)

    @Test
    fun displayTextViews() {
        launchActivity()

        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_species)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_gender)).check(matches((isDisplayed())))
        onView(withId(R.id.tv_origin)).check(matches((isDisplayed())))
        onView(withId(R.id.tv_location)).check(matches((isDisplayed())))
    }

    private fun launchActivity() {
        val intent = Intent()
        val character = Character(
            "", ArrayList(),
            "", 0, "",
            CharacterLocation("", ""),
            "", CharacterLocation("", ""),
            "", Status.ALIVE, "", ""
        )
        intent.putExtra(EXTRA_CHARACTER_ITEM, character)
        activity.launchActivity(intent)
    }
}
