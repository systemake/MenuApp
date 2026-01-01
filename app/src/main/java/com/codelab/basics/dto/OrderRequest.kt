package com.codelab.basics.dto

import com.codelab.basics.data.TypeStatusOrder

data class OrderRequest(val date : String,val status: Int = TypeStatusOrder.PENDING.value)

data class OrderUpdateRequest(val status: Int = TypeStatusOrder.PENDING.value)