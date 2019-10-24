package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.CreatorResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "creators")
data class CreatorEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null
) : Parcelable {
    companion object {
        @Ignore
        fun toCreator(r: CreatorResponse): CreatorEntity {
            return CreatorEntity(
                r.id,
                r.name
            )
        }
    }
}