package com.example.charts.vitaldaten.di

import android.content.Context
import androidx.room.Room
import com.example.charts.vitaldaten.data.ProfileDao
import com.example.charts.vitaldaten.data.ProfileDataSource
import com.example.charts.vitaldaten.data.ProfileRepository
import com.example.charts.vitaldaten.data.ProfileRoomDatabase
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(appContex: Context): ProfileRoomDatabase =
        Room.databaseBuilder(
            appContex,
            ProfileRoomDatabase::class.java,
            "ProfileRoomDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: ProfileRoomDatabase): ProfileDao =
        movieDatabase.profileDao()

    @Singleton
    @Provides
    fun productRepository(productDao: ProfileDao): ProfileRepository {
        return ProfileDataSource(productDao)
    }
}