package com.bookmoa.android.services

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)


    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0bW9hIiwiYXV0aCI6IlJPTEVfVVNFUiIsIm5pY2tuYW1lIjoidGVzdG1vYSIsImV4cCI6MTcyNDMyOTAzMn0.lDmb5UNLnPA73_uxyF2f3V_0DDKc_flnNgPpc8FapDA"
    fun getToken(): String? {

        return hardcodedToken
    }
}