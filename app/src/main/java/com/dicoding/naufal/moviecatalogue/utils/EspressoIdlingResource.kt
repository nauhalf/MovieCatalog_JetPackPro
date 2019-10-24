package com.dicoding.naufal.moviecatalogue.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private val name = "GLOBAL"
    val idlingResource = CountingIdlingResource(name)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}