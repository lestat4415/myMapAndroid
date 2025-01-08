package com.mymap.geomaticapp.domain

import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import com.mymap.geomaticapp.model.Positions

interface Repository {

    suspend fun getAllPositions(): List<Positions>

    suspend fun addPosition(request: AddPositionRequest): Boolean

}