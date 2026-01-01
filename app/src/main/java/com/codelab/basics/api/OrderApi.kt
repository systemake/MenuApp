package com.codelab.basics.api

import com.codelab.basics.dto.OrderDetailRequest
import com.codelab.basics.dto.OrderDetailResponse
import com.codelab.basics.dto.OrderRequest
import com.codelab.basics.dto.OrderResponse
import com.codelab.basics.dto.OrderUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApi {
    @GET("order_detail")
    suspend fun getOrdersDetail(@Query("id_order") idOrder: String) : List<OrderDetailResponse>

    @POST("orders")
    suspend fun saveOrder(@Header("Prefer") prefer: String = "return=representation", @Body request : OrderRequest) : List<OrderResponse>

    @PATCH("orders")
    suspend fun updateOrder(@Header("Prefer") prefer: String = "return=representation",@Query("id_order") idOrder: String, @Body request : OrderUpdateRequest) : List<OrderResponse>

    @POST("order_detail")
    suspend fun saveOrderDetail(@Header("Prefer") prefer: String = "return=representation", @Body request: List<OrderDetailRequest>) : List<OrderDetailResponse>

    @GET("orders")
    suspend fun getOrders(@Query("date") date: String) : List<OrderResponse>
}