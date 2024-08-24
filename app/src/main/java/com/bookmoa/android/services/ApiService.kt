package com.bookmoa.android.services

import android.content.Context
import android.os.Build
import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.annotation.RequiresApi
import com.bookmoa.android.BuildConfig
import com.bookmoa.android.models.BookMemoDeleteResponse
import com.bookmoa.android.models.BookMemoResponse
import com.bookmoa.android.models.LoginRequest
import com.bookmoa.android.models.MemberInfoResponse
import com.bookmoa.android.models.NickNameCheckResponse
import com.bookmoa.android.models.ProfileUpdateResponse
import com.bookmoa.android.models.RefreshTokenRequest
import com.bookmoa.android.models.RefreshTokenResponse
import com.bookmoa.android.models.SearchBookMemoResponse
import com.bookmoa.android.models.SignUpRequest
import com.bookmoa.android.models.SignUpResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException


interface ApiService: StorageListApi, ListTop10Api, StorageBookApi, ClubApi,
    GetClubsRecommend, PostClubs, GetClubsDetail, GetClubsMembers, PostClubsMembers,
    GetClubsSearch, ListContentApi,addBookListAnother, AddBookListSelectAnotherService,
    RecommendBookService ,DeleteMyBookListService, SearchListMemoService,BookDBCheckService,
    BookEntryService{


    @POST("/users/auth/sign-up")
    fun SignUp(
        @Body body: SignUpRequest,
    ): Call<SignUpResponse>

    @GET("/users/auth")
    fun nickNameCheck(
        @Query("nickname") nickname: String
    ): Call<NickNameCheckResponse>

    @POST("/users/auth/renew")
    fun refreshToken(
        @Body body : RefreshTokenRequest
    ): Call<RefreshTokenResponse>

    @POST("/users/auth/sign-in")
    fun login(
        @Body body: LoginRequest
    ): Call<ResponseBody>

    @GET("/memberBooks/bookMemos")
    fun BookMemoList(
        @Query("page") page: Int = 1
    ): Call<BookMemoResponse>

    @GET("/memberBooks/bookMemos")
    fun BookMemoTest(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1
    ): Call<BookMemoResponse>

    @DELETE("/memberBooks/{memberBookId}")
    fun DeleteBookMemo(
        @Path("memberBookId") memberBookId: Int
    ): Call<BookMemoDeleteResponse>

    @GET("/memberBooks/{memberBookId}")
    fun SearchBookMemo(
        @Path("memberBookId") memberBookId: Int
    ): Call<SearchBookMemoResponse>

    @Multipart
    @PUT("/users/profileInfo")
    fun updateProfile(
        @Query("email") email: String?,
        @Query("nickname") nickname: String?,
        @Part profileImg: MultipartBody.Part?
    ): Call<ProfileUpdateResponse>

    @GET("/users/adminInfo")
    fun adminInfo(): Call<MemberInfoResponse>

    companion object {
        private const val BASE_URL = "https://bookmoa.shop/"
        private val gson: Gson = GsonBuilder().setLenient().create()
        private lateinit var userInfoManager: UserInfoManager

        fun create(): ApiService {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun createWithHeader(context: Context): ApiService {
            userInfoManager = UserInfoManager(context)
            val interceptor =
                AppInterceptor(userInfoManager.getTokens(), userInfoManager.needRefreshToken())
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    class AppInterceptor(
        private val tokens: Pair<String?, String?>,
        private val needRefresh: Boolean,
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain) {
            if (needRefresh) {
                val tokens = tokens.second ?: ""
                create().refreshToken(RefreshTokenRequest(tokens))
                    .enqueue(object : Callback<RefreshTokenResponse> {
                        override fun onResponse(
                            call: Call<RefreshTokenResponse>,
                            response: Response<RefreshTokenResponse>,
                        ) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                GlobalScope.launch {
                                    userInfoManager.updateAccessToken(
                                        body?.data?.accessToken ?: "",
                                        body?.data?.accessExpiredDateTime ?: ""
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("[ERROR/interceptor]", "재발급 실패")
                        }
                    })
            }

            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer ${tokens.first ?: ""}")
                .addHeader("refresh-token", tokens.second ?: "")
                .build()

            // 요청 실행
            val response = chain.proceed(newRequest)

            // Response body를 로그에 출력 (JSON을 String으로 변환)
            val responseBodyString = response.body?.string() ?: "null"

            // 로그 출력
            Log.d("[PRINT/interceptor]", "Request Headers: ${newRequest.headers}")
            Log.d("[PRINT/interceptor]", "Response URL: ${newRequest.url}")
            Log.d("[PRINT/interceptor]", "Response Body: $responseBodyString")

            // 원래 Response Body를 다시 Response로 복원
            return response.newBuilder()
                .body(ResponseBody.create(response.body?.contentType(), responseBodyString))
                .build()
        }
    }
}
