package com.bookmoa.android.services



class TokenManager() {

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzI0MzIyMTkwfQ.OR2oHJ1i-5P61SzU7BKwIVYDN4Zyx4D0skq9azDWFeo"
    fun getToken(): String? {

        return hardcodedToken
    }

}