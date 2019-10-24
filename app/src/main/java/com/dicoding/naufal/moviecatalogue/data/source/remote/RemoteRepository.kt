package com.dicoding.naufal.moviecatalogue.data.source.remote

import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MovieResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvResponse

class RemoteRepository constructor(private val api: RemoteApi) : BaseRepository() {
    suspend fun getDiscoveryMovie(page: Int = 1): ApiResponse<List<FilmResponse>> = safeApiCall {
        val response = api.getDiscoveryMovieAsync(page).await()
        return@safeApiCall if (response.isSuccessful) {
            val data = response.body()?.filmResponse
            ApiResponse.success(data)
        } else {
            ApiResponse.error(response.errorBody().toString(), emptyList())

        }
    }

    suspend fun getDiscoveryTv(page: Int = 1): ApiResponse<List<FilmResponse>> = safeApiCall {

        val response = api.getDiscoveryTvAsync(page).await()
        return@safeApiCall if (response.isSuccessful) {
            val data = response.body()?.filmResponse
            ApiResponse.success(data)
        } else {
            ApiResponse.error(response.errorBody().toString(), emptyList())

        }
    }


    suspend fun getTv(tvId: Int): ApiResponse<TvResponse> = safeApiCall {
        val response = api.getTvAsync(tvId).await()
        var data: TvResponse? = null
        return@safeApiCall if (response.isSuccessful) {
            data = response.body()
            ApiResponse.success(data)
        } else {
            ApiResponse.error(response.errorBody().toString(), data)
        }
    }

    suspend fun getMovie(movieId: Int): ApiResponse<MovieResponse> = safeApiCall {

        val response = api.getMovieAsync(movieId).await()
        var data: MovieResponse? = null
        return@safeApiCall if (response.isSuccessful) {
            data = response.body()
            ApiResponse.success(data)
        } else {
            ApiResponse.error(response.errorBody().toString(), data)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(api: RemoteApi): RemoteRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = RemoteRepository(api)
                INSTANCE = instance
                return instance
            }
        }
    }
}