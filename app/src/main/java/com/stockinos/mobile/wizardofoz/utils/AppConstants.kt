package com.stockinos.mobile.wizardofoz.utils

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stockinos.mobile.wizardofoz.BuildConfig

object Constants {
    private var BASE_URL = "http://${BuildConfig.BASE_URL}"
    var API_URL = "$BASE_URL:8000"
    var SOCKET_IO_URL = "$BASE_URL:4000"
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
