package com.dp.date_range.data.constant

import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DateV1 {

    @POST("/report/daily")
    fun getDailySale(@Body rr: HttpDailyDateRequest): Call<HttpDailyDateResponse>
}