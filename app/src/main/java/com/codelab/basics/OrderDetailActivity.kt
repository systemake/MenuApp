package com.codelab.basics

//@Composable
//fun OrderView(list: ArrayList<OrderData>?) {
//    var showDialog by remember { mutableStateOf(false) }
//    val viewModel: DashboardViewModel = viewModel()
//
//    Column(modifier = Modifier.fillMaxSize()
//        .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(30.dp))
//        Text(
//            text = "Detalle de la orden",
//            fontSize = 30.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold
//        )
//        val menu by viewModel.menu.collectAsStateWithLifecycle()
//        for (item in list!!){
//            val menuDescription = menu.find{ it.id ==  item.idMenu}
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "${item.quantity} - ${menuDescription?.description}",
//                fontSize = 20.sp,
//                color = Color.Black,
//                fontWeight = FontWeight.Bold
//            )
//        }
//        Spacer(modifier = Modifier.height(30.dp))
//        Button(
//            onClick = { showDialog = true},
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Confirmar Orden")
//        }
//        if (showDialog) {
//            AlertDialog(
//                onDismissRequest = { showDialog = false },
//                title = {
//                    Text("Confirmación")
//                },
//                text = {
//                    Text("¿Estás seguro de enviar la orden?")
//                },
//                confirmButton = {
//                    TextButton(onClick = {
//                        showDialog = false
//                        viewModel.saveOrder()
//                    }) {
//                        Text("Aceptar")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = {
//                        showDialog = false
//                    }) {
//                        Text("Cancelar")
//                    }
//                }
//            )
//        }
//    }
//}
