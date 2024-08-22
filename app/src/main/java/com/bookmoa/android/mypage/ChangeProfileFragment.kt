package com.bookmoa.android.mypage

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentChangeProfileBinding
import com.bookmoa.android.models.NickNameCheckResponse
import com.bookmoa.android.models.ProfileUpdateResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.UserInfoManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.regex.Pattern

class ChangeProfileFragment : Fragment() {

    private lateinit var binding: FragmentChangeProfileBinding
    private lateinit var userInfoManager: UserInfoManager
    private lateinit var api: ApiService

    private var selectedImageUri: Uri? = null
    private var originalNickname: String? = null
    private var originalEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeProfileBinding.inflate(layoutInflater, container, false)

        api = ApiService.create()
        userInfoManager = UserInfoManager(requireContext())

        GlobalScope.launch {
            originalEmail = userInfoManager.getEmail().toString()
            originalNickname = userInfoManager.getNickname().toString()

            binding.emailTv.setText(originalEmail)
            binding.nicknameTv.setText(originalNickname)
        }


        // 프로필 이미지 로드
        GlobalScope.launch {
            val profileImageUri = userInfoManager.getProfileImageUri()
            withContext(Dispatchers.Main) {
                if (profileImageUri != null && profileImageUri.isNotEmpty()) {
                    // Glide를 사용하여 URL에서 이미지 로드
                    Glide.with(requireContext())
                        .load(profileImageUri)
                        .placeholder(R.drawable.ic_profile_unfilled)
                        .error(R.drawable.ic_profile_unfilled)
                        .into(binding.profileIv)
                } else {
                    binding.profileIv.setImageResource(R.drawable.ic_profile_unfilled)
                }
            }
        }

        binding.topBarBackIc.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.carmeraIc.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.profileChangeDoneTv.setOnClickListener {
            val updatedNickname = binding.nicknameTv.text.toString().trim()
            val updatedEmail = binding.emailTv.text.toString().trim()

            if (updatedNickname != originalNickname || updatedEmail != originalEmail || selectedImageUri != null) {
                profileUpdate(updatedNickname, updatedEmail, selectedImageUri)
            } else {
                Toast.makeText(requireContext(), "변경된 내용이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nicknameTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nickname = s.toString().trim()

                // 닉네임 필드가 비어 있는 경우
                if (nickname.isEmpty()) {
                    binding.nicknameUnfilledTv.visibility = View.VISIBLE
                    binding.idNoticeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey3))
                } else {
                    binding.nicknameUnfilledTv.visibility = View.GONE
                }

                // 닉네임 형식이 맞지 않는 경우
                if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
                    binding.idNoticeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                } else {
                    binding.idNoticeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey3))
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })

        binding.emailTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()

                // 닉네임 필드가 비어 있는 경우
                if (email.isEmpty()) {
                    binding.emailUnfilledTv.visibility = View.VISIBLE
                    binding.emailNoticeTv.visibility = View.GONE
                } else {
                    binding.emailUnfilledTv.visibility = View.GONE
                    binding.emailNoticeTv.visibility = View.GONE
                }

                // 이메일 형식 맞지 않는 경우
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailNoticeTv.visibility = View.VISIBLE
                } else {
                    binding.emailNoticeTv.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) { }
        })

        // 중복 확인 버튼 클릭 리스너 추가
        binding.idCheckBtn.setOnClickListener {
            val nickname = binding.nicknameTv.text.toString().trim()

            when {
                nickname.isEmpty() -> {
                    binding.nicknameUnfilledTv.visibility = View.VISIBLE
                    binding.nicknameCheckErrorTv.visibility = View.GONE
                    binding.nicknameCheckDoneTv.visibility = View.GONE
                }

                !NICKNAME_PATTERN.matcher(nickname).matches() -> {
                    binding.idNoticeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    binding.nicknameUnfilledTv.visibility = View.GONE
                    binding.nicknameCheckErrorTv.visibility = View.GONE
                    binding.nicknameCheckDoneTv.visibility = View.GONE
                }
                else -> {
                    binding.idNoticeTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey3))
                    binding.nicknameUnfilledTv.visibility = View.GONE
                    binding.nicknameCheckErrorTv.visibility = View.GONE
                    binding.nicknameCheckDoneTv.visibility = View.GONE
                    nicknameCheck(nickname)
                }
            }
        }
        return binding.root
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.profileIv.setImageURI(it)
        }
    }

    // URI에서 파일 생성
    private fun uriToFile(uri: Uri): File {
        val fileName = getFileName(uri)
        val file = File(requireContext().cacheDir, fileName)
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    // 파일 이름 추출
    private fun getFileName(uri: Uri): String {
        var name = "temp_file"
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }

    private fun profileUpdate(updatedNickname: String, updatedEmail: String, uri: Uri?) {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            val email = updatedEmail
            val nickname = updatedNickname

            val profileImagePart: MultipartBody.Part? = uri?.let {
                val file = uriToFile(it)
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
            }

            api.updateProfile(email, nickname, profileImagePart).enqueue(object: Callback<ProfileUpdateResponse> {
                override fun onResponse(
                    call: Call<ProfileUpdateResponse>,
                    response: Response<ProfileUpdateResponse>
                ) {
                    if(response.isSuccessful) {
                        val profileUrl = response.body()?.data?.profileURL
                        profileUrl?.let {
                            GlobalScope.launch {
                                userInfoManager.saveProfileImageUri(it)
                            }
                        }
                        Log.d("[PROFILE]", "프로필 업데이트 성공")
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    else {
                        Log.d("[PROFILE]", "프로필 업데이트 실패: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ProfileUpdateResponse>, t: Throwable) {
                    Log.d("[PROFILE]", "통신 실패: ${t.message}")
                }
            })
        }
    }



    // 닉네임 중복 확인 통신 메서드
    private fun nicknameCheck(nickname: String) {
        api.nickNameCheck(nickname).enqueue(object : Callback<NickNameCheckResponse> {
            override fun onResponse(
                call: Call<NickNameCheckResponse>,
                response: Response<NickNameCheckResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { checkResponse ->
                        if (checkResponse.data.isUnique) {

                            GlobalScope.launch {
                                userInfoManager.updateEmailAndNickname(
                                    email = userInfoManager.getEmail() ?: "",
                                    nickname = nickname
                                )
                            }

                            binding.nicknameCheckDoneTv.visibility = View.VISIBLE
                            binding.nicknameCheckErrorTv.visibility = View.GONE
                        } else {
                            binding.nicknameCheckDoneTv.visibility = View.GONE
                            binding.nicknameCheckErrorTv.visibility = View.VISIBLE
                        }
                    }
                } else {
                    Log.d("[NICKNAME_CHECK]", "닉네임 중복 확인 실패: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "닉네임 중복 확인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NickNameCheckResponse>, t: Throwable) {
                Log.d("[NICKNAME_CHECK]", "닉네임 중복 확인 통신 실패")
                Toast.makeText(requireContext(), "통신 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private val NICKNAME_PATTERN: Pattern = Pattern.compile(
            "^[가-힣a-zA-Z]{1,8}$"
        )
    }

}