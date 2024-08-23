package com.bookmoa.android.group

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.TokenManager
import com.bookmoa.android.databinding.FragmentMakeBinding
import com.bookmoa.android.interfaces.CreateClubRequest
import com.bookmoa.android.interfaces.CreateClubResponse
import com.bookmoa.android.interfaces.PostClubs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakeFragment : Fragment() {
    private var _binding: FragmentMakeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        setupUI()
        setupListeners()
    }

    private fun postClub() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")  // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(PostClubs::class.java)
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            Log.d("MakeFragment", "Token is null or empty")
            return
        }

        val request = CreateClubRequest(
            name = binding.makeClubnameEt.text.toString(),
            intro = binding.makeIntroduceEt.text.toString(),
            notice = binding.makeNoticeEt.text.toString(),
            password = binding.makePasswordEt.text.toString()
        )

        clubApi.createClub("Bearer $token", request).enqueue(object : Callback<CreateClubResponse> {
            override fun onResponse(call: Call<CreateClubResponse>, response: Response<CreateClubResponse>) {
                if (response.isSuccessful) {
                    val clubResponse = response.body()
                    Log.d("MakeFragment", "Club created successfully: ${clubResponse?.data?.clubId}")
                    activity?.runOnUiThread {
                        val communityFragment = CommunityFragment().apply {
                            arguments = Bundle().apply {
                                putInt("clubId", clubResponse?.data?.clubId ?: -1)
                                putString("name", binding.makeClubnameEt.text.toString())
                                putString("intro", binding.makeIntroduceEt.text.toString())
                            }
                        }
                        (activity as MainActivity).switchFragment(communityFragment)
                    }
                } else {
                    Log.e("MakeFragment", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CreateClubResponse>, t: Throwable) {
                Log.e("MakeFragment", "Failed to create club", t)
            }
        })
    }

    private fun setupUI() {
        setupPasswordEditText()
        setupIntroduceEditText()
        setupNoticeEditText()
        setupInitialVisibility()
    }

    private fun setupListeners() {
        setupBackButtonListener()
        setupSwitchListener()
        setupDoneButtonListener()  // Added this line
    }

    private fun setupBackButtonListener() {
        binding.makeBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupDoneButtonListener() {
        binding.makeDoneTv.setOnClickListener {
            postClub()
        }
    }

    private fun setupInitialVisibility() {
        binding.makeLockIv.visibility = View.VISIBLE
        binding.makePerpendicularIv.visibility = View.GONE
        binding.makePasswordTv.visibility = View.GONE
        binding.makePasswordEt.visibility = View.GONE
        binding.makePasswordhintTv.visibility = View.GONE
    }

    private fun setupSwitchListener() {
        binding.makeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateVisibilityBasedOnSwitch(isChecked)
        }
    }

    private fun updateVisibilityBasedOnSwitch(isChecked: Boolean) {
        if (isChecked) {
            binding.makeLockIv.visibility = View.GONE
            binding.makePerpendicularIv.visibility = View.VISIBLE
            binding.makePasswordTv.visibility = View.VISIBLE
            binding.makePasswordEt.visibility = View.VISIBLE
            binding.makePasswordhintTv.visibility = View.VISIBLE
        } else {
            binding.makeLockIv.visibility = View.VISIBLE
            binding.makePerpendicularIv.visibility = View.GONE
            binding.makePasswordTv.visibility = View.GONE
            binding.makePasswordEt.visibility = View.GONE
            binding.makePasswordhintTv.visibility = View.GONE
        }
    }

    private fun setupPasswordEditText() {
        binding.makePasswordEt.filters = arrayOf(InputFilter.LengthFilter(6))

        binding.makePasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val filtered = it.toString().filter { char ->
                        char.isLetterOrDigit()
                    }

                    if (filtered != it.toString()) {
                        binding.makePasswordEt.setText(filtered)
                        binding.makePasswordEt.setSelection(filtered.length)
                    }
                }
            }
        })
    }

    private fun setupIntroduceEditText() {
        binding.makeIntroduceEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.makeIntroduceTv.text = "$length / 20"

                if (length > 20) {
                    binding.makeIntroduceEt.setText(s?.subSequence(0, 20))
                    binding.makeIntroduceEt.setSelection(20)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupNoticeEditText() {
        binding.makeNoticeEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.makeNoticeTv.text = "$length / 200"

                if (length > 200) {
                    binding.makeNoticeEt.setText(s?.subSequence(0, 200))
                    binding.makeNoticeEt.setSelection(200)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
