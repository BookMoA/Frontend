package com.bookmoa.android.services


class TokenManager() {

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLsjajri4giLCJhdXRoIjoiUk9MRV9VU0VSIiwibmlja25hbWUiOiLsjajri4giLCJleHAiOjE3MjQzNTMzMTF9.t9gCl5qapVZ7W1E67UjYA9OsBTmBtXwizhbhauLSHzM"
    fun getToken(): String? {

        return hardcodedToken
    }


}