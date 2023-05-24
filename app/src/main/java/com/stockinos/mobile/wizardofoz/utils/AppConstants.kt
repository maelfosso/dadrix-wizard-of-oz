package com.stockinos.mobile.wizardofoz.utils

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Constants {
    const val BASE_URL = "https://6743f6ce17ba.ngrok.app" // "http://192.168.43.3:8000" // "http://192.168.100.3:8080" // "https://a70dba2c96da.ngrok.app"
}

object Style {
    var fontWeightBoldGlobal: FontWeight = FontWeight.Bold
    var fontWeightPrimaryGlobal: FontWeight = FontWeight.Normal
    var fontWeightSecondaryGlobal: FontWeight = FontWeight.Normal
}

object Size {
    var textBoldSizeGlobal = 16.0.sp
    var textPrimarySizeGlobal = 16.0.sp
    var textSecondarySizeGlobal = 14.0.sp
}