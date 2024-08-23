package com.bookmoa.android.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentMemberInfoBinding

class MemberInfoFragment : Fragment() {

    lateinit var binding: FragmentMemberInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberInfoBinding.inflate(inflater, container, false)

        return binding.root
    }


}