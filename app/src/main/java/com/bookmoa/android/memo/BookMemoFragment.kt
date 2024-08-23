package com.bookmoa.android.memo

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.BookMemoAdapter
import com.bookmoa.android.databinding.FragmentBookMemoBinding
import com.bookmoa.android.models.BookMemoDTO
import com.bookmoa.android.models.BookMemoDeleteResponse
import com.bookmoa.android.models.BookMemoResponse
import com.bookmoa.android.models.SearchBookMemoResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.services.UserInfoManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookMemoFragment : Fragment(), BookMemoAdapter.OnItemSelectedListener {

    private lateinit var binding: FragmentBookMemoBinding
    private lateinit var api: ApiService
    private lateinit var tokenManager: TokenManager

    private var isDeleteMode = false
    private val selectedItems = mutableListOf<BookMemoDTO>()
    private lateinit var adapter: BookMemoAdapter

    private lateinit var allBooks: MutableList<BookMemoDTO>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookMemoBinding.inflate(layoutInflater, container, false)

        initRecyclerView()
        val userInfoManager = UserInfoManager(requireContext())
        // tokenManager = TokenManager(requireContext())

        adapter= BookMemoAdapter(mutableListOf(), isDeleteMode, selectedItems, this)
        binding.memeoRv.adapter= adapter

        binding.searchTv.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                filterBooks(query)
            }
        })

        binding.memoEditBtn.setOnClickListener {
            showPopupMenu(it)
        }

        binding.chooseBtn.setOnClickListener {
            Log.d("[MEMO/DELETE]", "선택 완료 버튼 클릭됨")
            deleteSelectedBooks()
        }

        GlobalScope.launch {
            val accessToken = userInfoManager.getTokens()
            // val accessToken = tokenManager.getToken()

            // accessToken을 로그로 출력
            Log.d("[TOKEN_CHECK]", "Access Token: $accessToken")
        }

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.BookMemoList(1).enqueue(object: Callback<BookMemoResponse> {
                override fun onResponse(
                    call: Call<BookMemoResponse>,
                    response: Response<BookMemoResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {bookMemoResponse ->
                            Log.d("[MEMO]", "BookMemoList: $bookMemoResponse")
                            Log.d("[MEMO]", "독서메모 전체 조회 성공")
                            allBooks = bookMemoResponse.data.memberBookPreviewDTOList
                            adapter.updateData(bookMemoResponse.data.memberBookPreviewDTOList.toMutableList())
                        }
                    } else {
                        Log.d("[MEMO]", "독서메모 전체 조회 실패: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<BookMemoResponse>, t: Throwable) {
                    Log.d("[MEMO]", "독서메모 전체 조회 통신 실패")
                }
            })

            /*
            api = ApiService.create()
            val token = tokenManager.getToken()

            if (token != null) {
                api.BookMemoTest("Bearer $token", 1).enqueue(object : Callback<BookMemoResponse> {
                    override fun onResponse(
                        call: Call<BookMemoResponse>,
                        response: Response<BookMemoResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { bookMemoResponse ->
                                Log.d("[MEMO]", "BookMemoList: $bookMemoResponse")
                                Log.d("[MEMO]", "독서메모 전체 조회 성공")
                                val adapter =
                                    BookMemoAdapter(bookMemoResponse.data.memberBookPreviewDTOList)
                                binding.memeoRv.adapter = adapter
                            }
                        } else {
                            Log.d("[MEMO]", "독서메모 전체 조회 실패: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<BookMemoResponse>, t: Throwable) {
                        Log.d("[MEMO]", "독서메모 전체 조회 통신 실패")
                    }
                })

             */
            }

        return binding.root

    }

    private fun initRecyclerView() {
        with(binding) {
            memeoRv.apply {
                layoutManager = GridLayoutManager(context, 3)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        val menuInflater: MenuInflater = popupMenu.menuInflater
        menuInflater.inflate(R.menu.bookmemo_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.delete_memo_menu -> {
                    binding.chooseBtn.visibility = View.VISIBLE
                    enableDeleteMode()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    override fun onItemSelectedChanged() {
        updateButtonState()  // 선택 상태 변경 시 버튼 상태 업데이트
    }

    fun updateButtonState() {
        if (selectedItems.isNotEmpty()) {
            binding.chooseBtn.setBackgroundColor(resources.getColor(R.color.button_color)) // 버튼 활성화 색상으로 변경
        } else {
            binding.chooseBtn.setBackgroundColor(resources.getColor(R.color.grey5)) // 버튼 비활성화 색상으로 변경
        }
    }

    private fun enableDeleteMode() {
        isDeleteMode = true
        adapter.setDeleteMode(isDeleteMode)
        // updateUIForDeleteMode()
    }

    private fun deleteSelectedBooks() {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            Log.d("[MEMO/DELETE]", "삭제하려는 아이템 수: ${selectedItems.size}")

            if (selectedItems.isNotEmpty()) {

                val bookToDelete = selectedItems.first()
                Log.d("[MEMO/DELETE]", "독서메모 책 선택 - $bookToDelete: ${bookToDelete.memberBookId}")

                api.DeleteBookMemo(bookToDelete.memberBookId).enqueue(object : Callback<BookMemoDeleteResponse> {
                    override fun onResponse(
                        call: Call<BookMemoDeleteResponse>,
                        response: Response<BookMemoDeleteResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("[MEMO/DELETE]", "독서메모 책 삭제 성공 - $bookToDelete: ${bookToDelete.memberBookId}")
                            // selectedItems에서 제거
                            selectedItems.remove(bookToDelete)

                            // allBooks에서 제거
                            allBooks.remove(bookToDelete)

                            // adapter에서 제거
                            adapter.removeItem(bookToDelete)

                            if (selectedItems.isEmpty()) {
                                binding.chooseBtn.setBackgroundColor(resources.getColor(R.color.grey5))
                                binding.chooseBtn.visibility = View.GONE
                                isDeleteMode = false
                                adapter.setDeleteMode(isDeleteMode)
                            }
                        } else {
                            Log.d("[MEMO/DELETE]", "Failed to delete item: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<BookMemoDeleteResponse>, t: Throwable) {
                        Log.d("[MEMO/DELETE]", "통신 실패 - Error deleting item", t)
                    }
                })
            }
        }
    }

    private fun filterBooks(query: String) {
        if (::allBooks.isInitialized) {
            val filteredBooks = allBooks.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.writer.contains(query, ignoreCase = true)
            }
            adapter.updateData(filteredBooks.toMutableList())
        } else {
            Log.d("[MEMO]", "전체 책 목록이 초기화되지 않았습니다.")
        }
    }
}