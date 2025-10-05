package com.example.anees.ui.screens.hadith

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.enums.AuthorEdition
import com.example.anees.enums.getAuthorsName
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.extensions.isInternetAvailable
import com.example.anees.utils.hadith_helper.AuthorAssets
import com.google.gson.Gson

@Composable
fun HadithAuthorsScreen(navToHadithsSections: (String) -> Unit, onBackClick: () -> Unit) {
    val ctx = LocalContext.current
    val isOnline = ctx.isInternetAvailable()


    ScreenBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 24.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ScreenTitle(title = "رواة الحديث", onBackClick = onBackClick, size = 26)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 64.dp)

        ) {
            items(getAuthorsName(isOnline)) { author ->
                AuthorCard(author = author, onClick = {
                    navToHadithsSections(Gson().toJson(author))
                })
            }
        }
    }

}

@Composable
fun AuthorCard(
    author: AuthorEdition,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box{
            Image(
                painter = painterResource(id = R.drawable.authorbg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color(0xEBE7D3BB)),
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = AuthorAssets.getImage(author)),
                    contentDescription = author.displayNameAr,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = author.displayNameAr,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
