package com.bookmoa.android.group

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.databinding.FragmentPasswordBinding
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.CreateClubMemberRequest
import com.bookmoa.android.services.CreateClubMemberResponse
import com.bookmoa.android.services.PostClubsMembers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager

    private var isInitialInput = true
    private var correctPassword: String? = null
    private var clubId: Int? = null
    private var clubName: String? = null
    private var clubIntro: String? = null

    private lateinit var api: ApiService

    companion object {
        private const val ARG_CORRECT_PASSWORD = "correct_password"
        private const val ARG_CLUB_ID = "club_id"
        private const val ARG_CLUB_NAME = "club_name"
        private const val ARG_CLUB_INTRO = "club_intro"

        fun newInstance(password: String, clubId: Int, clubName: String, clubIntro: String): PasswordFragment {
            val fragment = PasswordFragment()
            val args = Bundle().apply {
                putString(ARG_CORRECT_PASSWORD, password)
                putInt(ARG_CLUB_ID, clubId)
                putString(ARG_CLUB_NAME, clubName)
                putString(ARG_CLUB_INTRO, clubIntro)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            correctPassword = it.getString(ARG_CORRECT_PASSWORD)
            clubId = it.getInt(ARG_CLUB_ID)
            clubName = it.getString(ARG_CLUB_NAME)
            clubIntro = it.getString(ARG_CLUB_INTRO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager()


        binding.passwordBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        correctPassword?.let {
            setupEditTextAutoMove(it)
        }
    }

    private fun postClubMember(password: String) {

        val clubId = this.clubId ?: run {
            Log.d("PasswordFragment", "Club ID is null")
            return
        }

        val request = CreateClubMemberRequest(clubId, password)

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            api.createClubMember(request).enqueue(object :
                Callback<CreateClubMemberResponse> {
                override fun onResponse(call: Call<CreateClubMemberResponse>, response: Response<CreateClubMemberResponse>) {
                    if (response.isSuccessful) {
                        Log.d("PasswordFragment", "Club member created successfully")
                        // Switch to CommunityFragment after successful API call
                        activity?.runOnUiThread {
                            val communityFragment = CommunityFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("clubId", clubId ?: -1)
                                    putString("name", clubName)
                                    putString("intro", clubIntro)
                                }
                            }
                            (activity as MainActivity).switchFragment(communityFragment)
                        }
                    } else {
                        Log.d("PasswordFragment", "Failed to create club member: ${response.errorBody()?.string()}")
                        // Handle error (e.g., show error message to user)
                    }
                }

                override fun onFailure(call: Call<CreateClubMemberResponse>, t: Throwable) {
                    Log.d("PasswordFragment", "Network error: ${t.message}")
                    // Handle network error (e.g., show error message to user)
                }
            })
        }

        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")  // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(PostClubsMembers::class.java)
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            Log.d("PasswordFragment", "Token is null or empty")
            return
        }

        val clubId = this.clubId ?: run {
            Log.d("PasswordFragment", "Club ID is null")
            return
        }

        val request = CreateClubMemberRequest(clubId, password)

        clubApi.createClubMember("Bearer $token", request).enqueue(object :
            Callback<CreateClubMemberResponse> {
            override fun onResponse(call: Call<CreateClubMemberResponse>, response: Response<CreateClubMemberResponse>) {
                if (response.isSuccessful) {
                    Log.d("PasswordFragment", "Club member created successfully")
                    // Switch to CommunityFragment after successful API call
                    activity?.runOnUiThread {
                        val communityFragment = CommunityFragment().apply {
                            arguments = Bundle().apply {
                                putInt("clubId", clubId ?: -1)
                                putString("name", clubName)
                                putString("intro", clubIntro)
                            }
                        }
                        (activity as MainActivity).switchFragment(communityFragment)
                    }
                } else {
                    Log.d("PasswordFragment", "Failed to create club member: ${response.errorBody()?.string()}")
                    // Handle error (e.g., show error message to user)
                }
            }

            override fun onFailure(call: Call<CreateClubMemberResponse>, t: Throwable) {
                Log.d("PasswordFragment", "Network error: ${t.message}")
                // Handle network error (e.g., show error message to user)
            }
        })

         */
    }

    private fun setupEditTextAutoMove(correctPassword: String) {
        val editTexts = listOf(
            binding.passwordEt1,
            binding.passwordEt2,
            binding.passwordEt3,
            binding.passwordEt4,
            binding.passwordEt5,
            binding.passwordEt6
        )

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (isInitialInput) {
                        if (i == editTexts.size - 1 && s?.length == 1) {
                            validatePassword(correctPassword)
                            isInitialInput = false
                        }
                    } else {
                        validatePassword(correctPassword)
                    }

                    if (s?.length == 1 && i < editTexts.size - 1) {
                        editTexts[i + 1].requestFocus()
                    }
                }
            })
        }
    }

    private fun validatePassword(correctPassword: String) {
        val userInput = binding.passwordEt1.text.toString() +
                binding.passwordEt2.text.toString() +
                binding.passwordEt3.text.toString() +
                binding.passwordEt4.text.toString() +
                binding.passwordEt5.text.toString() +
                binding.passwordEt6.text.toString()

        if (userInput == correctPassword) {
            binding.passwordErrorTv.visibility = View.GONE
            hideKeyboard()  // 키보드 숨기기
            postClubMember(userInput)
        } else {
            binding.passwordErrorTv.visibility = View.VISIBLE
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
