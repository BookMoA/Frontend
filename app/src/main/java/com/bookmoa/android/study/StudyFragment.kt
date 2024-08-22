package com.bookmoa.android.study

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.ListTop3Adapter
import com.bookmoa.android.adapter.StorageListAdapter
import com.bookmoa.android.adapter.StudyVpAdapter
import com.bookmoa.android.databinding.FragmentStudyBinding
import com.bookmoa.android.models.ListTop10Data
import com.bookmoa.android.models.StorageListData
import com.bookmoa.android.search.SearchFragment
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class StudyFragment : Fragment() {

    private var timer: Timer? = null
    private val handler = Handler(Looper.getMainLooper())
    lateinit var binding: FragmentStudyBinding
    private lateinit var tokenManager: TokenManager
    private var mystorageRVAdapter: StorageListAdapter? = null
    private var listTop3RVAdapter: ListTop3Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)
        tokenManager = TokenManager()
        binding.studyMyStorageIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(MyStorageFragment())
        }

        binding.studyListChartIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(ListTop10Fragment())
        }

        binding.studySearchIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(SearchFragment())
        }

        mystorageRVAdapter = StorageListAdapter()
        binding.studyMyStorageRv.layoutManager = LinearLayoutManager(context)
        binding.studyMyStorageRv.adapter = mystorageRVAdapter

        listTop3RVAdapter = ListTop3Adapter()
        binding.studyListChartRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.studyListChartRv.adapter = listTop3RVAdapter

        val recommendAdapter = StudyVpAdapter(this)
        binding.studyRecommendVp.adapter = recommendAdapter
        binding.studyRecommendIndicator.setViewPager(binding.studyRecommendVp)

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffsetPx = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.studyRecommendVp.setPageTransformer { page, position ->
            val offset = position * -(2 * pageOffsetPx + pageMarginPx)
            page.translationX = offset
        }

        // ViewPager2 내부의 RecyclerView에 패딩을 추가하여 중앙 정렬 효과
        val recyclerView = binding.studyRecommendVp.getChildAt(0) as RecyclerView
        val padding = (pageMarginPx + pageOffsetPx).toInt()
        recyclerView.setPadding(padding, 0, padding, 0)
        recyclerView.clipToPadding = false

        startAutoSlide(recommendAdapter)

        mystorageRVAdapter?.setOnItemClickListener(object : StorageListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: StorageListData) {
                val fragment = ListDetailFragment.newInstance(data.bookListId) // 전환할 Fragment
                Log.d("FragmentTransaction", "Navigating to ListDetailFragment with ID: ${data.bookListId}")

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        listTop3RVAdapter?.setOnItemClickListener(object : ListTop3Adapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: ListTop10Data) {
                val fragment = ListContentFragment.newInstance(data.bookListId) // ID만 전달
                Log.d("FragmentTransaction", "Navigating to ListDetailFragment with ID: ${data.bookListId}")

                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragment)  // fragment_container는 MainActivity의 FrameLayout ID입니다.
                    .addToBackStack(null)
                    .commit()
            }
        })
        loadMyListData()
        loadTop3Data()

        return binding.root
    }

    private fun startAutoSlide(adapter: StudyVpAdapter) {
        // 새 Timer 객체 생성
        timer?.cancel()
        timer = Timer()
        timer?.scheduleAtFixedRate(3000, 3000) {
            handler.post {
                val nextItem = binding.studyRecommendVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.studyRecommendVp.currentItem = nextItem
                } else {
                    binding.studyRecommendVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

    private fun loadMyListData() {
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // API 호출
                    val response = RetrofitInstance.storageListapi.getStorageList("Bearer $token")

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 응답이 성공적일 경우
                            val apiResponse = response.body()
                            if (apiResponse != null) {
                                val list = apiResponse.data
                                if (list != null) {
                                    // 데이터 중 상위 두 개만 필터링
                                    val filteredDataList = if (list.size > 2) list.subList(0, 2) else list
                                    mystorageRVAdapter?.updateItems(filteredDataList)
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
    private fun loadTop3Data() {
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.listTop10api.getTop10List("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()

                            if (apiResponse != null && apiResponse.result) {
                                val top10List = apiResponse.data?.bookLists
                                val filteredDataList = if (top10List!!.size > 3) top10List!!.subList(0,3) else top10List
                                if (top10List != null) {
                                    listTop3RVAdapter?.updateItems(filteredDataList)
                                } else {
                                    Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            Log.e("API Error", "Response code: ${response.code()}, message: ${response.message()}")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("Network Error", "Exception during API call", e)
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

}