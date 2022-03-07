package com.dp.date_range.domain.networkEntities.response


import com.google.gson.annotations.SerializedName

data class HttpYearlyResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("resultMessage")
    var resultMessage: String? = null,
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    @SerializedName("timeStamp")
    var timeStamp: String? = null,
    @SerializedName("yearlySaleTotal")
    var yearlySaleTotal: Double? = null
)