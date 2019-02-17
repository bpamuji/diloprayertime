package com.bayupamuji.dilojadwalsholat.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateFormat(date: Date?) : String? = with(date?: Date()){
    SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(this)
}