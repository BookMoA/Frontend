package com.bookmoa.android.group

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bookmoa.android.MainActivity
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.databinding.FragmentCommunitymanagevpBinding
import com.bookmoa.android.databinding.FragmentToastBinding
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.ClubDetailData
import com.bookmoa.android.services.GetClubs
import com.bookmoa.android.services.GetClubsDetail
import com.bookmoa.android.services.PatchClubs
import com.bookmoa.android.services.PatchClubsRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.bookmoa.android.services.ClubDetailResponse
import com.bookmoa.android.services.GetClubsDetail
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class CommunityManageFragment : Fragment() {
    private var _binding: FragmentCommunitymanagevpBinding? = null
    private val binding get() = _binding!!
    private var isIntroduceEditMode = false
    private var isNoticeEditMode = false
    private var clubId: Int? = null
    private lateinit var tokenManager: TokenManager
    private var isReader = false
    private var myMemberId: Int? = null

    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunitymanagevpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager()

        clubId = arguments?.getInt("clubId", 0)

        binding.communitymanageQuitTv.setOnClickListener {
            if (isReader) {
                val nextLeaderFragment = NextLeaderFragment()
                val bundle = Bundle().apply {
                    putInt("clubId", clubId ?: 0) // Pass the clubId
                }
                nextLeaderFragment.arguments = bundle
                (activity as MainActivity).switchFragment(nextLeaderFragment)
            } else {
                val dialogQuitFragment = DialogQuitFragment()
                dialogQuitFragment.show(parentFragmentManager, "DialogQuitFragment")
            }
        }


        setupInitialState()
        setupIntroduceEditText()
        setupNoticeEditText()
        setupEditModeToggles()
        setupTouchListener()
        setupCopyFeature()
        setupTextWatchers()

        fetchClubDetailM(clubId)
        fetchMyClub()

    }
    private fun setupTextWatchers() {
        binding.communitymanagevpIntroduceEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Call the function to patch data when the text changes
                patchClubData()
            }
        })

        binding.communitymanagevpNoticeEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Call the function to patch data when the text changes
                patchClubData()
            }
        })
    }
    private fun patchClubData() {
        val intro = binding.communitymanagevpIntroduceEt.text.toString()
        val notice = binding.communitymanagevpNoticeEt.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val patchClubsApi = retrofit.create(PatchClubs::class.java)
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            Log.e("CommunityManageFragment", "Token is null or empty")
            showErrorMessage("Authentication error. Please log in again.")
            return
        }

        val bearerToken = "Bearer $token"
        val requestBody = PatchClubsRequest(clubId?:0, intro, notice)

        lifecycleScope.launch {
            try {
                Log.d("CommunityManageFragment", "Patching club data...")
                val response = patchClubsApi.patchClubs(bearerToken, requestBody)

                if (response.result) {
                    Log.d("CommunityManageFragment", "Club data patched successfully")
                } else {
                    Log.e("CommunityManageFragment", "Failed to patch club data: ${response.description}")
                    showErrorMessage("Failed to update club information: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityManageFragment", "Error patching club data", e)
                when (e) {
                    is IOException -> showErrorMessage("Network error. Please check your internet connection.")
                    is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        Log.e("CommunityManageFragment", "HTTP Error: ${e.code()}, Error Body: $errorBody")
                        showErrorMessage("Server error: ${e.code()}. Please try again later.")
                    }
                    else -> showErrorMessage("An unexpected error occurred. Please try again.")
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    private fun fetchMyClub() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubs::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("CommunityMemberFragment", "Token is null or empty")
            return
        }

        // 코루틴 스코프에서 네트워크 호출을 실행
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = clubApi.getClubs(bearerToken)
                Log.d("CommunityMemberFragment", "Raw response: $response")
                if (response.result) {
                    myMemberId = response.data.memeberId
                    isReader = response.data.reader
                    Log.d("CommunityMemberFragment", "Updated Member ID: $myMemberId, Reader: $isReader")
                } else {
                    Log.e("CommunityMemberFragment", "Failed to fetch clubs: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityMemberFragment", "Error fetching clubs", e)
            }
        }
    }

    private fun fetchClubDetailM(clubId: Int?) {
        if (clubId == null) return


        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getClubDetail(clubId = clubId.toLong()).enqueue(object: Callback<ClubDetailResponse> {
                override fun onResponse(
                    call: Call<ClubDetailResponse>,
                    response: Response<ClubDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.result) {
                            updateUIWithClubDetails(apiResponse.data)
                        } else {
                            if (apiResponse != null) {
                                Log.d("GroupFragment", "Failed to get club details: ${apiResponse.description}")
                            }
                        }
                    } else {
                        Log.d("GroupFragment", "${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<ClubDetailResponse>, t: Throwable) {
                    Log.d("GroupFragment", "Error fetching club details")
                }

            })
        }
        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsDetail::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"
        if (token.isNullOrEmpty()) {
            Log.d("GroupFragment", "Token is null or empty")
            return
        }

        lifecycleScope.launch {
            try {
                val response = clubApi.getClubDetail(bearerToken, clubId.toLong())
                if (response.result) {
                    updateUIWithClubDetails(response.data)
                } else {
                    Log.d("GroupFragment", "Failed to get club details: ${response.description}")
                }
            } catch (e: HttpException) {
                Log.e("GroupFragment", "Error fetching club details: ${e.message()}")
            } catch (e: Exception) {
                Log.e("GroupFragment", "Unexpected error: ${e.localizedMessage}")
            }
        }

         */
    }

    private fun updateUIWithClubDetails(data: ClubDetailData) {
        binding.communitymanagevpIntroduceEt.setText(data.intro)
        binding.communitymanagevpNoticeEt.setText(data.notice)
        binding.communitymanagevpCopyTv.text = data.code
        binding.communitymanagevpMemberTv.text = "${data.memberCount} / 20"
    }

    private fun setupInitialState() {
        binding.communitymanagevpIntroduceTv.visibility = View.GONE
        binding.communitymanagevpNoticeTv.visibility = View.GONE
        binding.communitymanagevpIntroduceEt.isEnabled = false
        binding.communitymanagevpNoticeEt.isEnabled = false
    }

    private fun setupEditModeToggles() {
        binding.communitymanagevpIntroduceIv.setOnClickListener {
            toggleEditMode(
                binding.communitymanagevpIntroduceIv,
                binding.communitymanagevpIntroduceTv,
                binding.communitymanagevpIntroduceEt
            )
            isIntroduceEditMode = true
        }

        binding.communitymanagevpNoticeIv.setOnClickListener {
            toggleEditMode(
                binding.communitymanagevpNoticeIv,
                binding.communitymanagevpNoticeTv,
                binding.communitymanagevpNoticeEt
            )
            isNoticeEditMode = true
        }
    }

    private fun toggleEditMode(imageView: View, textView: View, editText: View) {
        imageView.visibility = View.GONE
        textView.visibility = View.VISIBLE
        editText.isEnabled = true
        (editText as? android.widget.EditText)?.apply {
            requestFocus()
            showKeyboard(this)
        }
    }

    private fun showKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setupTouchListener() {
        binding.root.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (isIntroduceEditMode && !isViewClicked(event, binding.communitymanagevpIntroduceEt)) {
                    exitEditMode(binding.communitymanagevpIntroduceIv, binding.communitymanagevpIntroduceTv, binding.communitymanagevpIntroduceEt)
                    isIntroduceEditMode = false
                    return@setOnTouchListener true
                }
                if (isNoticeEditMode && !isViewClicked(event, binding.communitymanagevpNoticeEt)) {
                    exitEditMode(binding.communitymanagevpNoticeIv, binding.communitymanagevpNoticeTv, binding.communitymanagevpNoticeEt)
                    isNoticeEditMode = false
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun isViewClicked(event: MotionEvent, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        return (x >= location[0] && x <= (location[0] + view.width) &&
                y >= location[1] && y <= (location[1] + view.height))
    }

    private fun exitEditMode(imageView: View, textView: View, editText: View) {
        imageView.visibility = View.VISIBLE
        textView.visibility = View.GONE
        editText.isEnabled = false
        hideKeyboard()
    }

    private fun setupIntroduceEditText() {
        val initialIntroduceLength = binding.communitymanagevpIntroduceEt.text?.length ?: 0
        binding.communitymanagevpIntroduceTv.text = "$initialIntroduceLength / 20"

        binding.communitymanagevpIntroduceEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.communitymanagevpIntroduceTv.text = "$length / 20"

                if (length > 20) {
                    binding.communitymanagevpIntroduceEt.setText(s?.subSequence(0, 20))
                    binding.communitymanagevpIntroduceEt.setSelection(20)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupNoticeEditText() {
        val initialNoticeLength = binding.communitymanagevpNoticeEt.text?.length ?: 0
        binding.communitymanagevpNoticeTv.text = "$initialNoticeLength / 200"

        binding.communitymanagevpNoticeEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.communitymanagevpNoticeTv.text = "$length / 200"

                if (length > 200) {
                    binding.communitymanagevpNoticeEt.setText(s?.subSequence(0, 200))
                    binding.communitymanagevpNoticeEt.setSelection(200)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun toggleEditIconsVisibility(isReader: Boolean) {
        if (isReader) {
            binding.communitymanagevpIntroduceIv.visibility = View.VISIBLE
            binding.communitymanagevpNoticeIv.visibility = View.VISIBLE
        } else {
            binding.communitymanagevpIntroduceIv.visibility = View.GONE
            binding.communitymanagevpNoticeIv.visibility = View.GONE
        }
    }

    private fun setupCopyFeature() {
        binding.communitymanagevpCopyIv.setOnClickListener {
            val groupCode = binding.communitymanagevpCopyTv.text.toString()
            copyToClipboard(groupCode)
            showCustomToast("그룹 코드가 복사되었습니다.")
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("그룹 코드", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showCustomToast(message: String) {
        val toastBinding = FragmentToastBinding.inflate(LayoutInflater.from(requireContext()))
        toastBinding.toastTv.text = message

        Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = toastBinding.root
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

