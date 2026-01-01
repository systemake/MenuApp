package com.codelab.basics.data

import com.codelab.basics.dto.OrderDetailRequest

data class OrderDetailData(val idOrder : Int = 0, val quantity: Int, val idMenu : Int, var menuDescription : String = ""){
    fun toOrderDetailRequest(idOrder: Int): OrderDetailRequest {
        return OrderDetailRequest(
            idOrder = idOrder,
            quantity = quantity,
            idMenu = idMenu
        )
    }

}