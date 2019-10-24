package com.dicoding.naufal.moviecatalogue.ui.detail.movie

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MovieResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MoviesResponse
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import com.dicoding.naufal.moviecatalogue.utils.parseJsonFromRes
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailMovieActivityTest {
    private val dummyMovie by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext.parseJsonFromRes<MoviesResponse>(
            R.raw.detail_movie
        ).movieResponses?.get(10) as MovieResponse
    }

    private val idling = EspressoIdlingResource.idlingResource

    @get:Rule
    var activityRule: ActivityTestRule<DetailMovieActivity> =
        object : ActivityTestRule<DetailMovieActivity>(DetailMovieActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return DetailMovieActivity.newIntent(targetContext, dummyMovie.id)
            }
        }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idling)
    }

    @After
    fun tearDown() {

        IdlingRegistry.getInstance().unregister(idling)
    }

    @Test
    fun loadTv() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.txt_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyMovie.title)))
        Espresso.onView(ViewMatchers.withId(R.id.txt_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.txt_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyMovie.overview)))
    }
}