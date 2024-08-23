package com.bookmoa.android.services

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "pref"
)

class UserInfoManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val accessTokenKey = stringPreferencesKey("accessToken")
        val refreshTokenKey = stringPreferencesKey("refreshToken")
        val accessTokenExpireKey = stringPreferencesKey("accessTokenExpire")
        val refreshTokenExpireKey = stringPreferencesKey("refreshTokenExpire")

        val emailKey = stringPreferencesKey("email") // user 이메일
        val nicknameKey = stringPreferencesKey("nickname") // user 닉네임
        val groupKey = stringPreferencesKey("group") // 그룹 이름
        val totalPageKey = intPreferencesKey("totalPageKey")
        val profileImgKey = stringPreferencesKey("profile_image_uri")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun isValidToken(): Boolean {
        return dataStore.data.map { prefs ->
            val accessToken = prefs[accessTokenKey]
            val refreshToken = prefs[refreshTokenKey]
            val accessTokenExpire = prefs[accessTokenExpireKey]
            val refreshTokenExpire = prefs[refreshTokenExpireKey]

            accessToken != "" && (accessToken != null && refreshToken != null && accessTokenExpire != null && refreshTokenExpire != null) && parseInstant(refreshTokenExpire) > Instant.now()
        }.first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun needRefreshToken(): Boolean {
        return dataStore.data.map { prefs ->
            val accessTokenExpireKey = prefs[accessTokenExpireKey]
            parseInstant(accessTokenExpireKey) < Instant.now()
        }.first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseInstant(dateString: String?): Instant {
        return if (dateString != null) {
            // 'Z'가 포함되지 않았다면 추가해 UTC 시간으로 처리
            val formattedDateString = if (!dateString.endsWith("Z")) "$dateString" + "Z" else dateString
            Instant.parse(formattedDateString)
        } else {
            Instant.EPOCH
        }
    }

    suspend fun getTokens(): Pair<String?, String?> {
        return dataStore.data.map { prefs ->
            val accessToken = prefs[accessTokenKey]
            val refreshToken = prefs[refreshTokenKey]
            accessToken to refreshToken
        }.first()
    }

    suspend fun updateTokens(
        accessToken: String,
        refreshToken: String,
        accessTokenExpire: String,
        refreshTokenExpire: String,
    ) {
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
            prefs[refreshTokenKey] = refreshToken
            prefs[accessTokenExpireKey] = accessTokenExpire
            prefs[refreshTokenExpireKey] = refreshTokenExpire
        }
    }

    suspend fun updateAccessToken(
        accessToken: String,
        accessTokenExpire: String,
    ) {
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
            prefs[accessTokenExpireKey] = accessTokenExpire
        }
    }

    suspend fun getEmail(): String? {
        return dataStore.data.map {prefs ->
            prefs[emailKey]
        }.first()
    }

    suspend fun getNickname(): String? {
        return dataStore.data.map {prefs ->
            prefs[nicknameKey]
        }.first()
    }

    suspend fun updateEmailAndNickname(email: String, nickname: String) {
        dataStore.edit { prefs ->
            prefs[emailKey] = email
            prefs[nicknameKey] = nickname
        }
    }

    suspend fun saveGroupandTotalPage(group: String, totalPage: Int) {
        dataStore.edit { prefs ->
            prefs[groupKey] = group
        }

        dataStore.edit { prefs ->
            prefs[totalPageKey] = totalPage
        }
    }

    suspend fun getGroup(): String? {
        return dataStore.data.map { prefs ->
            prefs[groupKey]
        }.first()
    }

    suspend fun getTotalPage(): Int? {
        return dataStore.data.map { prefs ->
            prefs[totalPageKey]
        }.first()
    }


    suspend fun saveProfileImageUri(uri: String) {
        dataStore.edit { prefs ->
            prefs[profileImgKey] = uri
        }
    }

    suspend fun getProfileImageUri() : String? {
        return dataStore.data.map { prefs ->
            prefs[profileImgKey]
        }.first()
    }
 }