package com.furkan.satellite_app.data.local.source.file

import android.content.Context
import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.model.Satellite
import com.furkan.satellite_app.data.model.SatellitePositionsList
import com.furkan.satellite_app.utils.Resource
import com.furkan.satellite_app.utils.getListFromJson
import com.furkan.satellite_app.utils.getObjectFromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val gson: Gson,
    @ApplicationContext private val context: Context
) : FileDataSource {
    override suspend fun getSatelliteList(fileName: String): Resource<List<Satellite>?> {
        return context.getListFromJson(fileName, gson, object : TypeToken<List<Satellite>>() {}.type)
    }

    override suspend fun getSatelliteDetail(fileName: String): Resource<List<SatelliteDetail>?> {
        return context.getListFromJson(fileName, gson,object : TypeToken<List<SatelliteDetail>>() {}.type)
    }

    override suspend fun getSatellitePosition(fileName: String): Resource<SatellitePositionsList?> {
        return context.getObjectFromJson(fileName, gson)
    }
}