package com.codelab.basics.dto

import com.codelab.basics.data.MenuData

data class MenuResponse(val id_menu: Int, val description : String, val image : String,val type : Int, val price : Double){
    fun toData(): MenuData {
        return MenuData(
            id = id_menu,
            description = description,
            image = image,
            type = type,
            price = price
        )
    }
}