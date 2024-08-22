package com.bookmoa.android.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bookmoa.android.models.SearchBookData
import com.bookmoa.android.models.SearchBookListData
import com.bookmoa.android.models.SearchMemoData

class SearchViewModel : ViewModel() {
    val titleResults = MutableLiveData<List<SearchBookData>>()
    val authorResults = MutableLiveData<List<SearchBookData>>()
    val bookListResults = MutableLiveData<List<SearchBookListData>>()
    val memoResults = MutableLiveData<List<SearchMemoData>>()
}