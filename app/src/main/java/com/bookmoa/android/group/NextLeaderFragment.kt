package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.NextLeaderFragmentAdapter
import com.bookmoa.android.adapter.NextLeaderItems
import com.bookmoa.android.databinding.FragmentNextleaderBinding

class NextLeaderFragment: Fragment() {
    private var _binding: FragmentNextleaderBinding? = null
    private val binding get() = _binding!!

    private lateinit var nextleaderAdapter: NextLeaderFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextleaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextleaderRv.layoutManager = GridLayoutManager(context, 3)

        val nextleaderItems = listOf(
            NextLeaderItems(true, R.drawable.icon_profile,"닉네임",true),
            NextLeaderItems(false, R.drawable.icon_profile,"닉네임",false),
            NextLeaderItems(false, R.drawable.icon_profile,"닉네임",false),
            NextLeaderItems(false, R.drawable.icon_profile,"닉네임",false),
            NextLeaderItems(false, R.drawable.icon_profile,"닉네임",false),
        )

        binding.nextleaderBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        nextleaderAdapter = NextLeaderFragmentAdapter(nextleaderItems)
        binding.nextleaderRv.adapter = nextleaderAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}