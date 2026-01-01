package com.codelab.basics.repository

import com.codelab.basics.data.MenuData
import com.codelab.basics.datasource.MenuDataSource

class MenuRepository(private val orderDataSource: MenuDataSource) {

    suspend fun getMenu() : List<MenuData> {
        return orderDataSource.getMenu()
    }
}