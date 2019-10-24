package com.dicoding.naufal.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse (

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("created_by")
    var creators: List<CreatorResponse>? = null,

    @SerializedName("genreResponses")
    var genres: List<GenreResponse>? = null,

    @SerializedName("homepage")
    var homepage: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_name")
    var originalName: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompanyResponse>? = null,

    @SerializedName("first_air_date")
    var firstAirDate: String? = null,

    @SerializedName("episode_run_time")
    var episodeRunTime: List<Int>? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("name")
    var title: String? = null,

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,

    @SerializedName("overview")
    var overview: String? = null
)