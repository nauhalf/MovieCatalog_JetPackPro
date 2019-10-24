package com.dicoding.naufal.moviecatalogue

import com.google.gson.Gson
import java.io.InputStream


inline fun <reified T> Any.parseJsonFromPath(fileName: String): T =
    Gson().fromJson(
        getFileFromPath(
            this,
            fileName
        )
            .bufferedReader().use { it.readText() }, T::class.java
    )

fun getFileFromPath(obj: Any, fileName: String): InputStream {
    val classLoader = obj.javaClass.classLoader
    return classLoader!!.getResourceAsStream(fileName)

}