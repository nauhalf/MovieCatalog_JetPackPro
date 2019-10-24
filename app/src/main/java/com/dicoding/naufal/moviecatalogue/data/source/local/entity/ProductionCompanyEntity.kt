package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.ProductionCompanyResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "productionCompanies")
data class ProductionCompanyEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "logoPath")
    var logoPath: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "originCountry")
    var originCountry: String? = null
) : Parcelable {
    companion object {
        fun toProductionCompany(r: ProductionCompanyResponse): ProductionCompanyEntity {
            return ProductionCompanyEntity(
                r.id,
                r.logoPath,
                r.name,
                r.originCountry
            )
        }
    }
}