package com.example.charts.vitaldaten.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table")
    fun getProfiles(): Observable<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(profile: Profile)

    @Query("DELETE FROM profile_table")
    fun deleteAll()
}