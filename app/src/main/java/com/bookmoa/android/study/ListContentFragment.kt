import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentListContentBinding
import com.bookmoa.android.models.ErrorResponse
import com.bookmoa.android.models.ListContentData
import com.bookmoa.android.models.ListContentResponse
import com.bookmoa.android.models.StorageListResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.BooksRequest
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.study.MyListStorageFragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListContentFragment : Fragment() {

    private lateinit var binding: FragmentListContentBinding
    private lateinit var itemListContentAdapter: ListContentAdapter
    private val selectedIds = mutableSetOf<Int>()

    private lateinit var api: ApiService

    companion object {
        private const val ARG_ID = "id"
        fun newInstance(id: Int): ListContentFragment {
            val fragment = ListContentFragment()
            val args = Bundle()
            args.putInt(ARG_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    private var item: ListContentData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id = it.getInt(ARG_ID)

            lifecycleScope.launch {
                val fetchedItem = fetchDataById(id)
                if (fetchedItem != null) {
                    item = fetchedItem
                    updateUI(item)
                }
            }
        }
    }


    private suspend fun fetchDataById(id: Int): ListContentData? {
        api = ApiService.createWithHeader(requireContext())

        return withContext(Dispatchers.IO) {
            try {
                val response = ApiService.createWithHeader(requireContext()).getBookListById(id)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.data?.let { data ->
                        return@withContext ListContentData(
                            bookListId = data.bookListId,
                            img = data.img ?: "",
                            title = data.title,
                            spec = data.spec,
                            listStatus = data.listStatus,
                            nickname = data.nickname,
                            likeCnt = data.likeCnt,
                            bookCnt = data.bookCnt,
                            likeStatus = data.likeStatus,
                            books = data.books ?: emptyList()
                        )
                    }
                } else {
                    // Handle unsuccessful response
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "서버 통신 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            null
        }
    }




    private fun updateUI(item: ListContentData?) {
        item?.let {
            Glide.with(this)
                .load(it.img)
                .into(binding.listContentImgIv)

            binding.listContentIntroduceTitleTv.text = it.title
            binding.listContentLikeNumTv.text = "${it.likeCnt}"
            binding.listContentOwnerTv.text =it.nickname
            binding.listContentNumTv.text = "총 ${it.bookCnt}권"
            binding.listContentIntroduceDetailTv.text = it.spec

            itemListContentAdapter = ListContentAdapter(selectedIds) { itemId ->
                updateSubmitButtonState()
            }
            binding.listContentRvList.layoutManager = LinearLayoutManager(context)
            binding.listContentRvList.adapter = itemListContentAdapter
            itemListContentAdapter.updateItems(it.books)
        }
    }

    private fun updateSubmitButtonState() {
        if (selectedIds.isNotEmpty()) {
            binding.listContentSubmitBtn.isEnabled = true
            binding.listContentSubmitBtn.setBackgroundColor(resources.getColor(R.color.buttonColor, null))
        } else {
            // 버튼 비활성화
            binding.listContentSubmitBtn.isEnabled = false
            binding.listContentSubmitBtn.setBackgroundColor(resources.getColor(R.color.grey3, null))
        }
    }
    private fun postBookId(bookListId: Int, callback: (Boolean, String?) -> Unit) {
        api.postBookId(
            bookListId = bookListId
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("ListContentFragment", "post Success")
                    callback(true, response.body()?.string())
                } else {
                    // 실패 시 콜백 호출
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = parseErrorBody(errorBody)
                    callback(false, errorResponse?.description)
                    Log.d("ListContentFragment", "post error: ${errorResponse?.description}")
                    Toast.makeText(context,"'${errorResponse?.description}",Toast.LENGTH_SHORT).show()
                    callback(false, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ListContetFragment", "POST request failed: ${t.message}")
                callback(false, t.message)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContentBinding.inflate(inflater, container, false)
        updateSubmitButtonState()

        binding.listContentBackIcon.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.listContentStorageStoreIv.setOnClickListener{
            Log.d("test","${item!!.bookListId}")
            postBookId(item!!.bookListId) { success, response ->
                if (success) {
                    Log.d("Success", "${response}")
                    Toast.makeText(context,"'${item!!.title}' 리스트가 보관함에 담겼습니다",Toast.LENGTH_SHORT).show()
                } else {
                  Log.d("Failed", "${response}")
                }
            }
        }

        binding.listContentSubmitBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putIntegerArrayList("selected_ids", ArrayList(selectedIds))
            }

            val nextFragment = MyListStorageFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, nextFragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
    private fun parseErrorBody(errorBody: String?): ErrorResponse? {
        return try {

            val json = JSONObject(errorBody)

            val status = json.optString("status")
            val code = json.optString("code")
            val result = json.optBoolean("result")
            val description = json.optString("description")

            ErrorResponse(status, code, result, description)
        } catch (e: Exception) {
            Log.e("ListContentFragment", "Error parsing error body: ${e.message}")
            null
        }
    }
}