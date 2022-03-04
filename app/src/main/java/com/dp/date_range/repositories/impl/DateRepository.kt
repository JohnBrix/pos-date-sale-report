package com.dp.date_range.repositories.impl

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dp.date_range.data.constant.DateV1
import com.dp.date_range.data.retrofit.RetrofitClient
import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.response.HttpDailyDateResponse
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
}