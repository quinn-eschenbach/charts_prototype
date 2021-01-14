package com.example.charts.vitaldaten.data

import javax.inject.Inject

open class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun allProfiles() = profileDao.getProfiles()

    fun addProfile(profile: Profile) = profileDao.insert(profile)
}