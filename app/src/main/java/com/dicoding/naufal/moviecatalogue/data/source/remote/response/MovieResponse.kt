package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse (

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("genres")
    var genres: List<GenreResponse>? = null,

    @SerializedName("homepage")
    var homepage: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompanyResponse>? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("runtime")
    var runtime: Int = 0,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,

    @SerializedName("overview")
    var overview: String? = null
)