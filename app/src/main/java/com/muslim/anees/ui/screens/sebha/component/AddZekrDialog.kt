package com.muslim.anees.ui.screens.sebha.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.muslim.anees.data.model.SebihaZekr
import com.muslim.anees.ui.screens.sebha.SebihaViewModel

@Composable
fun AddZekrDialog(
    modifier: Modifier = Modifier,
    viewModel: SebihaViewModel,
    showAddZekrDialog: MutableState<Boolean>,
) {
    var zekerAdded by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    OutlinedTextField(
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF6F3A18),
                            focusedBorderColor = Color(0xFF6F3A18),
                            disabledBorderColor = Color(0xFF6F3A18),
                            cursorColor = Color.Black,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        value = zekerAdded,
                        onValueChange = {
                            zekerAdded = it

                        },
                        placeholder = {
                            Text("أدخل الذكر هنا")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                if (zekerAdded.text.isNotEmpty()) {
                                    viewModel.addZekerToSebha(SebihaZekr(zekerAdded.text))
                                    Toast.makeText(context, "تم إضافة الذكر بنجاح", Toast.LENGTH_SHORT).show()
                                    showAddZekrDialog.value = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "من فصلك ادخل الذكر",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6F3A18),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, Color(0xFF9B623D)),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                        ) {
                            Text("اضافة ذكر", color = Color.White)
                        }
                        OutlinedButton(
                            onClick = {
                                showAddZekrDialog.value = false
                            },
                            border = BorderStroke(1.dp, Color(0xFF6F3A18)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                        ) {
                            Text("أغلق", color = Color(0xFF6F3A18))
                        }

                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}