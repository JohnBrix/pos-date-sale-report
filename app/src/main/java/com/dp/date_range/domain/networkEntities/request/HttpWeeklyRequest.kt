package com.dp.date_range.domain.networkEntities.request

import com.google.gson.annotations.SerializedName

data class HttpWeeklyRequest(
    @SerializedName("selectedNow")
    var selectedNow: String? = null,
    @SerializedName("selectedTo")
    var selectedTo: String? = null
)
