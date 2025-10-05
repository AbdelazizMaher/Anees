package com.example.anees.ui.screens.quran_pdf.juz_index

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anees.R
import com.example.anees.utils.pdf_helper.juzPageRanges

@Preview(showBackground = true , locale = "ar")
@Composable
fun JuzIndexScreen(onIndexButtonClick: () -> Unit={}) {

    val viewModel : JuzIndexViewModel = hiltViewModel()
    LazyColumn(
        modifier = Modifier.fillMaxWidth().background(Color(0xFFF8F5E3))
    ) {

        item {
            Spacer(modifier = Modifier.height(48.dp))
        }

        items(juzPageRanges){
            JuzRow(juz = it , position = juzPageRanges.indexOf(it)){
                viewModel.updateCurrentPageIndex(it)
                onIndexButtonClick()
            }
        }

        item {
            Spacer(modifier = Modifier.height(48.dp))
        }

    }

}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun JuzRow(
    juz:Pair<Int,String>,
    position:Int = 0,
    onClick:(Int)-> Unit
) {
    val backgroundColor = if (position % 2 == 0) Color(0xFFF8F5E3) else Color(0xFFE8E1C1)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = {
                            onClick(juz.first)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp).weight(1f)

                )
                Text(
                    text = "${juz.second}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(5f)
                )

                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(
                        id = R.drawable.juz
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp).weight(1f)
                )

            }
            //  Divider(Modifier.height(1.dp).background(Color.LightGray))
        }
    }
}