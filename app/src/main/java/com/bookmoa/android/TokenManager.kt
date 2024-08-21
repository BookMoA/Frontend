package com.bookmoa.android

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)



    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzI0MjczOTc4fQ.9wlYA14XElDY-Gh9KdKm1-SwgUdrJNir_aepFJ73bQ8"
    fun getToken(): String? {

        return hardcodedToken
    }

}