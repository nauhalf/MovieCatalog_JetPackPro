package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String? = null
)