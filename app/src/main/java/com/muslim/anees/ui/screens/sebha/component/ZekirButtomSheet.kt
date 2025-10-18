package com.muslim.anees.ui.screens.sebha.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.anees.R
import com.muslim.anees.data.model.SebihaZekr
import com.muslim.anees.ui.dialog.AneesAlertDialog
import com.muslim.anees.ui.screens.sebha.SebihaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzkarButtomSheet(
    viewModel: SebihaViewModel,
    showAddZekrDialog: MutableState<Boolean>,
    mainZekir: String,
    onClose: () -> Unit,
    onZekirClick: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val azkarList = viewModel.allSebhaZeker.collectAsStateWithLifecycle()
    var selectedZekir by remember { mutableStateOf(mainZekir) }

    val showDeleteDialog = remember { mutableStateOf(false) }
    var zekrToDelete by remember { mutableStateOf<SebihaZekr?>(null) }

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFF5F5DB)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (screenHeight * .35).dp, max = (screenHeight * .7).dp)
                .verticalScroll(rememberScrollState())
                .background(color = Color(0xFFF5F5DB)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    showAddZekrDialog.value = !showAddZekrDialog.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6F3A18),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "أضف ذكر جديد",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                )
            }
            Spacer(Modifier.height(16.dp))

            azkarList.value.forEach { zekr ->
                ZekirSheetCard(
                    zekir = zekr.name,
                    isSelected = zekr.name == selectedZekir,
                    onZekirClick = {
                        selectedZekir = zekr.name
                        onZekirClick(zekr.name)
                        onClose()
                    },
                    onLongClick = {
                        zekrToDelete = zekr
                        showDeleteDialog.value = true
                    }
                )
            }

            if (showDeleteDialog.value) {
                AneesAlertDialog(
                    title = "حذف الذكر",
                    message = "هل أنت متأكد من حذف هذا الذكر؟",
                    onConfirmLabel = "حذف",
                    onDismissLabel = "تخطي",
                    onConfirm = {
                        viewModel.deleteZekerFromSebha(SebihaZekr(zekrToDelete?.name ?: ""))
                        Toast.makeText(context, "تم حذف الذكر بنجاح", Toast.LENGTH_SHORT).show()
                        showDeleteDialog.value = false
                    },
                    onDismiss = {
                        showDeleteDialog.value = false
                    }
                )
            }
            Spacer(Modifier.height(24.dp))


        }
    }
}