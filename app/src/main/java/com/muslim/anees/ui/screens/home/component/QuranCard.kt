package com.muslim.anees.ui.screens.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R

@Preview
@Composable
fun QuranCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    id:Int = R.drawable.koran
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(horizontal = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick() },
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            border = BorderStroke(1.dp, Color(0xFFB8926A))

        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors =
                                listOf(
                                    Color(0xFF803F0B),
                                    Color(0xFF5A2E0E),
                                    Color(0xFF311403)
                                )
                        )
                    )
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),


                ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = "Quran Icon",
                    modifier = Modifier.padding(top = 24.dp).size(72.dp).alpha(0.4f),
                    alignment = Alignment.BottomEnd

                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "القرآن الكريم",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = FontFamily(
                            Font(R.font.thules)
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = id),
                        contentDescription = "Quran Icon",
                        modifier = Modifier.size(72.dp)
                    )

                }
            }
        }
    }
}