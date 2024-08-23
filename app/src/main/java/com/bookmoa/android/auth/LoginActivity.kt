package com.bookmoa.android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ActivityLoginBinding
import com.bookmoa.android.models.LoginRequest
import com.bookmoa.android.models.SignUpResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.UserInfoManager
import com.bookmoa.android.services.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var email: String = ""
    private var password: String = ""

    private lateinit var api: ApiService
    private lateinit var userInfoManager: UserInfoManager

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = ApiService.create()
        userInfoManager = UserInfoManager(applicationContext)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginTv.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s)) {
                    binding.emailGuideTv.visibility = View.VISIBLE
                    binding.emailErrorTv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.passwordTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 한 글자라도 입력되면 원래 색상으로 변경
                if (!TextUtils.isEmpty(s)) {
                    binding.passwordGuideTv.visibility = View.VISIBLE
                    binding.passwordErrorTv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun login() {
        email = binding.emailTv.text.toString().trim()
        password = binding.passwordTv.text.toString().trim()

        val isEmailValid: Boolean = if (TextUtils.isEmpty(email)) {
            binding.emailGuideTv.visibility = View.VISIBLE
            binding.emailErrorTv.visibility = View.GONE
            false
        } else {
            binding.passwordGuideTv.visibility = View.GONE
            binding.passwordErrorTv.visibility = View.GONE
            true
        }

        val isPasswordValid: Boolean = if (TextUtils.isEmpty(password)) {
            binding.passwordGuideTv.visibility = View.VISIBLE
            binding.passwordErrorTv.visibility = View.GONE
            false
        } else {
            binding.passwordGuideTv.visibility = View.GONE
            binding.passwordErrorTv.visibility = View.GONE
            true
        }

        if (isEmailValid && isPasswordValid) {
            api.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val body = response.body()
                    if(body != null) {
                        val responseString = body.string().replace(Regex("[^\\x20-\\x7E]"), "")
                        val gson = Gson()
                        val loginResponse =
                            gson.fromJson(responseString, SignUpResponse::class.java)

                        GlobalScope.launch {
                            userInfoManager.updateTokens(
                                accessToken = loginResponse.data.accessToken,
                                refreshToken = loginResponse.data.refreshToken,
                                accessTokenExpire = loginResponse.data.accessExpiredDateTime,
                                refreshTokenExpire = loginResponse.data.refreshExpiredDateTime
                            )

                            Log.d("[LOGIN]", "${userInfoManager.getTokens()}")
                            Log.d("[LOGIN]", "로그인 정보: $loginResponse.data")
                            Log.d("[LOGIN]", "로그인 성공")

                            userInfoManager.updateEmailAndNickname(
                                email = loginResponse.data.email,
                                nickname = loginResponse.data.nickname
                            )

                            userInfoManager.saveGroupandTotalPage(
                                group = loginResponse.data.myClub,
                                totalPage = loginResponse.data.totalPages
                            )

                            val profileUrl = loginResponse.data?.profileURL
                            profileUrl?.let {
                                GlobalScope.launch {
                                    userInfoManager.saveProfileImageUri(it)
                                }
                            }

                            val intent =
                                Intent(this@LoginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    } else {
                        Log.d("[LOGIN]", "로그인 실패: ${response.errorBody()?.string()}")
                        Toast.makeText(
                            this@LoginActivity,
                            "로그인에 실패했습니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("[LOGIN]", "통신 실패 - 로그인 실패")
                }

            })
        }
    }
}