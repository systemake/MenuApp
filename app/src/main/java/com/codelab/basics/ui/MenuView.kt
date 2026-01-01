package com.codelab.basics.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.codelab.basics.data.MenuData
import com.codelab.basics.data.TypeOperation
import com.codelab.basics.data.TypeProduct
import com.codelab.basics.extension.toTwoDecimals
import com.codelab.basics.viewmodel.DashboardViewModel

@Composable
fun MenuView(viewModel: DashboardViewModel, type : TypeProduct) {

    val menu by viewModel.menu.collectAsState()
    val menuByType = menu.filter { it.type == type.value }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(height = 16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menuByType) { item ->
                GridItem(
                    item, viewModel
                )
            }
        }
    }
}

@Composable
fun GridItem(
    menuData: MenuData,
    viewModel: DashboardViewModel
) {

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(menuData.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )


                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.Red, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = viewModel.onCounterMenu(menuData).toString(),
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.BottomEnd)
                        .background(Color.White, shape = RectangleShape)
                        .border(BorderStroke(1.dp, Color.LightGray))
                        .clickable { viewModel.onOrderChange(menuData, TypeOperation.ADD) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        fontSize = 10.sp,
                        color = Color.Black,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.BottomStart)
                        .background(Color.White, shape = RectangleShape)
                        .border(BorderStroke(1.dp, Color.LightGray))
                        .clickable { viewModel.onOrderChange(menuData, TypeOperation.REMOVE) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "-",
                        fontSize = 10.sp,
                        color = Color.Black,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = menuData.description,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "S./ ${menuData.price.toTwoDecimals()}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}