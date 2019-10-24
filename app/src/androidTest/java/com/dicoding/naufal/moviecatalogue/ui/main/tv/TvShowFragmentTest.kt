package com.dicoding.naufal.moviecatalogue.ui.main.tv

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class TvShowFragmentTest {

    @get:Rule
    var activityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    private val tvShowFragment = TvShowFragment()
    private val idling = EspressoIdlingResource.idlingResource

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idling)
        activityRule.activity.setFragment(tvShowFragment)
    }


    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idling)
    }

    @Test
    fun loadFilms() {
        onView(withId(R.id.recycler_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_tv)).check(RecyclerViewItemCountAssertion(20))
    }

}