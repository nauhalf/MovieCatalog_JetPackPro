package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.GenreResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null
) : Parcelable {
    companion object {
        fun toGenre(r: GenreResponse): GenreEntity {
            return GenreEntity(
                r.id,
                r.name
            )
        }
    }
}