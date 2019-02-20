package com.bayupamuji.dilojadwalsholat.utils

import android.content.Context
import android.content.SharedPreferences

class SharePreferencesUtils(context:Context){

    private val sharePreferences = context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE)

    fun getString(key:String) : String?{
        return sharePreferences.getString(key,"")
    }

    fun setString(key:String, value:String?) {
        val editor : SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    companion object {
        const val SHARE_PREF_NAME = "SHARE_PREF_NAME"
    }
}