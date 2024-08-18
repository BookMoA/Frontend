package com.bookmoa.android

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzIzOTc4MTcyfQ.jepnDUXrXiWPcL5wfK3ekdZ2SfXFxYlDCYKd5GN8yvc"

    fun getToken(): String? {

        return hardcodedToken
    }

    fun deleteToken() {
        val editor = prefs.edit()
        editor.remove("jwt_token")
        editor.apply()
    }
}