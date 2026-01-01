package com.codelab.basics.retrofit

import com.codelab.basics.api.MenuApi
import com.codelab.basics.api.OrderApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://kovkxoxydzvmmepcavfw.supabase.co/"
    private const val CONTEXT = "rest/"
    private const val VERSION = "v1/"

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvdmt4b3h5ZHp2bW1lcGNhdmZ3Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc2NTU2OTkwMCwiZXhwIjoyMDgxMTQ1OTAwfQ.vPz_xe-Og4e_WLse7CA_bBsxS0r2YSJ247v9mJU4zIM"
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvdmt4b3h5ZHp2bW1lcGNhdmZ3Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc2NTU2OTkwMCwiZXhwIjoyMDgxMTQ1OTAwfQ.vPz_xe-Og4e_WLse7CA_bBsxS0r2YSJ247v9mJU4zIM"

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor ( AuthInterceptor(token, apiKey) )
        .addInterceptor(logger)
        .build()

    val api: OrderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL + CONTEXT + VERSION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderApi::class.java)
    }

    val apiMenu: MenuApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL + CONTEXT + VERSION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MenuApi::class.java)
    }
}