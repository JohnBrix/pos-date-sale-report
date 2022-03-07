package com.dp.date_range.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.request.HttpMonthlyRequest
import com.dp.date_range.domain.networkEntities.request.HttpWeeklyRequest
import com.dp.date_range.domain.networkEntities.request.HttpYearlyRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
import com.dp.date_range.domain.networkEntities.response.HttpMonthlySaleResponse
import com.dp.date_range.domain.networkEntities.response.HttpWeeklyDateResponse
import com.dp.date_range.domain.networkEntities.response.HttpYearlyResponse
import com.dp.date_range.repositories.impl.DateRepository

class SaleViewModel: ViewModel() {

    private var dateRepo = DateRepository()

    fun getDailySaleTotal(context: Context,request: HttpDailyDateRequest): LiveData<HttpDailyDateResponse> {

        return dateRepo.getDailySale(context,request)
    }
    fun getWeeklySaleTotal(context: Context,request: HttpWeeklyRequest): LiveData<HttpWeeklyDateResponse> {

        return dateRepo.getWeeklySale(context,request)
    }
    fun getMonthlyTotal(context: Context,request: HttpMonthlyRequest): LiveData<HttpMonthlySaleResponse> {

        return dateRepo.getMonthly(context,request)
    }
    fun getYearlyTotal(context: Context,request: HttpYearlyRequest): LiveData<HttpYearlyResponse> {

        return dateRepo.getYearlySale(context,request)
    }
}