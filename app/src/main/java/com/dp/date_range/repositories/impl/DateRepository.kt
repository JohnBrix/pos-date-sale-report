package com.dp.date_range.repositories.impl

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dp.date_range.data.constant.DateV1
import com.dp.date_range.data.retrofit.RetrofitClient
import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.request.HttpMonthlyRequest
import com.dp.date_range.domain.networkEntities.request.HttpWeeklyRequest
import com.dp.date_range.domain.networkEntities.request.HttpYearlyRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
import com.dp.date_range.domain.networkEntities.response.HttpMonthlySaleResponse
import com.dp.date_range.domain.networkEntities.response.HttpWeeklyDateResponse
import com.dp.date_range.domain.networkEntities.response.HttpYearlyResponse
import com.google.gson.Gson
import com.zaiko.repositories.Date
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DateRepository : Date {
    private var retrofit = RetrofitClient()
    private val TAG = "DateRepository: "

    fun getDailySale(context: Context, request: HttpDailyDateRequest)
            : LiveData<HttpDailyDateResponse> {
        var liveData = MutableLiveData<HttpDailyDateResponse>()

        val userService: DateV1 = retrofit.getDailySale(context)
            .create(DateV1::class.java)
        var call: Call<HttpDailyDateResponse> =
            userService.getDailySale(request)

        call.enqueue(object : Callback<HttpDailyDateResponse?> {
            override fun onResponse(
                call: Call<HttpDailyDateResponse?>,
                response: Response<HttpDailyDateResponse?>
            ) {

                if (response.code() == 200) {
                    Log.i(TAG, "${response.body()}")
                    liveData.value = response.body()
                } else {
                    var product = HttpDailyDateResponse()
                    var gson = Gson();
                    var adapter = gson.getAdapter(HttpDailyDateResponse::class.java)

                    if (response.errorBody() != null)
                        product = adapter.fromJson(response.errorBody()?.string())

                    Log.i(TAG, "${product}")
                    liveData.value = product
                }

            }

            override fun onFailure(call: Call<HttpDailyDateResponse?>, t: Throwable) {
                Log.e("OnError: ", "${call} & ${t.message}")
                var prod = HttpDailyDateResponse()
                var httpProd = HttpDailyDateResponse()
                httpProd.resultMessage = "NO_CONTENT"
                Log.i(TAG, "${prod}")
                liveData.value = prod
            }

        })
        Log.i(TAG, "${liveData.value}")
        return liveData
    }

    fun getWeeklySale(context: Context, request: HttpWeeklyRequest)
            : LiveData<HttpWeeklyDateResponse> {
        var liveData = MutableLiveData<HttpWeeklyDateResponse>()

        val userService: DateV1 = retrofit.getWeeklySale(context)
            .create(DateV1::class.java)
        var call: Call<HttpWeeklyDateResponse> =
            userService.getWeeklySale(request)

        call.enqueue(object : Callback<HttpWeeklyDateResponse?> {
            override fun onResponse(
                call: Call<HttpWeeklyDateResponse?>,
                response: Response<HttpWeeklyDateResponse?>
            ) {

                if (response.code() == 200) {
                    Log.i(TAG, "${response.body()}")
                    liveData.value = response.body()
                } else {
                    var product = HttpWeeklyDateResponse()
                    var gson = Gson();
                    var adapter = gson.getAdapter(HttpWeeklyDateResponse::class.java)

                    if (response.errorBody() != null)
                        product = adapter.fromJson(response.errorBody()?.string())

                    Log.i(TAG, "${product}")
                    liveData.value = product
                }

            }

            override fun onFailure(call: Call<HttpWeeklyDateResponse?>, t: Throwable) {
                Log.e("OnError: ", "${call} & ${t.message}")
                var prod = HttpWeeklyDateResponse()
                var httpProd = HttpWeeklyDateResponse()
                httpProd.resultMessage = "NO_CONTENT"
                Log.i(TAG, "${prod}")
                liveData.value = prod
            }

        })
        Log.i(TAG, "${liveData.value}")
        return liveData
    }

    fun getMonthly(context: Context, request: HttpMonthlyRequest)
            : LiveData<HttpMonthlySaleResponse> {
        var liveData = MutableLiveData<HttpMonthlySaleResponse>()

        val userService: DateV1 = retrofit.getMonthlySale(context)
            .create(DateV1::class.java)
        var call: Call<HttpMonthlySaleResponse> =
            userService.getMonthLySale(request)

        call.enqueue(object : Callback<HttpMonthlySaleResponse?> {
            override fun onResponse(
                call: Call<HttpMonthlySaleResponse?>,
                response: Response<HttpMonthlySaleResponse?>
            ) {

                if (response.code() == 200) {
                    Log.i(TAG, "${response.body()}")
                    liveData.value = response.body()
                } else {
                    var product = HttpMonthlySaleResponse()
                    var gson = Gson();
                    var adapter = gson.getAdapter(HttpMonthlySaleResponse::class.java)

                    if (response.errorBody() != null)
                        product = adapter.fromJson(response.errorBody()?.string())

                    Log.i(TAG, "${product}")
                    liveData.value = product
                }

            }

            override fun onFailure(call: Call<HttpMonthlySaleResponse?>, t: Throwable) {
                Log.e("OnError: ", "${call} & ${t.message}")
                var prod = HttpMonthlySaleResponse()
                var httpProd = HttpMonthlySaleResponse()
                httpProd.resultMessage = "NO_CONTENT"
                Log.i(TAG, "${prod}")
                liveData.value = prod
            }

        })
        Log.i(TAG, "${liveData.value}")
        return liveData
    }

    fun getYearlySale(context: Context, request: HttpYearlyRequest)
            : LiveData<HttpYearlyResponse> {
        var liveData = MutableLiveData<HttpYearlyResponse>()

        val userService: DateV1 = retrofit.getYearlySale(context)
            .create(DateV1::class.java)
        var call: Call<HttpYearlyResponse> =
            userService.getYearlySale(request)

        call.enqueue(object : Callback<HttpYearlyResponse?> {
            override fun onResponse(
                call: Call<HttpYearlyResponse?>,
                response: Response<HttpYearlyResponse?>
            ) {

                if (response.code() == 200) {
                    Log.i(TAG, "${response.body()}")
                    liveData.value = response.body()
                } else {
                    var product = HttpYearlyResponse()
                    var gson = Gson();
                    var adapter = gson.getAdapter(HttpYearlyResponse::class.java)

                    if (response.errorBody() != null)
                        product = adapter.fromJson(response.errorBody()?.string())

                    Log.i(TAG, "${product}")
                    liveData.value = product
                }

            }

            override fun onFailure(call: Call<HttpYearlyResponse?>, t: Throwable) {
                Log.e("OnError: ", "${call} & ${t.message}")
                var prod = HttpYearlyResponse()
                var httpProd = HttpYearlyResponse()
                httpProd.resultMessage = "NO_CONTENT"
                Log.i(TAG, "${prod}")
                liveData.value = prod
            }

        })
        Log.i(TAG, "${liveData.value}")
        return liveData
    }
}