package com.muslim.anees.ui.screens.names_of_allah

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R
import com.muslim.anees.data.model.NamesOfAllahModelItem

@Composable
fun NamesOfAllahCard(name:NamesOfAllahModelItem) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var isFlipped by remember { mutableStateOf(false) }


    val animatedRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "CardFlipAnimation"
    )

    val showBackContent = animatedRotation > 90f

    Box(
        modifier = Modifier
            .width(screenWidth * 0.4f)
            .height(screenHeight * 0.25f)
            .padding(8.dp)
            .graphicsLayer {
                rotationY = animatedRotation
                cameraDistance = 12 * density
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp))
                .clickable (
                    interactionSource = null,
                    indication = null,
                    onClick = { isFlipped = !isFlipped}
                )  ,
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.authorbg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color(0xEBE7D3BB)),
                    modifier = Modifier.fillMaxSize()
                )

                if (!showBackContent) {
                    Text(
                        text = name.name,
                        color = Color(0xFF311403),
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.thules)),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = name.text,
                        fontSize = 14.sp,
                        color = Color(0xFF311403),
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .graphicsLayer {
                                rotationY = 180f
                            }
                    )
                }
            }
        }
    }
}
