package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentMyListBinding


class MyListFragment : Fragment(), OnEditButtonClickListener {
    lateinit var binding: FragmentMyListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(inflater, container, false)

        val fragment = MyListUsualFrament()
        childFragmentManager.beginTransaction()
            .replace(R.id.my_list_container, fragment)
            .commit()

        return binding.root
    }

    override fun onEditButtonClick() {
        // 프래그먼트 전환 코드
        val editFragment = MyListEditFragment() // 예시로 EditFragment로 전환
        childFragmentManager.beginTransaction()
            .replace(R.id.my_list_container, editFragment)
            .addToBackStack(null)
            .commit()
    }
}
interface OnEditButtonClickListener {
    fun onEditButtonClick()
}