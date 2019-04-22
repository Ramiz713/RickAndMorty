package com.itis2019.rickandmorty

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.itis2019.rickandmorty.ui.locations.LocationAdapter
import com.itis2019.rickandmorty.ui.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationsFragmentTest {

    @get:Rule
    val activity = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.view_pager)).perform(swipeLeft())
    }

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.rv_locations)).check(matches((isDisplayed())))
    }

    @Test
    fun testScrollRecyclerView() {
        onView(withId(R.id.rv_locations))
            .perform(scrollToPosition<LocationAdapter.LocationHolder>(7))
            .perform(scrollToPosition<LocationAdapter.LocationHolder>(5))
            .perform(scrollToPosition<LocationAdapter.LocationHolder>(3))
            .perform(scrollToPosition<LocationAdapter.LocationHolder>(1))
    }
}
