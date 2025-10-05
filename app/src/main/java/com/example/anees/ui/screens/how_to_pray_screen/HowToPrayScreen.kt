package com.example.anees.ui.screens.how_to_pray_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.how_to_pray_screen.component.HowToPrayCard
import com.example.anees.ui.screens.how_to_pray_screen.component.HowToPrayChips
import com.example.anees.ui.screens.how_to_pray_screen.model.toHowToPrayPojo
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.how_to_pray_helper.getHowToPrayChosenList
import com.example.anees.utils.how_to_pray_helper.getHowToPrayYoutubeLink
import com.example.anees.utils.how_to_pray_helper.prayList
import com.example.anees.utils.how_to_pray_helper.wodoaList
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun HowToPrayScreen(modifier: Modifier = Modifier,navToHome : () -> Unit = {}) {
    val list = remember { mutableStateOf(wodoaList) }
    val youtubeLink = remember { mutableStateOf(getHowToPrayYoutubeLink("الوضوء" )) }
    val pagerState = rememberPagerState(pageCount = { list.value.size })
    val coroutineScope = rememberCoroutineScope()

    ScreenBackground()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(vertical = 24.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ScreenTitle(title = "صفة الصلاة الصحيحة", onBackClick = {
                navToHome()
            }, size = 24)
        }
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HowToPrayChips{
                list.value = getHowToPrayChosenList(it).toMutableList()
                youtubeLink.value = getHowToPrayYoutubeLink(it)
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(bottom = 32.dp)
            ) { page ->
                HowToPrayCard(
                    data = list.value[page].toHowToPrayPojo(
                        page + 1,
                        isLast = page == list.value.size - 1
                        ,
                        link = youtubeLink.value
                    ),
                    onBackClick = {
                        if (page > 0) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page - 1)
                            }
                        }
                    },
                    onNextClick = {
                        if (page < prayList.lastIndex) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                )
            }

        }

    }

}