package com.dp.date_range.domain.networkEntities.request

import com.google.gson.annotations.SerializedName

data class HttpDailyDateRequest(
    @SerializedName("selectedDate")
    var selectedDate: String? = null,
)
