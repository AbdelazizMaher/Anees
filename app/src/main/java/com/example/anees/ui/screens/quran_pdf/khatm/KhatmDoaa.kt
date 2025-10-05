package com.example.anees.ui.screens.quran_pdf.khatm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R

@Preview(showBackground = true ,locale = "ar")
@Composable
fun KhatmQuranDuaScreen() {
    val scrollState = rememberScrollState()

    val duaParts = listOf(
        """اللَّهُمَّ ارْحَمْنِي بِالْقُرْآنِ وَاجْعَلْهُ لِي إِمَامًا وَنُورًا وَهُدًى وَرَحْمَةً، اللَّهُمَّ ذَكِّرْنِي مِنْهُ مَا نُسِيتُ وَعَلِّمْنِي مِنْهُ مَا جَهِلْتُ وَارْزُقْنِي تِلَاوَتَهُ آنَاءَ اللَّيْلِ وَأَطْرَافَ النَّهَارِ وَاجْعَلْهُ لِي حُجَّةً يَا رَبَّ الْعَالَمِينَ ✱ اللَّهُمَّ أَصْلِحْ لِي دِينِي الَّذِي هُوَ عِصْمَةُ أَمْرِي، وَأَصْلِحْ لِي دُنْيَايَ الَّتِي فِيهَا مَعَاشِي، وَأَصْلِحْ لِي آخِرَتِي الَّتِي فِيهَا مَعَادِي، وَاجْعَلِ الْحَيَاةَ زِيَادَةً لِي فِي كُلِّ خَيْرٍ وَاجْعَلِ الْمَوْتَ رَاحَةً لِي مِنْ كُلِّ شَرٍّ ✱ اللَّهُمَّ اجْعَلْ خَيْرَ عُمُرِي آخِرَهُ، وَخَيْرَ عَمَلِي خَوَاتِمَهُ، وَخَيْرَ أَيَّامِي يَوْمَ أَلْقَاكَ فِيهِ ✱ اللَّهُمَّ إِنِّي أَسْأَلُكَ عَيْشَةً هَنِيَّةً وَمِيتَةً سَوِيَّةً وَمَرَدًّا غَيْرَ مَخْزٍ وَلا فَاضِح ✱ اللَّهُمَّ إِنِّي أَسْأَلُكَ خَيْرَ الْمَسْأَلَةِ وَخَيْرَ الدُّعَاءِ وَخَيْرَ النَّجَاحِ وَخَيْرَ الْعِلْمِ وَخَيْرَ الْعَمَلِ وَخَيْرَ الثَّوَابِ وَخَيْرَ الْحَيَاةِ وَخَيْرَ الْمَمَاتِ، وَثَبِّتْنِي وَثَقِّلْ مَوَازِينِي وَحَقِّقْ إِيمَانِي وَارْفَعْ دَرَجَتِي وَتَقَبَّلْ صَلَاتِي وَاغْفِرْ خَطَايَايَ، وَأَسْأَلُكَ الْعُلَا مِنَ الْجَنَّةِ ✱ اللَّهُمَّ إِنِّي أَسْأَلُكَ مُوجِبَاتِ رَحْمَتِكَ وَعَزَائِمَ مَغْفِرَتِكَ وَالسَّلَامَةَ مِنْ كُلِّ إِثْمٍ وَالْغُنَيْمَةَ مِنْ كُلِّ بَرٍّ وَالْفَوْزَ بِالْجَنَّةِ وَالنَّجَاةَ مِنَ النَّارِ ✱ اللَّهُمَّ أَحْسِنْ عَاقِبَتَنَا فِي الأُمُورِ كُلِّهَا، وَأَجِرْنَا مِنْ خِزْيِ الدُّنْيَا وَعَذَابِ الآخِرَة ✱ اللَّهُمَّ اقْسِمْ لَنَا مِنْ خَشْيَتِكَ مَا تَحُولُ بِهِ بَيْنَنَا وَبَيْنَ مَعْصِيَتِكَ، وَمِنْ طَاعَتِكَ مَا تُبَلِّغُنَا بِهِ جَنَّتَكَ، وَمِنَ اليَقِينِ مَا تَهُونُ بِهِ عَلَيْنَا مَصَائِبَ الدُّنْيَا، وَمَتِّعْنَا بِأَسْمَاعِنَا وَأَبْصَارِنَا وَقُوَّتِنَا مَا أَحْيَيْتَنَا، وَاجْعَلْهُ الْوَارِثَ مِنَّا، وَاجْعَلْ ثَأْرَنَا عَلَى مَنْ ظَلَمَنَا، وَانْصُرْنَا عَلَى مَنْ عادَانَا، وَلا تَجْعَلْ مُصِيبَتَنَا فِي دِينِنَا، وَلا تَجْعَلِ الدُّنْيَا أَكْبَرَ هَمِّنَا، وَلا مَبْلَغَ عِلْمِنَا، وَلا تُسَلِّطْ عَلَيْنَا مَنْ لا يَرْحَمُنَا ✱ اللَّهُمَّ لا تَدَعْ لَنَا ذَنْبًا إِلَّا غَفَرْتَهُ، وَلا هَمًّا إِلَّا فَرَّجْتَهُ، وَلا دَيْنًا إِلَّا قَضَيْتَهُ، وَلا حَاجَةً مِنْ حَوَائِجِ الدُّنْيَا وَالْآخِرَةِ إِلَّا قَضَيْتَهَا يَا أَرْحَمَ الرَّاحِمِينَ ✱ رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ، صَلَّى اللَّهُ عَلَى سَيِّدِنَا وَنَبِيِّنَا مُحَمَّدٍ، وَعَلَى آلِهِ وَأَصْحَابِهِ الْأُخْيَارِ، وَسَلَّمَ تَسْلِيمًا كَثِيرًا""".trimMargin()
    )

    val keywords = listOf("اللَّهُمَّ", "رَبَّ", "رَبْنَا", "اللَّهُ")

    Box(
        Modifier.fillMaxSize().padding(bottom = 40.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .paint(
                        painter = painterResource(id = R.drawable.khatm_b),
                        contentScale = ContentScale.FillBounds
                    )
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "دُعَاءُ خَتْمِ الْقُرْآنِ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.thules)),
                    color = Color(0xFF311403),
                    modifier = Modifier.padding(top = 32.dp)
                )

                duaParts.forEachIndexed { index, part ->
                    val annotatedString = buildAnnotatedString {
                        val words = part.split(" ")
                        for (word in words) {
                            if (keywords.any { word.contains(it) }) {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("$word ")
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Color.Black)) {
                                    append("$word ")
                                }
                            }
                        }
                    }

                    Text(
                        text = annotatedString,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                }
            }
        }
    }


}

