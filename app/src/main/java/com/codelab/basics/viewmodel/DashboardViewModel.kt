package com.codelab.basics.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.codelab.basics.data.MenuData
import com.codelab.basics.data.OrderData
import com.codelab.basics.data.OrderDetailData
import com.codelab.basics.data.TypeOperation
import com.codelab.basics.datasource.MenuDataSource
import com.codelab.basics.datasource.OrderDataSource
import com.codelab.basics.dto.OrderDetailResponse
import com.codelab.basics.dto.generic.ApiResult
import com.codelab.basics.dto.generic.onError
import com.codelab.basics.dto.generic.onSuccess
import com.codelab.basics.extension.toTimestampWithTimeZone
import com.codelab.basics.repository.MenuRepository
import com.codelab.basics.repository.OrderRepository
import com.codelab.basics.retrofit.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class DashboardViewModel() : BaseViewModel() {

    var orderSaved by mutableStateOf(false)
    var orderUpdated by mutableStateOf(false)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    private val repositoryOrder = OrderRepository(OrderDataSource(RetrofitClient.api))
    private val repositoryMenu = MenuRepository(MenuDataSource(RetrofitClient.apiMenu))

    private val _menu = MutableStateFlow<List<MenuData>>(emptyList())
    val menu: StateFlow<List<MenuData>> = _menu

    private val _orderRegistered = MutableStateFlow<List<OrderDetailData>>(emptyList())
    val orderRegistered: StateFlow<List<OrderDetailData>> = _orderRegistered

    private val _orderByDay = MutableStateFlow<List<OrderData>>(emptyList())
    val orderByDay: StateFlow<List<OrderData>> = _orderByDay

    private val _orderList = mutableStateListOf<MenuData>()
    val orderList: List<MenuData> get() = _orderList
    val formattedDate = LocalDateTime.now().toTimestampWithTimeZone()
    fun onOrderChange(menuData: MenuData, type: TypeOperation) {
        when (type) {
            TypeOperation.ADD -> {
                _orderList.add(menuData)
            }

            TypeOperation.REMOVE -> {
                _orderList.remove(menuData)
            }
        }
    }

    fun onCounterMenu(menuData: MenuData): Int {
        return _orderList.count { it.id == menuData.id }
    }

    fun listPreOrder(): List<OrderDetailData> {
        return _orderList
            .groupingBy { it.id }
            .eachCount()
            .map { (id, count) ->
                OrderDetailData(quantity = count, idMenu = id, menuDescription = "")
            }
    }

    fun saveOrder() {
        viewModelScope.launch {
            isLoading = true
            try {
                val idOrder = repositoryOrder.saveOrders(formattedDate)
                repositoryOrder.saveOrderDetail(idOrder, orders = listPreOrder())
                orderSaved = true
            } catch (e: Exception) {
                // manejar error
                Log.d("ERROR", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun getMenu() {
        viewModelScope.launch {
            isLoading = true
            try {
                _menu.value = repositoryMenu.getMenu()
            } catch (e: Exception) {
                // manejar error
            } finally {
                isLoading = false
            }
        }
    }

    fun getDetailOrder(idOrder: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                _menu.value = repositoryMenu.getMenu()
                _orderRegistered.value = repositoryOrder.getOrdersDetail("eq.${idOrder}")
                _orderRegistered.value.forEach { itemOrder ->
                    val menuFiltered = _menu.value.find { it.id == itemOrder.idMenu }
                    itemOrder.menuDescription = menuFiltered?.description ?: ""
                }
            } catch (e: Exception) {
                // manejar error
                Log.d("ERROR", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            isLoading = true
            val today = LocalDate.now().toString()
             repositoryOrder.getOrders("eq.${today}")
                .onSuccess { orders ->
                   _orderByDay.value = orders.map { it.toOrderData() }
                }
                .onError { error ->
                    _errorMessage.value = error.message
                }
            isLoading = false
        }
    }

    fun updateOrder(idOrder: String, status: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                repositoryOrder.updateOrders(idOrder, status)
                orderUpdated = true
            } catch (e: Exception) {
                // manejar error
                Log.d("ERROR", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun clearForm() {
        _orderList.clear()
    }

    fun clearError() {
        _errorMessage.value = null
    }
}