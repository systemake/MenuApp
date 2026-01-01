package com.codelab.basics.datasource

import com.codelab.basics.api.OrderApi
import com.codelab.basics.data.OrderDetailData
import com.codelab.basics.dto.OrderDetailRequest
import com.codelab.basics.dto.OrderRequest
import com.codelab.basics.dto.OrderUpdateRequest

class OrderDataSource(private val api: OrderApi) {

    suspend fun getOrdersDetail(idOrder : String) : List<OrderDetailData> = api.getOrdersDetail(idOrder).map { it.toOrderDetailData() }
    suspend fun saveOrder(date: String) = api.saveOrder(request = OrderRequest(date))[0].idOrder
    suspend fun saveOrderDetail(orders: List<OrderDetailRequest>) =
        api.saveOrderDetail(request = orders)[0].idOrder

    suspend fun getOrders(date : String) = api.getOrders(date)
    suspend fun updateOrder(idOrder : String, status: Int) = api.updateOrder(idOrder = idOrder, request = OrderUpdateRequest(status))[0].idOrder
}