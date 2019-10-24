package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "films", primaryKeys = ["id", "type"])
data class FilmEntity(
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "originalName")
    var originalName: String? = null,

    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0,

    //TYPE 1 = MOVIE && TYPE 2 = TV
    //composite key
    @NonNull
    @ColumnInfo(name = "type")
    var type: Int = 1
) : Parcelable {
    companion object {
        @Ignore
        fun toFilmEntity(r: FilmResponse): FilmEntity {
            return FilmEntity(
                r.id,
                r.title,
                r.releaseDate,
                r.overview,
                r.originalName,
                r.originalLanguage,
                r.posterPath,
                r.voteAverage
            )
        }
    }

}