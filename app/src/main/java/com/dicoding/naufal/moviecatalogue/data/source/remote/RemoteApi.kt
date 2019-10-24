package com.dicoding.naufal.moviecatalogue.data.source.remote

import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmsResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MovieResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApi{
    @GET("discover/movie?language=en-US&with_original_language=ja&with_genres=16")
    fun getDiscoveryMovieAsync(@Query("page") page: Int = 1) : Deferred<Response<FilmsResponse>>

    @GET("discover/tv?language=en-US&with_original_language=ja&with_genres=16")
    fun getDiscoveryTvAsync(@Query("page") page: Int = 1) : Deferred<Response<FilmsResponse>>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieAsync(@Path("movie_id") movieId: Int) : Deferred<Response<MovieResponse>>

    @GET("tv/{tv_id}?language=en-US")
    fun getTvAsync(@Path("tv_id") tvId: Int) : Deferred<Response<TvResponse>>

}