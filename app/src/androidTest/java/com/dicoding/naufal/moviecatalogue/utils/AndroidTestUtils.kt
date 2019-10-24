package com.dicoding.naufal.moviecatalogue.utils

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson


inline fun <reified T> Context.parseJsonFromRes(@RawRes resourceId: Int): T =
    Gson().fromJson(
        resources.openRawResource(resourceId).bufferedReader().use { it.readText() },
        T::class.java
    )