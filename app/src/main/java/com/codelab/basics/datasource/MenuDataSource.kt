package com.codelab.basics.datasource

import com.codelab.basics.api.MenuApi

class MenuDataSource(private val api: MenuApi) {

    suspend fun getMenu() = api.getMenu().map { it.toData() }
}