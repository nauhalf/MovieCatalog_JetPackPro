package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvsResponse(
    @SerializedName("results")
    var tvResponses: List<TvResponse>? = null
)