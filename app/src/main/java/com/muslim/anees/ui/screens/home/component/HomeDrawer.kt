package com.muslim.anees.ui.screens.home.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R
import com.muslim.anees.ui.navigation.ScreenRoute


@Composable
fun HomeDrawer(
    isOpen: Boolean,
    onNavigate: (ScreenRoute) -> Unit
) {
    val drawerWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = drawerWidth * 0.5f
    val offsetX by animateDpAsState(
        targetValue = if (isOpen) 0.dp else drawerWidth,
        animationSpec = tween(300)
    )

    val expandedSection = remember { mutableStateOf<String?>(null) }
    fun handleExpansionChange(section: String, isExpanding: Boolean) {
        expandedSection.value = if (isExpanding) section else null
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth)
            .offset(x = offsetX)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(cardWidth)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_foreground),
                        contentDescription = "صورة التطبيق",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "مرحباً بك",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.othmani))
                        )
                        Text(
                            text = "المستخدم الكريم",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.othmani))
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.width(cardWidth),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ExpandableMenuCard(
                        width = cardWidth,
                        expanded = expandedSection.value == "quran",
                        onExpandChange = { handleExpansionChange("quran", it) },
                        mainText = "القرآن الكريم",
                        items = listOf(
                            "المصحف" to { onNavigate(ScreenRoute.CompleteQuranScreen) },
                            "التفسير" to { onNavigate(ScreenRoute.TafsirScreen) }
                        )
                    )

                    ExpandableMenuCard(
                        width = cardWidth,
                        expanded = expandedSection.value == "audio",
                        onExpandChange = { handleExpansionChange("audio", it) },
                        mainText = "صوتيات",
                        items = listOf(
                            "القراء" to { onNavigate(ScreenRoute.RecitersScreen) },
                            "الراديو" to { onNavigate(ScreenRoute.RadioScreen) }
                        )
                    )

                    ExpandableMenuCard(
                        width = cardWidth,
                        expanded = expandedSection.value == "adhkar",
                        onExpandChange = { handleExpansionChange("adhkar", it) },
                        mainText = "الأذكار",
                        items = listOf(
                            "أذكار الصباح والمساء" to { onNavigate(ScreenRoute.AdhkarScreen) },
                            "تسبيح" to { onNavigate(ScreenRoute.Sebiha) },
                            "حصن المسلم" to { onNavigate(ScreenRoute.HisnAlMuslimScreen) }
                        )
                    )

                    MenuCard(
                        width = cardWidth,
                        text = "القبلة",
                        onClick = { onNavigate(ScreenRoute.QiblaScreen) }
                    )

                    MenuCard(
                        width = cardWidth,
                        text = "أوقات الصلاة",
                        onClick = { onNavigate(ScreenRoute.PrayerTimesScreen) }
                    )

                    MenuCard(
                        width = cardWidth,
                        text = "أسماء الله الحسنى",
                        onClick = { onNavigate(ScreenRoute.NamesOfAllahScreen) }
                    )

                    MenuCard(
                        width = cardWidth,
                        text = "الاعدادت",
                        onClick = { onNavigate(ScreenRoute.SettingsScreen) }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}