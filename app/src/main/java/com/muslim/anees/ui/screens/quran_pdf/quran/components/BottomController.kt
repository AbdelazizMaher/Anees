package com.muslim.anees.ui.screens.quran_pdf.quran.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomControlBar(onIndexButtonClick: () -> Unit ,
                     onJuzButtonClick: () -> Unit ,
                     onKhatmButtonClick: () -> Unit,
                     onBookmarkButtonClick: () -> Unit,
                     onBookMoveClicked : () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Surface(color = Color.Black.copy(alpha = 0.8f), modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = onBookMoveClicked,
                        modifier = Modifier.weight(1f),


                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "الانتقال الى العلامه",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.BookmarkAdded,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    IconButton(
                        onClick = onBookmarkButtonClick,
                        modifier = Modifier.weight(1f),


                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "إضافه علامه", color = Color.White, fontSize = 16.sp)
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = onKhatmButtonClick,
                        modifier = Modifier.weight(1f),


                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "دعاء الختم", color = Color.White, fontSize = 16.sp)
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    IconButton(
                        onClick = onJuzButtonClick,
                        modifier = Modifier.weight(1f),


                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "الاجزاء", color = Color.White, fontSize = 16.sp)
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.PieChart,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                    IconButton(
                        onClick = onIndexButtonClick,
                        modifier = Modifier.weight(1f),


                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "الفهرس", color = Color.White, fontSize = 16.sp)
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                }
            }
        }

    }
}
