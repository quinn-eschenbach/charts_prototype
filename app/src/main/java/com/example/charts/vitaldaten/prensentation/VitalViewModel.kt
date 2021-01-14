package com.example.charts.vitaldaten.prensentation

import com.example.charts.vitaldaten.data.Profile
import com.example.charts.vitaldaten.data.ProfileRepository
import javax.inject.Inject

class VitalViewModel @Inject constructor(private val profileRepository: ProfileRepository) {

    val profiles = profileRepository.allProfiles()

    fun addProfile(profile: Profile) = profileRepository.addProfile(profile)
}