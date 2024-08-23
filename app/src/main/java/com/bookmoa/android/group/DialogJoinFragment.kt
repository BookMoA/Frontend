package com.bookmoa.android.group

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bookmoa.android.MainActivity
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.databinding.FragmentDialogjoinBinding
import com.bookmoa.android.databinding.FragmentToastBinding
import com.bookmoa.android.services.ClubDetailData
import com.bookmoa.android.services.GetClubsDetail
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DialogJoinFragment : DialogFragment() {
    private var _binding: FragmentDialogjoinBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager
    private var clubId: Int? = null
    private var clubPassword: String? = null
    private var isClubJoined: Boolean = false
    private var clubName: String? = null
    private var clubIntro: String? = null

    companion object {
        private const val ARG_CLUB_ID = "club_id"
        private const val ARG_IS_CLUB_JOINED = "is_club_joined"

        fun newInstance(clubId: Int, isClubJoined: Boolean): DialogJoinFragment {
            val fragment = DialogJoinFragment()
            val args = Bundle()
            args.putInt(ARG_CLUB_ID, clubId)
            args.putBoolean(ARG_IS_CLUB_JOINED, isClubJoined)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clubId = it.getInt(ARG_CLUB_ID)
            isClubJoined = it.getBoolean(ARG_IS_CLUB_JOINED, false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDialogjoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager(requireContext())

        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes?.let { params ->
                params.width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                params.dimAmount = 0.7f
                attributes = params
            }
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        binding.dialogjoinJoinBtn.setOnClickListener {
            if (isClubJoined) {
                showCustomToast("이미 모임에 가입되어 있습니다.")
                dismiss()
            } else {
                clubPassword?.let { password ->
                    clubId?.let { id ->
                        val passwordFragment = PasswordFragment.newInstance(password, id, clubName ?: "", clubIntro ?: "")
                        (activity as MainActivity).switchFragment(passwordFragment)
                        dismiss()
                    } ?: run {
                        Log.d("DialogJoinFragment", "clubId is null")
                    }
                } ?: run {
                    Log.d("DialogJoinFragment", "clubPassword is null")
                }
            }
        }

        binding.dialogjoinCancelBtn.setOnClickListener {
            dismiss()
        }
        fetchClubDetail(clubId)
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

    private fun fetchClubDetail(clubId: Int?){
        if (clubId == null) return

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
                if (response.result) { //
                    updateUI(response.data)
                    clubPassword = response.data.password
                } else {
                    Log.d("DialogJoinFragment", "Error: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("DialogJoinFragment", "Exception during network call", e)
            }
        }
    }

    private fun updateUI(clubData: ClubDetailData?) {
        clubData?.let {
            binding.dialogjoinTitleTv.text = it.name
            binding.dialogjoinDescriptionTv.text = it.intro
            binding.dialogjoinCountTv.text = "인원 ${it.memberCount} / 20"
            binding.dialogjoinNoticeTv.text = it.notice
            clubPassword = it.password
            clubName = it.name
            clubIntro = it.intro
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}