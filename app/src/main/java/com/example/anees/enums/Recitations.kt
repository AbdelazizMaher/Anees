package com.example.anees.enums

import com.example.anees.data.model.RecitationModel
import com.example.anees.utils.IbnZakoonList
import com.example.anees.utils.KhalafAnHamzaList
import com.example.anees.utils.QalunAbuNashitList
import com.example.anees.utils.QalunAnNafiList
import com.example.anees.utils.QunbulAnIbnKathirList
import com.example.anees.utils.ShubaAnAsimList
import com.example.anees.utils.WarshAbuBakrList
import com.example.anees.utils.WarshAzraqList
import com.example.anees.utils.YaqoubRowaisRuhList
import com.example.anees.utils.alBazziAnIbnKathirList
import com.example.anees.utils.alBazziQunbulList
import com.example.anees.utils.alDuriAnAbuAmrList
import com.example.anees.utils.alDuriAnAlKisaiList
import com.example.anees.utils.alSusiAnAbuAmrList
import com.example.anees.utils.hafsAnAsimList
import com.example.anees.utils.mushafMualimList
import com.example.anees.utils.mushafMujawadList
import com.example.anees.utils.warshAnNafiList

enum class Recitations(
    val recitationName: String,
    val list: List<RecitationModel>
) {
    HAFS_AN_ASIM("حفص عن عاصم", hafsAnAsimList),
    WARSH_AN_NAFI("ورش عن نافع", warshAnNafiList),
    KHALAF_AN_HAMZA("خلف عن حمزة", KhalafAnHamzaList),
    AL_BAZZI_AN_IBN_KATHIR("البزي عن ابن كثير",alBazziAnIbnKathirList),
    QALUN_AN_NAFI("قالون عن نافع",QalunAnNafiList),
    QUNBUL_AN_IBN_KATHIR("قنبل عن ابن كثير",QunbulAnIbnKathirList),
    AL_SUSI_AN_ABU_AMR("السوسي عن أبي عمرو",alSusiAnAbuAmrList),
    QALUN_ABU_NASHIT("قالون عن نافع من طريق أبي نشيط",QalunAbuNashitList),
    YAQOUB_ROWAIS_RUH("قراءة يعقوب الحضرمي بروايتي رويس وروح",YaqoubRowaisRuhList),
    WARSH_ABU_BAKR("ورش عن نافع من طريق أبي بكر الأصبهاني",WarshAbuBakrList),
    AL_BAZZI_QUNBUL("البزي وقنبل عن ابن كثير",alBazziQunbulList),
    AL_DURI_AN_AL_KISAI("الدوري عن الكسائي",alDuriAnAlKisaiList),
    AL_DURI_AN_ABU_AMR("الدوري عن أبي عمرو",alDuriAnAbuAmrList),
    SHUBA_AN_ASIM("شعبة عن عاصم",ShubaAnAsimList),
    ABN_ZAKOON("ابن ذكوان عن ابن عامر",IbnZakoonList),
    WARSH_AZRAQ("ورش عن نافع من طريق الأزرق",WarshAzraqList),
    MUSHAF_MUALIM("المصحف المعلم",mushafMualimList),//المصحف المرتل
    MUSHAF_MUJAWAD("المصحف المجود",mushafMujawadList),//المصحف المجود;
}
