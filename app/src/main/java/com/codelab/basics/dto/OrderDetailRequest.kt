package com.codelab.basics.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailRequest(@SerializedName("id_order") val idOrder : Int = 0, val quantity: Int, @SerializedName("id_menu") val idMenu : Int)