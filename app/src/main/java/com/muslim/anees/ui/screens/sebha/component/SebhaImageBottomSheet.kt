package com.muslim.anees.ui.screens.sebha.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.muslim.anees.R
import com.muslim.anees.ui.screens.sebha.SebihaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SebhaImageBottomSheet(
    viewModel: SebihaViewModel,
    bottomSheetState: MutableState<Boolean>,
) {
    val sheetState = rememberModalBottomSheetState()
    val imageList = listOf(
        R.drawable.sebha,
        R.drawable.sebha0,
        R.drawable.sebha1,
        R.drawable.sebha2,
        R.drawable.sebha3,
        R.drawable.sebha4,
        R.drawable.sebha5,
    )

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp

    var selectedImageId by remember { mutableIntStateOf(viewModel.getChachedSebhaImageId()) }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        ModalBottomSheet(
            onDismissRequest = { bottomSheetState.value = false },
            sheetState = sheetState,
            containerColor = Color(0xFFF5F5DB),
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = (screenHeight * .35).dp, max = (screenHeight * .7).dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .background(Color(0xFFF5F5DB)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "اختر شكل السبحة المفضل",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF6F3A18),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 110.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = (screenHeight * 0.55).dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(imageList.size) { index ->
                        val imageResId = imageList[index]
                        val isSelected = imageResId == selectedImageId

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isSelected) Color(0xFF6F3A18).copy(alpha = 0.15f)
                                    else Color.Transparent
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Color(0xFF6F3A18)
                                    else Color.LightGray,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    selectedImageId = imageResId
                                    viewModel.cashSebhaImageId(imageResId)
                                    bottomSheetState.value = false
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Inside
                            )
                        }
                    }
                }

            }
        }
    }
}

