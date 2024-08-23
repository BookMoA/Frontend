package com.bookmoa.android.services


class TokenManager() {

    private val hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLsjajri4giLCJhdXRoIjoiUk9MRV9VU0VSIiwibmlja25hbWUiOiLsjajri4giLCJleHAiOjE3MjQ0MTI5MDJ9.2VgbRYf1QTqLA1j-7A8eyRb_gpaRip19x6equzo21SE"

    fun getToken(): String? {

        return hardcodedToken
    }
}