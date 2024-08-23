package com.bookmoa.android.group

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bookmoa.android.databinding.FragmentDialogexpelBinding
import com.bookmoa.android.databinding.FragmentToastBinding
import com.bookmoa.android.services.DeleteClubsMembersDrop
import com.bookmoa.android.services.DeleteMemberRequest
import com.bookmoa.android.services.DeleteMemberResponse
import com.bookmoa.android.services.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class DialogExpelFragment : DialogFragment() {
    private var _binding: FragmentDialogexpelBinding? = null
    private val binding get() = _binding!!
    private var onConfirmClick: (() -> Unit)? = null
    private var memberId: Int? = null
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogexpelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TokenManager
        tokenManager = TokenManager(requireContext())

        // Retrieve memberId from arguments
        memberId = arguments?.getInt("memberId")
        val memberName = arguments?.getString("memberName") ?: "닉네임"

        binding.dialogexpelTv.text = "타 유저 ${memberName}님을(를)\n정말 내보내시겠어요?"

        binding.dialogexpelConfirmBtn.setOnClickListener {
            memberId?.let { id ->
                deleteMemberFromServer(id)
            }
            onConfirmClick?.invoke()
        }

        binding.dialogexpelCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
                dimAmount = 0.7f
                flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            }
        }
        return dialog
    }

    private fun deleteMemberFromServer(memberId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val deleteService = retrofit.create(DeleteClubsMembersDrop::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            showCustomToast("Authentication error. Please log in again.")
            return
        }

        val request = DeleteMemberRequest(memberId)
        val call = deleteService.deleteMember(bearerToken, request)

        call.enqueue(object : Callback<DeleteMemberResponse> {
            override fun onResponse(call: Call<DeleteMemberResponse>, response: Response<DeleteMemberResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null && result.result) {
                        showCustomToast("타 유저 ${binding.dialogexpelTv.text}님을(를) 내보냈습니다.")
                    } else {
                        showCustomToast("멤버 삭제 실패: ${result?.description ?: "Unknown error"}")
                    }
                    dismiss()
                } else {
                    showCustomToast("서버 오류: ${response.code()}")
                    dismiss()
                }
            }

            override fun onFailure(call: Call<DeleteMemberResponse>, t: Throwable) {
                if (t is IOException) {
                    showCustomToast("네트워크 오류. 인터넷 연결을 확인하세요.")
                } else {
                    showCustomToast("알 수 없는 오류 발생. 다시 시도해주세요.")
                }
                dismiss()
            }
        })
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

    fun setConfirmClickListener(listener: () -> Unit) {
        onConfirmClick = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
