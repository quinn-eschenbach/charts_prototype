package com.example.charts.vitaldaten.di

import android.app.Application
import com.example.charts.vitaldaten.data.ProfileDao
import com.example.charts.vitaldaten.data.ProfileDataSource
import com.example.charts.vitaldaten.data.ProfileRepository
import com.example.charts.vitaldaten.data.ProfileRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {
    private val database: ProfileRoomDatabase = ProfileRoomDatabase.getDatabase(mApplication.applicationContext)

    @Singleton
    @Provides
    fun providesRoomDatabase(): ProfileRoomDatabase {
        return database
    }

    @Singleton
    @Provides
    fun providesProfileDao(database: ProfileRoomDatabase): ProfileDao {
        return database.profileDao()
    }

    @Singleton
    @Provides
    fun productRepository(productDao: ProfileDao): ProfileRepository {
        return ProfileDataSource(productDao)
    }

}