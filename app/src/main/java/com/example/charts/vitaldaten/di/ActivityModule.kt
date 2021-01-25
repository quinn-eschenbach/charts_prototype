package com.example.charts.vitaldaten.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Context) {

    @Provides
    internal fun providesContext(): Context {
        return activity
    }
}