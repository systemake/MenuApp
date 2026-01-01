package com.codelab.basics.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelab.basics.R
import com.codelab.basics.data.OrderDetailData
import com.codelab.basics.viewmodel.DashboardViewModel


@Composable
fun OrderView(
    navController: NavController,
    viewModel: DashboardViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    val activity = LocalContext.current as ComponentActivity
    Scaffold(
        topBar = {
            ToolbarWithBack(
                "Detalle de la orden",
                navController
            )
        }
    ) { paddingValues ->

        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {

            Text(
                text = stringResource(R.string.text_menu),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.text_quantity),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )


        }


        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items( viewModel.listPreOrder()) { order ->
                OrderItem(order, viewModel)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar Orden")
        }

        if (showDialog) {
            ConfirmOrderDialog(
                onConfirm = {
                    showDialog = false
                    viewModel.saveOrder()
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
        SavedOrderDialog(navController, viewModel)

    }
    }
}

@Composable
fun ConfirmOrderDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Confirmación")
        },
        text = {
            Text("¿Estás seguro de enviar la orden?")
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun SavedOrderDialog(navController: NavController, viewModel: DashboardViewModel) {
    if( viewModel.orderSaved){
    AlertDialog(
        onDismissRequest = { viewModel.orderSaved = false },
        title = {
            Text("Orden Registrada")
        },
        text = {
            Text("Se envió la orden correctamente")
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.orderSaved = false
                viewModel.clearForm()
                navController.popBackStack()
            }) {
                Text("Aceptar")
            }
        }
    )
    }
}

@Composable
fun OrderItem(order: OrderDetailData, viewModel: DashboardViewModel) {
    val menu by viewModel.menu.collectAsStateWithLifecycle()
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        val menuDescription = menu.find { it.id == order.idMenu }
        Text(
            text = menuDescription?.description ?: "",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = order.quantity.toString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}



