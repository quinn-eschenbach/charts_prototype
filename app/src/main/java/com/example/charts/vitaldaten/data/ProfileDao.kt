package com.example.charts.vitaldaten.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.charts.vitaldaten.di.ProfileDaoModule
import dagger.Component
import dagger.Provides
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Singleton


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table")
    fun getProfiles(): Observable<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(profile: Profile)

    @Query("DELETE FROM profile_table")
    fun deleteAll()
}