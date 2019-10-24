package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tvs")
data class TvEntity(

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

    @ColumnInfo(name = "originalName")
    var originalName: String? = null,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null,

    @ColumnInfo(name = "firstAirDate")
    var firstAirDate: String? = null,

    @ColumnInfo(name = "episodeRunTime")
    var episodeRunTime: Int = 0,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @Ignore
    var creators: List<CreatorEntity>? = null,

    @Ignore
    var genres: List<GenreEntity>? = null,

    @Ignore
    var productionCompanies: List<ProductionCompanyEntity>? = null
) : Parcelable {
    companion object {
        @Ignore
        fun toTv(r: TvResponse?): TvEntity? {

            val tvCreators = r?.creators?.map {
                CreatorEntity.toCreator(it)
            }

            val tvGenres = r?.genres?.map {
                GenreEntity.toGenre(it)
            }

            val tvProductionCompanies = r?.productionCompanies?.map {
                ProductionCompanyEntity.toProductionCompany(it)
            }

            return if (r == null) {
                null
            } else {
                TvEntity(
                    r.id,
                    r.backdropPath,
                    r.homepage,
                    r.originalLanguage,
                    r.originalName,
                    r.posterPath,
                    r.firstAirDate,
                    r.episodeRunTime?.get(0) ?: 0,
                    r.status,
                    r.title,
                    r.voteAverage,
                    r.overview,
                    tvCreators,
                    tvGenres,
                    tvProductionCompanies
                )
            }
        }
    }
}