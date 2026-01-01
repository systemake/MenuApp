package com.codelab.basics.dto

import com.codelab.basics.data.OrderData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(@SerializedName("id_order") val idOrder: Int, @SerializedName("date") val date: String,@SerializedName("status") val status: Int){
    fun toOrderData() : OrderData {
        return OrderData(idOrder, date, status)
    }
}