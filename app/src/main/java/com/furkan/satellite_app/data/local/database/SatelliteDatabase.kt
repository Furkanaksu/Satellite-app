package com.furkan.satellite_app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.data.local.dao.SatelliteDao


@Database(
    entities = [SatelliteDetail::class, SatellitePosition::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun getSatelliteDao(): SatelliteDao
}