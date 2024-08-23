package com.bookmoa.android.services


class TokenManager() {

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLsjajri4giLCJhdXRoIjoiUk9MRV9VU0VSIiwibmlja25hbWUiOiLsjajri4giLCJleHAiOjE3MjQ0MDc4MzB9.DoBgW5UiV3pAlGJj7HKLsphT106fDxlwZmW2w9GKCgM"
    fun getToken(): String? {

        return hardcodedToken
    }


}