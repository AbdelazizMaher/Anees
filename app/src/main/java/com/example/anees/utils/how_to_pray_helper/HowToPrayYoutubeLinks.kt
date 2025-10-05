package com.example.anees.utils.how_to_pray_helper


////NOTE:Do u need to add more than one link ?, : List<String> and let user choose?
fun getHowToPrayYoutubeLink(chosenIndex: String): String {
    return when (chosenIndex) {
        "الوضوء" -> "https://www.youtube.com/watch?v=jj8DbMqQTgY"
        "الصلاة" -> "https://www.youtube.com/watch?v=sU4JIFbPhDI"
        "صلاة الجنازة" -> "https://www.youtube.com/watch?v=Ovg9cM1AwbY"
        "صلاة الاستخارة"-> "https://www.youtube.com/watch?si=wi4OH3XZQaOYwybY&v=-OrBIpLpy3o&feature=youtu.be"
        "صلاة الاستسقاء" -> "https://www.youtube.com/watch?v=kSYygUenSw4"
        "صلاة العيد" -> "https://youtu.be/gLraJZDlwOI?si=btkoxWLrftqbaikM"
        "صلاة الخوف" ->"https://youtu.be/prZ9ZrZ8J5Q?si=2PWpClOBIb4F_pSP"
        "صلاة الكسوف / الخسوف" -> "https://www.youtube.com/watch?v=wgDI5eKDxvA"
        else -> ""
    }
}
