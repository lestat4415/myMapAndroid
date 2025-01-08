package com.mymap.geomaticapp.data

import com.google.gson.annotations.SerializedName

data class GetPositionsResponse(
    @SerializedName("responseCode") val responseCode: Int?,
    @SerializedName("data") val data: DataResponse?
)

data class DataResponse(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("points") val points: List<PositionsData>?
)

data class PositionsData(
    @SerializedName("id") val id: String?,
    @SerializedName("coordinates") val coordinates: CoordinatesData?,
    @SerializedName("description") val description: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("lastUpdate") val lastUpdate: String?,
    @SerializedName("typeId") val typeId: Int?
)

data class CoordinatesData(
    @SerializedName("lat") val lat: Double?,
    @SerializedName("long") val long: Double?
)
