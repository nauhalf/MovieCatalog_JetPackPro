package com.dicoding.naufal.moviecatalogue.ui.detail.tv

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvsResponse
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import com.dicoding.naufal.moviecatalogue.utils.parseJsonFromRes
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailTvShowActivityTest {


    private val dummyTv by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext.parseJsonFromRes<TvsResponse>(
            R.raw.detail_tv
        ).tvResponses?.get(10) as TvResponse
    }

    private val idling = EspressoIdlingResource.idlingResource

    @get:Rule
    var activityRule: ActivityTestRule<DetailTvShowActivity> =
        object : ActivityTestRule<DetailTvShowActivity>(DetailTvShowActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return DetailTvShowActivity.newIntent(targetContext, dummyTv.id)
            }
        }


    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(idling)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(idling)
    }

    @Test
    fun loadTv(){
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(withText(dummyTv.title)))
        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyTv.overview)))
    }
}