package com.example.charts.vitaldaten.di

import com.example.charts.vitaldaten.data.ProfileDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ProfileDaoModule {
    @Singleton
    @Binds
    abstract fun bindProfileDao(profileDao: ProfileDao): ProfileDao
}