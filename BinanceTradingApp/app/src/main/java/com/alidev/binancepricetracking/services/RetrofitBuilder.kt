package com.alidev.binancepricetracking.services

import com.alidev.binancepricetracking.models.OrderBookSnapshot
import com.alidev.binancepricetracking.models.Ticker
import com.alidev.binancepricetracking.models.Trade
import com.alidev.binancepricetracking.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/ticker/24hr")
    fun getTickers(): Call<List<Ticker>>

    @GET("v3/depth")
    fun getOrderBookSnapshot(
        @Query("symbol") symbol: String,
        @Query("limit") limit: Int = Constants.DEPTH_LIMIT
    ): Call<OrderBookSnapshot>

    @GET("v3/trades")
    fun getRecentTrades(
        @Query("symbol") symbol: String,
        @Query("limit") limit: Int = Constants.API_RESULT_LIMIT
    ): Call<List<Trade>>
}

object RetrofitBuilder {
    private var BASE_URL = Constants.BASE_URL

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
