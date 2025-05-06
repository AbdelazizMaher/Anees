package com.example.anees.ui.screens.prayer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrayerTopBar(
    location: String,
    onBackClick: () -> Unit = {}
){
    Row(
        Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        LocationChip(location = location, icon = Icons.Default.LocationOn)
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp),
        ){
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
        }

    }
}

@Composable
fun LocationChip(
    location: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(

        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFCD0202),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = location,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
