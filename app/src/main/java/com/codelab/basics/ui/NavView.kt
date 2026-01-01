package com.codelab.basics.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codelab.basics.data.TypeUser
import com.codelab.basics.viewmodel.DashboardViewModel

@Composable
fun AppNavGraph(typeUser: Int) {
    val navController = rememberNavController()
    val viewModel: DashboardViewModel = viewModel()
    val destination = if (typeUser == TypeUser.WAITER.value) {
        "menu"
    } else {
        "ordersByDay"
    }

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable("menu") {
            Box {
                ContainerView(navController, viewModel)
                LoadingIndicator(viewModel.isLoading)
            }
        }
        composable("order") {
            Box {
                OrderView(navController, viewModel = viewModel)
                LoadingIndicator(viewModel.isLoading)
            }
        }
        composable(
            "orderList/{idOrder}/{status}",
            arguments = listOf(
                navArgument("idOrder") {
                    type = NavType.StringType
                },
                navArgument("status") {
                    type = NavType.IntType
                }
            )) { backStackEntry ->
            val idOrder = backStackEntry.arguments?.getString("idOrder").orEmpty()
            val status = backStackEntry.arguments?.getInt("status") ?: 0
            Box {
                OrderListDetailView(navController, viewModel = viewModel, idOrder = idOrder.toInt(), status)
                LoadingIndicator(viewModel.isLoading)
            }
        }
        composable("ordersByDay") {
            Box {
                OrderListView(navController, viewModel = viewModel)
                LoadingIndicator(viewModel.isLoading)
            }
        }
    }
}