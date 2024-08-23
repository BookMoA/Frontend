package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.TokenManager
import com.bookmoa.android.adapter.SearchNameFragmentAdapter
import com.bookmoa.android.adapter.SearchNameItems
import com.bookmoa.android.databinding.FragmentOutsearchBinding
import com.bookmoa.android.interfaces.GetClubsSearch
import com.bookmoa.android.interfaces.GetClubsSearchClub
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OutSearchFragment : Fragment() {
    private var _binding: FragmentOutsearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager
    private var currentSearchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutsearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var currentClubList: List<GetClubsSearchClub>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        setupSearchView()
        setupRecyclerView()

        binding.searchBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val spinner = binding.searchnameSpinner
        val spinnerContainer = binding.searchnameContainer
        val originalItems = resources.getStringArray(R.array.spinner_options).toList()
        val items = mutableListOf<String>().apply {
            add("정확도순")
            addAll(originalItems)
        }

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        ) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.textSize = 14f

                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = items[0]
                textView.textSize = 14f
                return view
            }
        }

        spinner.adapter = adapter

        spinnerContainer.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                spinnerContainer.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = spinner.width
                spinnerContainer.layoutParams.width = width
                spinner.requestLayout()
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // 첫 번째 항목(현재 선택된 항목)이 아닌 경우에만 변경
                    val selectedItem = items[position]
                    items[0] = selectedItem
                    adapter.notifyDataSetChanged()
                    sortClubList(selectedItem)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinner.setSelection(items.indexOf("정확도순"))
    }
    private fun sortClubList(sortOption: String) {
        val sortedList = when (sortOption) {
            "최신순" -> currentClubList.sortedByDescending { it.createAt }
            "오래된순" -> currentClubList.sortedBy { it.createAt }
            else -> currentClubList  // 정확도순, 별점 높은순, 별점 낮은순은 기본 정렬 유지
        }
        updateRecyclerView(sortedList, currentClubList.size, currentSearchQuery)
    }
    private fun updateRecyclerView(clubList: List<GetClubsSearchClub>, totalElements: Int, searchQuery: String) {
        currentClubList = clubList  // 현재 클럽 리스트 저장
        val searchItems = clubList.map { club ->
            SearchNameItems(
                title = club.name,
                count = "${club.memberCount}/20",
                description = club.intro
            )
        }
        val nameAdapter = binding.searchnameRv.adapter as? SearchNameFragmentAdapter
        if (nameAdapter != null) {
            nameAdapter.updateData(searchItems, searchQuery)
        } else {
            binding.searchnameRv.adapter = SearchNameFragmentAdapter(searchItems, searchQuery)
        }
        binding.searchnameTv.text = "총 ${totalElements}건의 검색결과"
    }

    private fun fetchClubSearch(query: String) {
        currentSearchQuery = query
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop") // Replace with your actual API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsSearch::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("OutSearchFragment", "Token is null or empty")
            return
        }

        lifecycleScope.launch {
            try {
                val response = clubApi.searchClubs(bearerToken, word = query)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    val searchResults = responseData?.clubList ?: emptyList()
                    val totalElements = responseData?.totalElements ?: 0

                    currentClubList = searchResults  // 검색 결과 저장
                    updateRecyclerView(searchResults, totalElements, query)
                } else {
                    Log.e("OutSearchFragment", "API call failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("OutSearchFragment", "Exception during API call: ${e.message}")
            }
        }

    }


    private fun setupSearchView() {
        binding.searchSv.apply {
            queryHint = "검색어를 입력하세요"
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = false

            val searchEditText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setHintTextColor(Color.parseColor("#999999"))
                setTextColor(Color.BLACK)
            }
            val searchPlate = findViewById<View>(androidx.appcompat.R.id.search_plate)
            searchPlate.setBackgroundColor(Color.TRANSPARENT)

            val submitArea = findViewById<View>(androidx.appcompat.R.id.submit_area)
            submitArea.setBackgroundColor(Color.TRANSPARENT)

            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        // Clear the RecyclerView when the search text is empty
                        updateRecyclerView(emptyList(), 0, "")
                    } else {
                        // Trigger the search when there is text
                        fetchClubSearch(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun setupRecyclerView() {
        binding.searchnameRv.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}