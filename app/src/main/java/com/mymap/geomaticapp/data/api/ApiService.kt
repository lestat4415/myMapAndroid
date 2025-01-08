package com.mymap.geomaticapp.data.api

import com.mymap.geomaticapp.data.GetPositionsResponse
import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/mapGo_lastest/getAllPositions")
    suspend fun getAllPositions(): Response<GetPositionsResponse>

    @POST("/mapGo_lastest/addPosition")
    suspend fun addPosition(
        @Body request: AddPositionRequest
    ): Response<GetPositionsResponse>
}