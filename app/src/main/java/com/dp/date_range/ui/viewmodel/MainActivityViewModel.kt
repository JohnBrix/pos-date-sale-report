package com.dp.date_range.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
import com.dp.date_range.repositories.impl.DateRepository

class MainActivityViewModel: ViewModel() {

    private var dateRepo = DateRepository()

    fun getDailySaleTotal(context: Context,request: HttpDailyDateRequest): LiveData<HttpDailyDateResponse> {

        return dateRepo.getDailySale(context,request)
    }
}