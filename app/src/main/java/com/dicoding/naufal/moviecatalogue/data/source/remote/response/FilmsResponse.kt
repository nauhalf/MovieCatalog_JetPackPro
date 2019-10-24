package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FilmsResponse(
    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("total_results")
    var totalResults: Int =0,

    @SerializedName("total_pages")
    var totalPages: Int = 0,

    @SerializedName("results")
    var filmResponse: List<FilmResponse>? = null
)