package com.codelab.basics.api

import com.codelab.basics.data.MenuData
import com.codelab.basics.dto.MenuResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApi {
    @GET("menu")
    suspend fun getMenu( @Query("select")  select : String = "*") : List<MenuResponse>
}