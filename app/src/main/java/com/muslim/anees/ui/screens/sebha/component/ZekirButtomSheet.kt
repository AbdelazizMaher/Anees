package com.muslim.anees.ui.screens.sebha.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.muslim.anees.utils.sebha_helper.azkarList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzkarButtomSheet(
    mainZekir: String,
    onClose: () -> Unit,
    onZekirClick: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var selectedZekir by remember { mutableStateOf(mainZekir) }
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFF5F5DB)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(color = Color(0xFFF5F5DB)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            azkarList.forEach {
                ZekirSheetCard(it, isSelected = it.arabicName == selectedZekir){
                    selectedZekir = it
                    onZekirClick(it)
                    onClose()
                }
            }
            Spacer(Modifier.height(24.dp))


        }
    }
}