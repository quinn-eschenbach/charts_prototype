package com.example.charts.vitaldaten.data

import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.data.ProfileDao
import com.example.charts.vitaldaten.data.ProfileRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProfileDataSource @Inject constructor(private val profileDao: ProfileDao): ProfileRepository(profileDao) {

    fun getProfiles(): Observable<List<Profile>> {
        return profileDao.getProfiles()
    }

}