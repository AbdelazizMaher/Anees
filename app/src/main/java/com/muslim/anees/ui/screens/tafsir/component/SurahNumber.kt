package com.muslim.anees.ui.screens.tafsir.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muslim.anees.R
import com.muslim.anees.utils.extensions.convertNumbersToArabic

@Composable
fun SurahNumber(number: String) {
    Box{
        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = "Background Image",
            modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xFF311403))
        )
        Text(
            number.convertNumbersToArabic(),
            color = Color(0xFF311403),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}