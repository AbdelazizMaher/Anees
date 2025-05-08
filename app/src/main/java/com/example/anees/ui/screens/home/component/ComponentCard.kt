package com.example.anees.ui.screens.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R


@Preview
@Composable
fun ComponentCard(
    brush: Brush=Brush.verticalGradient(
        colors = listOf(
            Color(0xFF803F0B),
            Color(0xFF5A2E0E),
            Color(0xFF311403),
        )
    ),
    fontSizeOption: Float? = null,
    fontColor: Color = Color.White,
    fontId:Int=R.font.thules,
    onClick: () -> Unit = {},
    id: Int = R.drawable.koran,
    title: String = "القرآن الكريم",
    size: Float = 0.4f
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val cardWidth = screenWidth * size
    val cardHeight = cardWidth * 0.65f

    val iconSize = cardWidth * 0.28f
    val bgIconSize = cardWidth * 0.8f
    val fontSize = fontSizeOption ?: cardWidth.value * 0.11f
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color(0xFFB8926A))

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = brush,

                    shape = RoundedCornerShape(16.dp),
                ),


        ) {
            Image(
                painter = painterResource(id),
                contentDescription = null,
                modifier = Modifier
                    .size(bgIconSize * 1.2f)
                    .align(Alignment.Center)
                    .alpha(0.08f),
                contentScale = ContentScale.Fit
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                Image(
                    painter = painterResource(id),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )

                Text(
                    text = title,
                    color =fontColor,
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(fontId)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }}
}
