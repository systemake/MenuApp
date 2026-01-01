package com.codelab.basics.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codelab.basics.data.TypeProduct
import com.codelab.basics.viewmodel.DashboardViewModel

@Composable
fun ContainerView(navController: NavController, viewModel: DashboardViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Carta", "Bebidas")
    var showDialogEmpty by remember { mutableStateOf(false) }
    val activity = LocalContext.current as ComponentActivity

    LaunchedEffect(Unit) {
        viewModel.getMenu()
    }
    Scaffold(
        topBar = {
            ToolbarWithBack(
                "Menú",
                navController,
                onBackClick = {
                    activity.onBackPressedDispatcher.onBackPressed()
                }
            )
        }
    ) {paddingValues ->

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(1f)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> MenuView(viewModel, TypeProduct.FOOD)
                1 -> MenuView(viewModel, TypeProduct.DRINK)
            }
        }
        Button(
            onClick = {
                showDialogEmpty = true
                      },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Continuar")
        }
    }
    if(showDialogEmpty){
        validateOrderFilled(navController, viewModel, onDismiss = { showDialogEmpty = false})
    }
    }
}

@Composable
fun validateOrderFilled(navController: NavController, viewModel: DashboardViewModel,   onDismiss: () -> Unit){

    if(viewModel.orderList.isEmpty()){
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Text("Orden")
            },
            text = {
                Text("Agrege productos a la orden")
            },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }else{
        navController.navigate("order")
    }
}