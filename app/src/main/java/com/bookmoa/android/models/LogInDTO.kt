package com.bookmoa.android.models

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email") val email : String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String
)

data class SignUpResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("data") val data: SignUpDataDTO
)

data class SignUpDataDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("inFocusMode") val inFocusMode: Boolean,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("signUpType") val signUpType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessExpiredDateTime") val accessExpiredDateTime: String,
    @SerializedName("refreshExpiredDateTime") val refreshExpiredDateTime: String,
    @SerializedName("profileURL") val profileURL: String,
    @SerializedName("pushNotification") val pushNotification: pushNotificationDTO,
    @SerializedName("myClub") val myClub: myClubDTO,
)

data class pushNotificationDTO(
    @SerializedName("likePush") val likePush: Boolean,
    @SerializedName("comment") val comment: Boolean,
    @SerializedName("nightPush") val nightPush: Boolean
)

data class myClubDTO(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("reader") val reader: Boolean
)

data class NickNameCheckResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("data") val data: NickNameCheckResult
)

data class NickNameCheckResult(
    @SerializedName("isUnique") val isUnique: Boolean
)

data class RefreshTokenRequest(
    @SerializedName("refreshToken") val refreshToken: String
)

data class RefreshTokenResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("data") val data: RefreshDTO
)

data class RefreshDTO(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessExpiredDateTime") val accessExpiredDateTime: String,
    @SerializedName("refreshExpiredDateTime") val refreshExpiredDateTime: String,
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ProfileUpdateResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("data") val data: ProfileDTO
)

data class ProfileDTO(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileURL") val profileURL: String
)

data class MemberInfoResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("data") val data: List<MemberInfoDTO>
)

data class MemberInfoDTO(
    @SerializedName("adminRole") val adminRole: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("githubId") val githubId: String,
    @SerializedName("snsAddress") val snsAddress: String,
    @SerializedName("profileUrl") val profileUrl: String,
)