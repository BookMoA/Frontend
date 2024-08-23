package com.bookmoa.android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ActivitySignUpBinding
import com.bookmoa.android.models.NickNameCheckResponse
import com.bookmoa.android.models.SignUpRequest
import com.bookmoa.android.models.SignUpResponse
import com.bookmoa.android.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    private lateinit var api: ApiService
    private var email: String = ""
    private var password: String = ""
    private var passwordCheck: String = ""
    private var nickname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.noAllAgreeIc.setOnClickListener {
            binding.noAllAgreeIc.visibility = View.GONE
            binding.allAgreeIc.visibility = View.VISIBLE
            binding.ageAgreeIc.setImageResource(R.drawable.ic_term_check)
            binding.serviceAgreeIc.setImageResource(R.drawable.ic_term_check)
            binding.privacyAgreeIc.setImageResource(R.drawable.ic_term_check)
        }

        binding.allAgreeIc.setOnClickListener {
            binding.noAllAgreeIc.visibility = View.VISIBLE
            binding.allAgreeIc.visibility = View.GONE
            binding.ageAgreeIc.setImageResource(R.drawable.ic_term_no_check)
            binding.serviceAgreeIc.setImageResource(R.drawable.ic_term_no_check)
            binding.privacyAgreeIc.setImageResource(R.drawable.ic_term_no_check)
        }

        binding.ageAgreeIc.setOnClickListener {
            if (binding.ageAgreeIc.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.ic_term_check)?.constantState) {
                binding.ageAgreeIc.setImageResource(R.drawable.ic_term_no_check)
            } else {
                binding.ageAgreeIc.setImageResource(R.drawable.ic_term_check)
            }
        }

        binding.serviceAgreeIc.setOnClickListener {
            if (binding.serviceAgreeIc.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.ic_term_check)?.constantState) {
                binding.serviceAgreeIc.setImageResource(R.drawable.ic_term_no_check)
            } else {
                binding.serviceAgreeIc.setImageResource(R.drawable.ic_term_check)
            }
        }

        binding.privacyAgreeIc.setOnClickListener {
            if (binding.privacyAgreeIc.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.ic_term_check)?.constantState) {
                binding.privacyAgreeIc.setImageResource(R.drawable.ic_term_no_check)
            } else {
                binding.privacyAgreeIc.setImageResource(R.drawable.ic_term_check)
            }
        }

        binding.nicknameCheckBtn.setOnClickListener {
            nicknameCheck()
        }

        binding.signupBtn.setOnClickListener {
            validateFields()
        }

        setupTextWatchers()
    }


    private fun setupTextWatchers() {
        binding.emailTv.addTextChangedListener(createTextWatcher { validateEmail()})
        binding.passwordTv.addTextChangedListener(createTextWatcher { validatePassword() })
        binding.passwordCheckTv.addTextChangedListener(createTextWatcher { validatePasswordCheck() })
        binding.nicknameTv.addTextChangedListener(createTextWatcher { validateNickname() })
    }

    private fun createTextWatcher(validationFunction: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validationFunction()
                // updateSignupButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun validateFields() {
        validateEmail()
        validatePassword()
        validatePasswordCheck()
        validateNickname()
        validateAgreeTerms()

        if (isFormValid()) {
            signUp()
            Log.d("[SIGNUP]", "회원가입 가능")
        } else {
            Log.d("[SIGNUP]", "모든 필드가 입력되지 않았습니다.")
        }
    }

    private fun validateEmail() {
        email = binding.emailTv.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailGuideTv.visibility = View.VISIBLE
        } else {
            binding.emailGuideTv.visibility = View.GONE
        }
    }

    private fun validatePassword() {
        password = binding.passwordTv.text.toString().trim()

        if (!TextUtils.isEmpty(password) && !PASSWORD_PATTERN.matcher(password).matches()) {
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.grey3))
        }
    }

    private fun validatePasswordCheck() {
        passwordCheck = binding.passwordCheckTv.text.toString().trim()

        if (password != passwordCheck) {
            binding.passwordCheckGuideTv.visibility = View.VISIBLE
        } else {
            binding.passwordCheckGuideTv.visibility = View.GONE
        }
    }

    private fun validateNickname() {
        nickname  = binding.nicknameTv.text.toString().trim()

        if (!TextUtils.isEmpty(nickname) && !NICKNAME_PATTERN.matcher(nickname).matches()) {
            binding.nicknameGuideTv.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.passwordGuideTv.setTextColor(ContextCompat.getColor(this, R.color.grey3))
        }
    }

    private fun validateAgreeTerms() {

    }

    private fun isFormValid(): Boolean {
        val isEmailValid = !TextUtils.isEmpty(email)
        val isPasswordValid = !TextUtils.isEmpty(password)
        val isPasswrodCheckValid = !TextUtils.isEmpty(passwordCheck)
        val isNicknameValid = !TextUtils.isEmpty(nickname)

        return isEmailValid && isPasswordValid && isPasswrodCheckValid && isNicknameValid
    }

    private fun nicknameCheck() {
        if (!TextUtils.isEmpty(nickname)) {
            Log.d("[SIGNUP]", "닉네임 중복 요청: $nickname")

            val apiService = ApiService.create()

            apiService.nickNameCheck(nickname).enqueue(object: Callback<NickNameCheckResponse> {
                override fun onResponse(
                    call: Call<NickNameCheckResponse>,
                    response: Response<NickNameCheckResponse>
                ) {
                    if (response.isSuccessful) {
                        val nickNameCheckResponse = response.body()
                        if (nickNameCheckResponse != null && nickNameCheckResponse.data.isUnique) {
                            // 닉네임 중복 확인 성공
                            Log.d("[SIGNUP]", "닉네임 중복 확인 성공")
                            binding.nicknameCheckDoneTv.visibility = View.VISIBLE
                            binding.nicknameCheckErrorTv.visibility = View.GONE
                        }
                        else {
                            Log.d("[SIGNUP]", "닉네임이 중복되었습니다.")
                            binding.nicknameCheckErrorTv.visibility = View.VISIBLE
                            binding.nicknameCheckDoneTv.visibility = View.GONE
                        }
                    } else {
                        Log.d("[SIGNUP]", "닉네임 중복 확인 실패")
                    }
                }

                override fun onFailure(call: Call<NickNameCheckResponse>, t: Throwable) {
                    Log.d("[SIGNUP]", "통신 실패 - 닉네임 중복 확인 실패")
                }

            })
        } else {
            Log.d("[SIGNUP]", "아이디가 비어있습니다.")
        }
    }

    // 회원가입 통신
    private fun signUp() {
        val request = SignUpRequest(email, password, nickname)
        Log.d("[SIGNUP]", "회원가입 요청: $request")

        val apiService = ApiService.create()

        apiService.SignUp(request).enqueue(object: Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    val signUpResponse = response.body()
                    if (signUpResponse != null) {
                        // 회원가입 성공
                        Log.d("[SIGNUP]", "회원가입 성공")
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish() // SignUpActivity 종료
                    } else {
                        Log.d("[SIGNUP]", "회원가입 실패: 응답 본문이 비어 있음")
                    }
                } else {
                    Log.d("[SIGNUP]", "회원가입 실패: 서버 응답 에러 코드: ${response.code()}")
                    Log.d("[SIGNUP]", "서버 응답 에러 메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("[SIGNUP]", "회원가입 통신 실패: ${t.message}")
            }

        })
    }

    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$"
        )

        private val NICKNAME_PATTERN: Pattern = Pattern.compile(
            "^[가-힣a-zA-Z]{1,8}$"
        )
    }
}