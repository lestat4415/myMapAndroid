package com.mymap.geomaticapp.data.api.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("responseCode") val responseCode: Int? = null,
    val data: T? = null
)
