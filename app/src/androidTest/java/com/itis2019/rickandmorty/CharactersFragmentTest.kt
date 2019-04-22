package com.itis2019.rickandmorty

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.itis2019.rickandmorty.ui.characters.CharacterAdapter
import com.itis2019.rickandmorty.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharactersFragmentTest {

    @get:Rule
    val activity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.rv_characters)).check(matches((isDisplayed())))
    }

    @Test
    fun testScrollRecyclerView() {
        onView(withId(R.id.rv_characters))
            .perform(scrollToPosition<CharacterAdapter.CharacterHolder>(7))
            .perform(scrollToPosition<CharacterAdapter.CharacterHolder>(5))
            .perform(scrollToPosition<CharacterAdapter.CharacterHolder>(3))
            .perform(scrollToPosition<CharacterAdapter.CharacterHolder>(1))
    }

    @Test
    fun testClickOnItem() {
        onView(withId(R.id.rv_characters))
            .perform(actionOnItemAtPosition<CharacterAdapter.CharacterHolder>(3, click()))
    }
}
