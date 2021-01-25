package com.example.charts.vitaldaten.di

import android.app.Application

class ChartsApp : Application() {
    private var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        getComponent().inject(this)
    }

    fun getComponent(): ApplicationComponent {

        if (component == null) {
            component = DaggerApplicationComponent.factory().create(this)
        }

        return component as ApplicationComponent
    }
}