package com.codelab.basics.repository

import com.codelab.basics.data.OrderDetailData
import com.codelab.basics.datasource.OrderDataSource
import com.codelab.basics.dto.OrderDetailRequest
import com.codelab.basics.dto.OrderResponse
import com.codelab.basics.dto.generic.ApiResult
import com.codelab.basics.dto.generic.safeApiCall

class OrderRepository(private val orderDataSource: OrderDataSource) {
    suspend fun getOrdersDetail(idOrder : String): List<OrderDetailData> {
       return  orderDataSource.getOrdersDetail(idOrder)
    }

    suspend fun saveOrders(date: String): Int {
        return orderDataSource.saveOrder(date)
    }

    suspend fun updateOrders(idOrder: String, status: Int): Int {
        return orderDataSource.updateOrder(idOrder, status)
    }

    suspend fun saveOrderDetail(idOrder: Int, orders: List<OrderDetailData>): Int {
        val listDetails = ArrayList<OrderDetailRequest>()
        orders.forEach { listDetails.add(it.toOrderDetailRequest(idOrder)) }
        return orderDataSource.saveOrderDetail(listDetails)
    }

    suspend fun getOrders(date : String): ApiResult<List<OrderResponse>> {
        return  safeApiCall{ orderDataSource.getOrders(date) }
    }
}