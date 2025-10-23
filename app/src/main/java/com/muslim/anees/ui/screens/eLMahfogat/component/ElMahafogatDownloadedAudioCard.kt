package com.muslim.anees.ui.screens.eLMahfogat.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R
import com.muslim.anees.data.model.audio.AudioDto
import com.muslim.anees.ui.dialog.AneesAlertDialog
import com.muslim.anees.ui.screens.eLMahfogat.ElMahfogatViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElMahafogatDownloadedAudioCard(
    surah: AudioDto,
    index: Int,
    onClick: (index: Int) -> Unit = {},
    viewModel: ElMahfogatViewModel
) {
    val ctx = LocalContext.current
    val showDeleteDialog = remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp))
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Color(0xFF6AB0AB)),
                    onClick = { onClick(index) },
                )

                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text ="سورة ${surah.title+" - "+surah.artist+" - "+surah.album}",
                fontSize = 16.sp,
                color = Color(0xFF311403),
                fontFamily = FontFamily(Font(R.font.othmani)),
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick={
                showDeleteDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline
                    , contentDescription = null,
                    tint = Color(0xEB803F0B)
                )
            }
        }
        if (showDeleteDialog.value) {
            AneesAlertDialog(
                title = "حذف السورة",
                message = "هل أنت متأكد من حذف هذه السورة؟",
                onConfirmLabel = "حذف",
                onDismissLabel = "تخطي",
                onConfirm = {
                    viewModel.deleteDownloadedSura(
                        ctx,
                        "${surah.title} - ${surah.artist} - ${surah.album}"
                    )
                },
                onDismiss = {
                    showDeleteDialog.value = false
                }
            )
        }
    }

}