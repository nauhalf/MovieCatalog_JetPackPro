package com.dicoding.naufal.moviecatalogue.data.source.remote

import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any?> safeApiCall(call: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return try {
            call.invoke()
        } catch (e: IOException) {
            ApiResponse.error("Failed to connect to the internet", null)
        } catch (e: Exception) {
            ApiResponse.error("Something wrong happened", null)
        }
    }
}