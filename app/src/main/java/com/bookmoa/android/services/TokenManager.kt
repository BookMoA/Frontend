package com.bookmoa.android.services


class TokenManager() {

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLsjajri4giLCJhdXRoIjoiUk9MRV9VU0VSIiwibmlja25hbWUiOiLsjajri4giLCJleHAiOjE3MjQzMzY5NDZ9.r2jsXjsYIi8jMVnmzj1Fjg00k_u3kMJgSb7c-1v71-I"

    fun getToken(): String? {

        return hardcodedToken
    }
}