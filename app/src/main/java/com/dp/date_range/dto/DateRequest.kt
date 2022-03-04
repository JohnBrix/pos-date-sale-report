package com.dp.date_range.dto

data class DateRequest(
    var daily:String? = null,
    var weeklyNow:String? = null,
    var weeklyTo:String? = null,
    var monthlyNumber:String? = null,
    var monthlyYear:String? = null,
    var yearly:String? = null,
)
