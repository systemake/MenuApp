package com.codelab.basics.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: String,
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {
            addHeader("Authorization", "Bearer $tokenProvider")
            addHeader("apikey", apiKey)
            addHeader("Accept", "application/json")
        }.build()

        return chain.proceed(request)
    }
}