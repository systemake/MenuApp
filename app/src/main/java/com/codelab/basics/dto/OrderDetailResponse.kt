package com.codelab.basics.dto


import com.codelab.basics.data.OrderDetailData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailResponse(@SerializedName("id_order") val idOrder : Int = 0, val quantity: Int, @SerializedName("id_menu") val idMenu : Int){
    fun toOrderDetailData() : OrderDetailData {
        return OrderDetailData(idOrder, quantity, idMenu)
    }
}