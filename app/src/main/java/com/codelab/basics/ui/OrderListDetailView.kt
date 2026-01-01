package com.codelab.basics.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelab.basics.R
import com.codelab.basics.data.OrderDetailData
import com.codelab.basics.data.TypeStatusOrder
import com.codelab.basics.viewmodel.DashboardViewModel

@Composable
fun OrderListDetailView(
    navController: NavController,
    viewModel: DashboardViewModel,
    idOrder: Int,
    status: Int
) {
    LaunchedEffect(Unit) {
        viewModel.getDetailOrder(idOrder)
    }
    val orders by viewModel.orderRegistered.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ToolbarWithBack(
                stringResource(R.string.text_detail_order),
                navController
            )
        }
    ) {paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                when (status) {
                    TypeStatusOrder.PENDING.value -> {
                        statusText(Color.Red,  stringResource(R.string.text_pending_order))
                    }
                    TypeStatusOrder.IN_COURSE.value -> {

                        statusText(Color.Gray,  stringResource(R.string.text_pending_in_progress))
                    }
                    TypeStatusOrder.COMPLETED.value -> {
                        statusText(Color.Green,  stringResource(R.string.text_pending_completed))
                    }
                }
            }


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
                    items(orders) { order ->
                        OrderDetailItem(order)
                    }
                }


            when(status){
                TypeStatusOrder.PENDING.value -> {
                    statusButton(viewModel, idOrder, TypeStatusOrder.IN_COURSE, Color.Red, stringResource(R.string.text_take_order))

                }

                TypeStatusOrder.IN_COURSE.value -> {
                    statusButton(viewModel, idOrder, TypeStatusOrder.COMPLETED, Color.Gray, stringResource(R.string.text_change_to_completed))
                }
            }

            if (viewModel.orderUpdated) {
                viewModel.orderUpdated = false
                navController.popBackStack()
            }
        }
    }
}



@Composable
fun OrderDetailItem(order: OrderDetailData) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = order.menuDescription,
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

@Composable
fun statusText(color : Color, text: String){
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(color)
    )
    Text(
        text = text,
        fontSize = 20.sp,
        color = color,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
    )
}
@Composable
fun statusButton(viewModel: DashboardViewModel, idOrder: Int, status : TypeStatusOrder, color : Color, text: String){
ActionButton(
text = text,
color = color
) {
    viewModel.updateOrder(
        "eq.$idOrder",
        status.value
    )
}
}