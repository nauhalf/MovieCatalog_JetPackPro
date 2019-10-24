package com.dicoding.naufal.moviecatalogue.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "5c0e91774500f6eb45986979bbe7914c"

    private val interceptor = Interceptor {
        val url = it.request().url.newBuilder().addQueryParameter("api_key",
            API_KEY
        )
            .build()
        val request = it.request()
            .newBuilder()
            .url(url)
            .build()

        it.proceed(request)
    }

    private val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    fun api(): RemoteApi {
        return Retrofit.Builder().client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RemoteApi::class.java)
    }
}