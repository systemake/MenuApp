package com.codelab.basics.ui

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelab.basics.R
import com.codelab.basics.data.OrderData
import com.codelab.basics.data.TypeStatusOrder
import com.codelab.basics.extension.toTimestampWithTimeZone
import com.codelab.basics.viewmodel.DashboardViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListView(
    navController: NavController,
    viewModel: DashboardViewModel
) {
    val activity = LocalContext.current as ComponentActivity
    LaunchedEffect(Unit) {
        viewModel.getOrders()
    }

    val orders by viewModel.orderByDay.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ToolbarWithBack(
                "Lista de Ordenes",
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
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(
                text = LocalDateTime.now().toTimestampWithTimeZone(false),
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(height = 16.dp))
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {

                Text(
                    text = "Id",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Estado",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn {
                items(orders) { order ->
                    OrderItem(order, navController)
                }
            }
        }
    }


    val errorMessage by viewModel.errorMessage.collectAsState()
    errorMessage?.let { message ->
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Error") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("Aceptar")
                }
            }
        )
    }

}

@Composable
fun OrderItem(order: OrderData, navController: NavController) {

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = order.idOrder.toString(),
            modifier = Modifier.weight(1f)
        )

        when (order.status) {
            TypeStatusOrder.PENDING.value -> {
                Button(
                    onClick = { navController.navigate("orderList/${order.idOrder}/${order.status}") },
                    enabled = true,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.text_status_pending)
                    )
                }
            }

            TypeStatusOrder.IN_COURSE.value -> {
                Button(
                    onClick = { navController.navigate("orderList/${order.idOrder}/${order.status}") },
                    enabled = true,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.text_take_in_progress)
                    )
                }
            }

            TypeStatusOrder.COMPLETED.value -> {
                Button(
                    onClick = { navController.navigate("orderList/${order.idOrder}/${order.status}") },
                    enabled = true,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.text_take_completed)
                    )
                }
            }
        }
    }
}
