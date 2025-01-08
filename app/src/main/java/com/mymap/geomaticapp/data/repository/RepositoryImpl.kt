package com.mymap.geomaticapp.data.repository

import android.util.Log
import com.mymap.geomaticapp.data.api.ApiService
import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import com.mymap.geomaticapp.domain.Repository
import com.mymap.geomaticapp.model.Positions
import com.mymap.geomaticapp.model.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {

    private val logTAG = RepositoryImpl::class.java.simpleName

    override suspend fun getAllPositions(): List<Positions> {
        return withContext(Dispatchers.IO){
            val response = apiService.getAllPositions()
            Log.d(logTAG, "body ${response.body()}")
            Log.d(logTAG, "points ${response.body()?.data?.points}")
            response.body()?.data?.points?.map { it.toModel() } ?: emptyList()
        }
    }

    override suspend fun addPosition(request: AddPositionRequest): Boolean {
        return withContext(Dispatchers.IO){
            val response = apiService.addPosition(request)
            Log.d(logTAG, "body ${response.body()}")
            Log.d(logTAG, "responseCode ${response.body()?.data}")
            response.body()?.responseCode == 200
        }
    }

}