package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MovieResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String? = null,


    @ColumnInfo(name = "homepage")
    var homepage: String? = null,

    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "originalTitle")
    var originalTitle: String? = null,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null,


    @ColumnInfo(name = "releaseDate")
    var releaseDate: String? = null,

    @ColumnInfo(name = "runtime")
    var runtime: Int = 0,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @Ignore
    var genres: List<GenreEntity>? = null,

    @Ignore
    var productionCompanies: List<ProductionCompanyEntity>? = null
) : Parcelable {
    companion object {
        @Ignore
        fun toMovie(r: MovieResponse?): MovieEntity? {
            val movieGenre = r?.genres?.map {
                GenreEntity.toGenre(it)
            }

            val movieProductionCompany = r?.productionCompanies?.map {
                ProductionCompanyEntity.toProductionCompany(it)
            }

            return if (r == null) {
                null
            } else {
                MovieEntity(
                    r.id,
                    r.backdropPath,

                    r.homepage,

                    r.originalLanguage,

                    r.originalTitle,

                    r.posterPath,


                    r.releaseDate,

                    r.runtime,

                    r.status,

                    r.title,

                    r.voteAverage,

                    r.overview,

                    movieGenre,

                    movieProductionCompany
                )
            }

        }
    }
}