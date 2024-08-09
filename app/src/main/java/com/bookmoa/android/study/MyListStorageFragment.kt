package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentMyListStorageBinding


class MyListStorageFragment : Fragment() {
    lateinit var binding: FragmentMyListStorageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListStorageBinding.inflate(inflater, container, false)

        val childFragmentManager = childFragmentManager
        val transaction = childFragmentManager.beginTransaction()

        val childFragment = MyListFragment()
        transaction.replace(
            binding.childFragmentContainer.id,
            childFragment
        )
        transaction.commit()

        return binding.root
    }
}