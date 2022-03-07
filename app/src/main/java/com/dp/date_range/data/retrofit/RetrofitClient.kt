package com.dp.date_range.data.retrofit

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private val PRODUCT_URI = "https://product-api-v1.herokuapp.com"

    fun getDailySale(context: Context?): Retrofit {
        var client: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS).build()

        val chuckActivity = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .build()

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(PRODUCT_URI)
            .client(client)
            .client(chuckActivity)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
    fun getWeeklySale(context: Context?): Retrofit {
        var client: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS).build()

        val chuckActivity = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .build()

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(PRODUCT_URI)
            .client(client)
            .client(chuckActivity)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
    fun getMonthlySale(context: Context?): Retrofit {
        var client: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS).build()

        val chuckActivity = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .build()

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(PRODUCT_URI)
            .client(client)
            .client(chuckActivity)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}