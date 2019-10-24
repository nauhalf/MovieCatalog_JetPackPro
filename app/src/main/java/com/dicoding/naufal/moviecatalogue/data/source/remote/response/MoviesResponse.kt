package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    var movieResponses: List<MovieResponse>? = null
)