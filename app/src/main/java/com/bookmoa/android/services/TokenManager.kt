package com.bookmoa.android.services

import android.content.Context
import android.content.SharedPreferences

class TokenManager() {


    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzI0Mzg1NjYyfQ.jIbMoTVOnhzR8O42Nu2fDbtWorhEiUVVNQwiZaDOs9E"
    fun getToken(): String {
        return hardcodedToken
    }
}