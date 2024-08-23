package com.bookmoa.android.services

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)


    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzI0NDQ5MDEzfQ.klLlxyLjofyK43tGC7yibS643XHwVmhnTz4tlcpmz38"
    fun getToken(): String {

        return hardcodedToken
    }
}