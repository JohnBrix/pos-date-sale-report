package com.dp.date_range.domain.networkEntities.request

import com.google.gson.annotations.SerializedName

data class HttpMonthlyRequest(
    @SerializedName("selectedDate")
    var selectedDate: String? = null,
    @SerializedName("selectedMonth")
    var selectedMonth: String? = null
)
