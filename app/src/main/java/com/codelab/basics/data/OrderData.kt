package com.codelab.basics.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderData (val idOrder : Int = 0, val date: String, val status : Int) : Parcelable