package com.example.anees.ui.screens.downloaded_audio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.R
import com.example.anees.ui.screens.downloaded_audio.component.DownloadedAudioCard
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.downloaded_audio.loadAllAudio

@Composable
fun DownloadedAudioScreen(
    onBackClick: () -> Unit = {},
    onSuraClicked: (index: Int) -> Unit
) {
    val ctx = LocalContext.current
    val audioList = loadAllAudio(ctx)
    Box(modifier = Modifier.fillMaxSize()) {
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.downloaded_audio_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(2.dp, CircleShape, clip = true)
                    )
                    Text(
                        text = "المفضلة",
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {

                itemsIndexed(audioList) { index, surah ->
                    DownloadedAudioCard(
                        index = index,
                        surah = surah,
                        onClick = onSuraClicked
                    )
                }
            }
        }
    }

}