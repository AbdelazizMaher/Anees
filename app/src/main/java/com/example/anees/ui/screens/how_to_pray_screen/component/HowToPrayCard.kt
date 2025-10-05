package com.example.anees.ui.screens.how_to_pray_screen.component

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.SubcomposeAsyncImage
import com.example.anees.R
import com.example.anees.ui.screens.how_to_pray_screen.model.HowToPrayPojo
import com.example.anees.ui.screens.radio.components.CustomSnackbar
import com.example.anees.utils.extensions.convertNumbersToArabic


@Composable
fun HowToPrayCard(
    modifier: Modifier = Modifier,
    data: HowToPrayPojo,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val snackbarMessage = remember { mutableStateOf<String?>(null) }


    Card(
        modifier = modifier
            .width(LocalConfiguration.current.screenWidthDp.dp * 0.95f)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(2.dp, Color(0xEB803F0B), RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xEBE7D3BB))
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height((screenHeight * 0.48f))
                    .fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = data.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.Black,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.anees),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 32.dp)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFF803F0B), Color(0xFFB55C28))
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        data.indexOfObj.toString().convertNumbersToArabic(),
                        color = Color.White,
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(CircleShape)
                     ,
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            Log.d("Alxi", "HowToPrayCard: ${data.youtubeLink}")
                            if (data.youtubeLink.isNullOrEmpty() || data.youtubeLink == "") {
                                snackbarMessage.value = "لا يوجد رابط"
                                return@IconButton
                            }
                            data.youtubeLink.let {
                                snackbarMessage.value = "الانتقال الى اليوتيوب"
                                val intent = Intent(Intent.ACTION_VIEW, it?.toUri())
                                context.startActivity(intent)
                            }
                        },
                        modifier = Modifier
                            .background(Color(0x66FFFFFF), shape = CircleShape)
                            .size(64.dp)
                            .padding(4.dp)

                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_youtube),
                            contentDescription = "youtube link",
                            tint = Color.Unspecified
                        )
                    }

                }
                Surface(
                    color = Color(0xAAFFFFFF).copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(4.dp)
                ) {
                    Text(
                        data.title,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.amiri)),
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (!data.isFirst) {
                        IconButton(
                            onClick = {
                                onBackClick()
                            },
                            modifier = Modifier
                                .background(Color(0x66000000), shape = CircleShape)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    } else {
                        Box(Modifier.size(48.dp)) {}
                    }
                    if (!data.isLast) {
                        IconButton(
                            onClick = {
                                onNextClick()
                            },
                            modifier = Modifier
                                .background(Color(0x66000000), shape = CircleShape)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                tint = Color.White
                            )
                        }
                    } else {
                        Box(Modifier.size(48.dp)) {}
                    }
                }
                snackbarMessage.value?.let {
                    CustomSnackbar(message = it) {
                        snackbarMessage.value = null
                    }
                }
            }
            Text(
                data.description.convertNumbersToArabic(),
                color = Color.Black,
                modifier = Modifier
                    .height((screenHeight * 0.3f))
                    .background(Color(0xEBE7D3BB))
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp, bottom = 12.dp)
                    .verticalScroll(rememberScrollState()),
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Normal,
                style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.othmani)))
            )
        }
    }
}

