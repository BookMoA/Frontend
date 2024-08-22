package com.bookmoa.android.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.adapter.SearchBookListAdapter
import com.bookmoa.android.databinding.FragmentSearchBookListBinding
import com.bookmoa.android.models.SearchBookListData
import com.bookmoa.android.models.SearchBookListResponse
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBookListFragment : Fragment() {

    private var _binding: FragmentSearchBookListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookListAdapter: SearchBookListAdapter
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBookListBinding.inflate(inflater, container, false)
        tokenManager = TokenManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        bookListAdapter = SearchBookListAdapter(itemClickedListener = { bookListData ->
//            val fragment = ListContentFragment.newInstance(bookListData.id)
//            Log.d("FragmentTransaction", "Navigating to ListContentFragment with ID: ${bookListData.id}")
//
//            // 프래그먼트를 전환
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, fragment)
//                .addToBackStack(null)
//                .commit()
        })


        binding.searchBookListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookListAdapter
        }
    }

    fun searchBookLists(query: String, callback: (List<SearchBookListData>) -> Unit) {
        val token = tokenManager.getToken()
        RetrofitInstance.searchListApi.getBookList("Bearer $token", query)
            .enqueue(object : Callback<SearchBookListResponse> {
                override fun onResponse(call: Call<SearchBookListResponse>, response: Response<SearchBookListResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data ?: emptyList()
                        updateBookList(data)
                        callback(data)
                    } else {
                        Log.e("SearchBookListFragment", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SearchBookListResponse>, t: Throwable) {
                    Log.e("SearchBookListFragment", "Failure: ${t.message}")
                }
            })
    }


    fun updateBookList(bookLists: List<SearchBookListData>) {
        if (bookLists.isNullOrEmpty()) {
            binding.searchBookListAvailable.visibility = View.GONE
            binding.searchBookListNotAvailable.visibility = View.VISIBLE
        } else {
            binding.searchBookListAvailable.visibility = View.VISIBLE
            binding.searchBookListNotAvailable.visibility = View.GONE
            bookListAdapter.submitList(bookLists)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}