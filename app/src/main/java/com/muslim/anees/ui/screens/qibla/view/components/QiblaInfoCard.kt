package com.muslim.anees.ui.screens.qibla.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R
import com.muslim.anees.utils.extensions.convertNumbersToArabic

@Composable
fun QiblaInfoCard(
    bearingToQibla: Float
) {
    val directionColor = Color(0xFFFFD54F)
    val directionText = getArabicDirection(bearingToQibla)

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF).copy(alpha = 0.9f)
            )
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF).copy(alpha = 0.9f))
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(directionColor, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "اتجاه القبلة",
                        fontSize = 18.sp,
                        color = Color(0xFF8D6E63),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${bearingToQibla.toInt().toString().convertNumbersToArabic()}°",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    color = directionColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "اتجه نحو $directionText",
                    fontSize = 16.sp,
                    color = Color(0xFF8D6E63),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.othmani))
                )
            }
        }
    }
}


fun getArabicDirection(bearing: Float): String {
    val normalized = ((bearing % 360) + 360) % 360
    return when (normalized) {
        in 337.5..360.0, in 0.0..22.5 -> "الشمال"
        in 22.5..67.5 -> "الشمال الشرقي"
        in 67.5..112.5 -> "الشرق"
        in 112.5..157.5 -> "الجنوب الشرقي"
        in 157.5..202.5 -> "الجنوب"
        in 202.5..247.5 -> "الجنوب الغربي"
        in 247.5..292.5 -> "الغرب"
        in 292.5..337.5 -> "الشمال الغربي"
        else -> ""
    }
}


