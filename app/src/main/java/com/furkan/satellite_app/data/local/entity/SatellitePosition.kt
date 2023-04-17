package com.furkan.satellite_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.furkan.satellite_app.data.model.PositionCoordinate


@Entity(tableName = "satellite_position")
data class SatellitePosition(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val positions: List<PositionCoordinate>
)
