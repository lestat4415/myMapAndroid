package com.mymap.geomaticapp.model

import com.mymap.geomaticapp.data.PositionsData

data class Positions(
    val id: String,
    val lat: Double,
    val long: Double,
    val description: String,
    val name: String,
    val lastUpdate: String,
    val type: Int
)

fun PositionsData.toModel(): Positions =
    Positions(
        id = this.id ?: "",
        lat = this.coordinates?.lat ?: 0.0,
        long = this.coordinates?.long ?: 0.0,
        description = this.description ?: "",
        name = this.name ?: "",
        lastUpdate = this.lastUpdate ?: "",
        type = this.typeId ?: 0
    )