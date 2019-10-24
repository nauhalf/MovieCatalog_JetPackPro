package com.dicoding.naufal.moviecatalogue.ui.main.movie

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.testing.SingleFragmentActivity
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import com.dicoding.naufal.moviecatalogue.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFragmentTest {
    @get:Rule
    var activityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    private val movieFragment = MovieFragment()
    private val idling = EspressoIdlingResource.idlingResource

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idling)
        activityRule.activity.setFragment(movieFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idling)
    }

    @Test
    fun loadFilms() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.onView(ViewMatchers.withId(R.id.recycler_movie))
            .check(RecyclerViewItemCountAssertion(20))
    }

}