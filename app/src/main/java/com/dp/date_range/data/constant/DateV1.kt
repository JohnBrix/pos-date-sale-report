package com.dp.date_range.data.constant

import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.request.HttpMonthlyRequest
import com.dp.date_range.domain.networkEntities.request.HttpWeeklyRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
import com.dp.date_range.domain.networkEntities.response.HttpMonthlySaleResponse
import com.dp.date_range.domain.networkEntities.response.HttpWeeklyDateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DateV1 {

    @POST("/report/daily")
    fun getDailySale(@Body rr: HttpDailyDateRequest): Call<HttpDailyDateResponse>
    @POST("/report/weekly")
    fun getWeeklySale(@Body rr: HttpWeeklyRequest): Call<HttpWeeklyDateResponse>
    @POST("/report/monthly")
    fun getMonthLySale (@Body rr: HttpMonthlyRequest): Call<HttpMonthlySaleResponse>
}