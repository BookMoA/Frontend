package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.RetrofitInstance
import com.bookmoa.android.TokenManager
import com.bookmoa.android.databinding.FragmentListTop10Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTop10Fragment : Fragment() {

    private var _binding: FragmentListTop10Binding? = null
    private val binding get() = _binding!!
    private var listTop10Adapter: ListTop10Adapter? = null
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListTop10Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TokenManager 초기화
        tokenManager = TokenManager(requireContext())

        // 임시로 토큰 설정 (여기에서 직접 토큰을 설정)
        tokenManager.saveToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCQiIsImF1dGgiOiJST0xFX1VTRVIiLCJuaWNrbmFtZSI6IkJCIiwiZXhwIjoxNzIzOTcwOTA2fQ.FVtSxnnejch66_3Pn7wI_AG5rCHNAJNkCaJAiUOOJHY")

        // RecyclerView 설정
        listTop10Adapter = ListTop10Adapter()
        binding.listTop10Rv.layoutManager = LinearLayoutManager(context)
        binding.listTop10Rv.adapter = listTop10Adapter

        // Click listener 설정


        listTop10Adapter?.setOnItemClickListener(object : ListTop10Adapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: ListTop10Data) {
                val fragment = ListContentFragment.newInstance(data.bookListId) // ID만 전달
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragment)  // fragment_container는 MainActivity의 FrameLayout ID입니다.
                    .addToBackStack(null)
                    .commit()
            }
        })


        // 뒤로가기 버튼 클릭 시 프래그먼트 전환
        binding.listTop10BackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())
        }

        // 데이터 로드
        loadTop10Data()
    }

    private fun loadTop10Data() {
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // API 호출
                    val response = RetrofitInstance.listTop10api.getTop10List("Bearer $token")

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 응답이 성공적일 경우
                            val apiResponse = response.body()
                            if (apiResponse != null && apiResponse.result) {
                                binding.listTop10DataAndtimeTv.text = apiResponse.data?.updatedAt
                                val top10List = apiResponse.data?.bookLists
                                if (top10List != null) {
                                    listTop10Adapter?.updateItems(top10List)
                                } else {
                                    // 데이터가 없는 경우
                                    Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // 응답이 성공적이지만 `result`가 false인 경우
                                Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 응답이 실패한 경우
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            handleNoToken()
        }
    }

    private fun handleNoToken() {
        Toast.makeText(context, "로그인이 필요합니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).switchFragment(StudyFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}