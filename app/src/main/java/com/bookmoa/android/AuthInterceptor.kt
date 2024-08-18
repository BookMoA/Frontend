package com.bookmoa.android

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // TokenManager를 통해 토큰을 가져옴
        val token = tokenManager.getToken()
        if (token != null) {
            // Authorization 헤더에 토큰 추가
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
