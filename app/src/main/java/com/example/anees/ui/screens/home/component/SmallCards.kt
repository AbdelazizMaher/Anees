package com.example.anees.ui.screens.home.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.anees.R
import com.example.anees.utils.location.checkPermission
import com.example.anees.utils.location.handleLocationPermission
import com.example.anees.utils.location.isLocationEnabled

@Composable
fun SubCards(
    navToSebiha: () -> Unit,
    navToQibla: () -> Unit,
    navToRadio: () -> Unit
) {
    val ctx = LocalContext.current
    val activity = ctx as? Activity
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        ComponentCard(
            size = .31f, title = "القبلة",
            onClick = {
//                if (ctx.checkPermission() && ctx.isLocationEnabled()) {
                    navToQibla()
//                } else {
//                    ctx.handleLocationPermission(activity)
//                }
            }, fontId = R.font.othmani, brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB9745C),
                    Color(0xFFC8A97E),
                    Color(0xFF927F67),
                )
            ),
            fontColor = Color.Black,
            fontSizeOption = 14f,
            id = R.drawable.qiblahome
        )
        ComponentCard(
            size = .31f,
            title = "الراديو",
            onClick = navToRadio,
            fontId = R.font.othmani,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB9745C),
                    Color(0xFFC8A97E),
                    Color(0xFF927F67),
                )
            ),
            fontColor = Color.Black,
            fontSizeOption = 14f,
            id = R.drawable.rad
        )
        Spacer(modifier = Modifier.width(2.dp))
        ComponentCard(
            size = .31f,
            title = "السبحة",
            onClick = navToSebiha,
            fontId = R.font.othmani,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB9745C),
                    Color(0xFFC8A97E),
                    Color(0xFF927F67),
                )
            ),
            fontColor = Color.Black,
            fontSizeOption = 14f,
            id = R.drawable.seb
        )
    }
}