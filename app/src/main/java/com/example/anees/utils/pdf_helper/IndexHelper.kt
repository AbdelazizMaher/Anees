package com.example.anees.utils.pdf_helper

import com.example.anees.enums.SuraTypeEnum

data class SuraIndex(
    val index : Int ,
    val suraName : String,
    val type : SuraTypeEnum,
    val pageNumber : Int,
    val ayahNumber : Int ,

)

val SuraIndexes = listOf(
    SuraIndex(1,  "ٱلْفَاتِحَةُ", SuraTypeEnum.MECCA, 568, 7),
    SuraIndex(2,  "البقرة", SuraTypeEnum.MADIENA, 567, 286),
    SuraIndex(3, "آل عمران", SuraTypeEnum.MADIENA, 524, 200),
    SuraIndex(4, "النساء", SuraTypeEnum.MADIENA, 500, 176),
    SuraIndex(5,  "المائدة", SuraTypeEnum.MADIENA, 474, 120),
    SuraIndex(6, "الأنعام", SuraTypeEnum.MECCA, 454, 165),
    SuraIndex(7,  "الأعراف", SuraTypeEnum.MECCA, 433, 206),
    SuraIndex(8, "الأنفال", SuraTypeEnum.MADIENA, 409, 75),
    SuraIndex(9,  "التوبة", SuraTypeEnum.MADIENA, 400, 129),
    SuraIndex(10,  "يونس", SuraTypeEnum.MECCA, 382, 109),
    SuraIndex(11, "هود", SuraTypeEnum.MECCA, 370, 123),
    SuraIndex(12, "يوسف", SuraTypeEnum.MECCA, 356, 111),
    SuraIndex(13, "الرعد", SuraTypeEnum.MECCA, 344, 43),
    SuraIndex(14, "إبراهيم", SuraTypeEnum.MECCA, 338, 52),
    SuraIndex(15,  "الحجر", SuraTypeEnum.MECCA, 332, 99),
    SuraIndex(16,  "النحل", SuraTypeEnum.MECCA, 327, 128),
    SuraIndex(17,  "الإسراء", SuraTypeEnum.MECCA, 314, 111),
    SuraIndex(18,  "الكهف", SuraTypeEnum.MECCA, 303, 110),
    SuraIndex(19,  "مريم", SuraTypeEnum.MECCA, 292, 98),
    SuraIndex(20,  "طه", SuraTypeEnum.MECCA, 285, 135),
    SuraIndex(21, "الأنبياء", SuraTypeEnum.MECCA, 275, 112),
    SuraIndex(22,  "الحج", SuraTypeEnum.MADIENA, 266, 78),
    SuraIndex(23,  "المؤمنون", SuraTypeEnum.MECCA, 258, 118),
    SuraIndex(24,  "النور", SuraTypeEnum.MECCA, 250, 64),
    SuraIndex(25, "الفرقان", SuraTypeEnum.MECCA, 240, 77),
    SuraIndex(26,  "الشعراء", SuraTypeEnum.MECCA, 234, 227),
    SuraIndex(27,  "النمل", SuraTypeEnum.MECCA, 224, 93),
    SuraIndex(28,  "القصص", SuraTypeEnum.MECCA, 215, 88),
    SuraIndex(29,  "العنكبوت", SuraTypeEnum.MECCA, 205, 69),
    SuraIndex(30,  "الروم", SuraTypeEnum.MECCA, 198, 60),
    SuraIndex(31,  "لقمان", SuraTypeEnum.MECCA, 192, 34),
    SuraIndex(32, "السجدة", SuraTypeEnum.MECCA, 188, 30),
    SuraIndex(33,  "الأحزاب", SuraTypeEnum.MADIENA, 186, 73),
    SuraIndex(34,  "سبأ", SuraTypeEnum.MECCA, 176, 54),
    SuraIndex(35,  "فاطر", SuraTypeEnum.MECCA, 170, 45),
    SuraIndex(36,  "يس", SuraTypeEnum.MECCA, 165, 83),
    SuraIndex(37, "الصافات", SuraTypeEnum.MECCA, 159, 182),
    SuraIndex(38,  "ص", SuraTypeEnum.MECCA, 152, 88),
    SuraIndex(39,  "الزمر", SuraTypeEnum.MECCA, 147, 75),
    SuraIndex(40,  "غافر", SuraTypeEnum.MECCA, 138, 85),
    SuraIndex(41, "فصلت", SuraTypeEnum.MECCA, 130, 54),
    SuraIndex(42,  "الشورى", SuraTypeEnum.MECCA, 124, 53),
    SuraIndex(43,  "الزخرف", SuraTypeEnum.MECCA, 118, 89),
    SuraIndex(44,  "الدخان", SuraTypeEnum.MECCA, 112, 59),
    SuraIndex(45, "الجاثية", SuraTypeEnum.MECCA, 109, 37),
    SuraIndex(46, "الأحقاف", SuraTypeEnum.MECCA, 105, 35),
    SuraIndex(47, "محمد", SuraTypeEnum.MADIENA, 101, 38),
    SuraIndex(48, "الفتح", SuraTypeEnum.MADIENA, 97, 29),
    SuraIndex(49, "الحجرات", SuraTypeEnum.MADIENA, 92, 18),
    SuraIndex(50, "ق", SuraTypeEnum.MECCA, 90, 45),
    SuraIndex(51, "الذاريات", SuraTypeEnum.MECCA, 87, 60),
    SuraIndex(52, "الطور", SuraTypeEnum.MECCA, 84, 49),
    SuraIndex(53, "النجم", SuraTypeEnum.MECCA, 82, 62),
    SuraIndex(54, "القمر", SuraTypeEnum.MECCA, 79, 55),
    SuraIndex(55, "الرحمن", SuraTypeEnum.MECCA, 76, 78),
    SuraIndex(56, "الواقعة", SuraTypeEnum.MECCA, 73, 96),
    SuraIndex(57, "الحديد", SuraTypeEnum.MADIENA, 70, 29),
    SuraIndex(58, "المجادلة", SuraTypeEnum.MADIENA, 65, 22),
    SuraIndex(59, "الحشر", SuraTypeEnum.MADIENA, 62, 24),
    SuraIndex(60, "الممتحنة", SuraTypeEnum.MADIENA, 59, 13),
    SuraIndex(61, "الصف", SuraTypeEnum.MADIENA, 56, 14),
    SuraIndex(62, "الجمعة", SuraTypeEnum.MADIENA, 54, 11),
    SuraIndex(63, "المنافقون", SuraTypeEnum.MADIENA, 53, 11),
    SuraIndex(64, "التغابن", SuraTypeEnum.MADIENA, 51, 18),
    SuraIndex(65, "الطلاق", SuraTypeEnum.MADIENA, 49, 12),
    SuraIndex(66, "التحريم", SuraTypeEnum.MADIENA, 47, 12),
    SuraIndex(67, "الملك", SuraTypeEnum.MECCA, 45, 30),
    SuraIndex(68, "القلم", SuraTypeEnum.MECCA, 43, 52),
    SuraIndex(69, "الحاقة", SuraTypeEnum.MECCA, 40, 52),
    SuraIndex(70, "المعارج", SuraTypeEnum.MECCA, 38, 44),
    SuraIndex(71, "نوح", SuraTypeEnum.MECCA, 36, 28),
    SuraIndex(72, "الجن", SuraTypeEnum.MECCA, 34, 28),
    SuraIndex(73, "المزمل", SuraTypeEnum.MECCA, 32, 20),
    SuraIndex(74, "المدثر", SuraTypeEnum.MECCA, 31, 56),
    SuraIndex(75, "القيامة", SuraTypeEnum.MECCA, 29, 40),
    SuraIndex(76, "الإنسان", SuraTypeEnum.MADIENA, 27, 31),
    SuraIndex(77, "المرسلات", SuraTypeEnum.MECCA, 25, 50),
    SuraIndex(78, "النبأ", SuraTypeEnum.MECCA, 24, 40),
    SuraIndex(79, "النازعات", SuraTypeEnum.MECCA, 22, 46),
    SuraIndex(80, "عبس", SuraTypeEnum.MECCA, 20, 42),
    SuraIndex(81, "التكوير", SuraTypeEnum.MECCA, 19, 29),
    SuraIndex(82, "الانفطار", SuraTypeEnum.MECCA, 18, 19),
    SuraIndex(83, "المطففين", SuraTypeEnum.MECCA, 17, 36),
    SuraIndex(84, "الانشقاق", SuraTypeEnum.MECCA, 16, 25),
    SuraIndex(85, "البروج", SuraTypeEnum.MECCA, 15, 22),
    SuraIndex(86, "الطارق", SuraTypeEnum.MECCA, 14, 17),
    SuraIndex(87, "الأعلى", SuraTypeEnum.MECCA, 13, 19),
    SuraIndex(88, "الغاشية", SuraTypeEnum.MECCA, 12, 26),
    SuraIndex(89, "الفجر", SuraTypeEnum.MECCA, 12, 30),
    SuraIndex(90, "البلد", SuraTypeEnum.MECCA, 10, 20),
    SuraIndex(91, "الشمس", SuraTypeEnum.MECCA, 10, 15),
    SuraIndex(92, "الليل", SuraTypeEnum.MECCA, 9, 21),
    SuraIndex(93, "الضحى", SuraTypeEnum.MECCA, 8, 11),
    SuraIndex(94, "الشرح", SuraTypeEnum.MECCA, 8, 8),
    SuraIndex(95, "التين", SuraTypeEnum.MECCA, 7, 8),
    SuraIndex(96, "العلق", SuraTypeEnum.MECCA, 7, 19),
    SuraIndex(97, "القدر", SuraTypeEnum.MECCA, 6, 5),
    SuraIndex(98, "البينة", SuraTypeEnum.MADIENA, 6, 8),
    SuraIndex(99, "الزلزلة", SuraTypeEnum.MADIENA, 5, 8),
    SuraIndex(100, "العاديات", SuraTypeEnum.MECCA, 5, 11),
    SuraIndex(101, "القارعة", SuraTypeEnum.MECCA, 4, 11),
    SuraIndex(102, "التكاثر", SuraTypeEnum.MECCA, 4, 8),
    SuraIndex(103, "العصر", SuraTypeEnum.MECCA, 3, 3),
    SuraIndex(104, "الهمزة", SuraTypeEnum.MECCA, 3, 9),
    SuraIndex(105, "الفيل", SuraTypeEnum.MECCA, 3, 5),
    SuraIndex(106, "قريش", SuraTypeEnum.MECCA, 2, 4),
    SuraIndex(107, "الماعون", SuraTypeEnum.MECCA, 2, 7),
    SuraIndex(108, "الكوثر", SuraTypeEnum.MECCA, 2, 3),
    SuraIndex(109, "الكافرون", SuraTypeEnum.MECCA, 1, 6),
    SuraIndex(110, "النصر", SuraTypeEnum.MADIENA, 1, 3),
    SuraIndex(111, "المسد", SuraTypeEnum.MECCA, 1, 5),
    SuraIndex(112, "الإخلاص", SuraTypeEnum.MECCA, 0, 4),
    SuraIndex(113, "الفلق", SuraTypeEnum.MECCA, 0, 5),
    SuraIndex(114, "الناس", SuraTypeEnum.MECCA, 0, 6),
)

