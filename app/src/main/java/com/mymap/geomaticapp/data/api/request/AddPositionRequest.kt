package com.mymap.geomaticapp.data.api.request

import com.google.gson.annotations.SerializedName

data class AddPositionRequest(
    @SerializedName("coordinates")
    val coordinates: CoordinatesRequest,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("typeId")
    val type: Int
)

data class CoordinatesRequest(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double
)
