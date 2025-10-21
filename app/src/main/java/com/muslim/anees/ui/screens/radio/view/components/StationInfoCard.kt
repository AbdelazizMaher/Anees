package com.muslim.anees.ui.screens.radio.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R
import com.muslim.anees.data.model.audio.AudioTrack

@Composable
fun StationInfoCard(currentStation: AudioTrack) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF18181E)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 36.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = currentStation.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentStation.description,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    textAlign = TextAlign.Right
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            BarWaveVisualizer()
        }
    }
}