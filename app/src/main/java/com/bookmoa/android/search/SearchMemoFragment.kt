package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.adapter.SearchMemoAdapter
import com.bookmoa.android.databinding.FragmentSearchMemoBinding
import com.bookmoa.android.models.SearchMemoData
import com.bookmoa.android.models.SearchMemoResponse
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchMemoFragment : Fragment() {

    private var _binding: FragmentSearchMemoBinding? = null
    private val binding get() = _binding!!
    private lateinit var memoAdapter: SearchMemoAdapter
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMemoBinding.inflate(inflater, container, false)
        tokenManager = TokenManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        memoAdapter = SearchMemoAdapter(itemClickedListener = {

        })
        binding.searchTitleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = memoAdapter
        }
    }

    fun searchMemoLists(query: String, callback: (List<SearchMemoData>) -> Unit) {
        val token = tokenManager.getToken() ?: return
        RetrofitInstance.searchListApi.getBookMemos("Bearer $token", query)
            .enqueue(object : Callback<SearchMemoResponse> {
                override fun onResponse(call: Call<SearchMemoResponse>, response: Response<SearchMemoResponse>) {
                    if (response.isSuccessful) {
                        val memos = response.body()?.data ?: emptyList()
                        updateMemo(memos)
                        callback(memos)
                    } else {
                        Log.e("SearchMemoFragment", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SearchMemoResponse>, t: Throwable) {
                    Log.e("SearchMemoFragment", "Failure: ${t.message}")
                }
            })
    }

    fun updateMemo(memos: List<SearchMemoData>) {
        if (memos.isNullOrEmpty()) {
            binding.searchMemoAvailable.visibility = View.GONE
            binding.searchMemoNotAvailable.visibility = View.VISIBLE
        } else {
            binding.searchMemoAvailable.visibility = View.VISIBLE
            binding.searchMemoNotAvailable.visibility = View.GONE
            memoAdapter.updateItems(memos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}