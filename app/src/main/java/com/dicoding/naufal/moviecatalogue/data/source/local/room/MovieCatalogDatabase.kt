package com.dicoding.naufal.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.*
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.*
import com.dicoding.naufal.moviecatalogue.data.source.local.room.dao.*

@Database(
    entities = [CreatorEntity::class,
        FavoriteEntity::class,
        FilmEntity::class,
        GenreEntity::class,
        MovieEntity::class,
        ProductionCompanyEntity::class,
        TvEntity::class,
        MovieGenreJoin::class,
        MovieProductionCompanyJoin::class,
        TvCreatorJoin::class,
        TvGenreJoin::class,
        TvProductionCompanyJoin::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogDatabase : RoomDatabase() {

    abstract fun movieCatalogDao(): MovieCatalogDao
    abstract fun movieGenreJoinDao(): MovieGenreJoinDao
    abstract fun movieProductionCompanyJoinDao(): MovieProductionCompanyJoinDao
    abstract fun tvCreatorJoinDao(): TvCreatorJoinDao
    abstract fun tvGenreJoinDao(): TvGenreJoinDao
    abstract fun tvProductionCompanyJoinDao(): TvProductionCompanyJoinDao

    companion object {
        @Volatile
        private var INSTANCE: MovieCatalogDatabase? = null

        fun getDatabase(context: Context): MovieCatalogDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieCatalogDatabase::class.java,
                    "MovieCatalogDatabase.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}