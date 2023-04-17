package com.furkan.satellite_app.data.model

import com.furkan.satellite_app.data.local.entity.SatellitePosition


data class SatelliteDetailUIModel(
    val satelliteId: Int?,
    val heightMassText: String?,
    val costText: String?,
    val dateText: String?,
    val lastPosition: List<SatellitePosition?>?
)
