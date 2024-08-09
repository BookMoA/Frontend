package com.bookmoa.and.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.and.adapter.GroupRvFragmentAdapter
import com.bookmoa.and.adapter.GroupRvItems
import com.bookmoa.and.databinding.FragmentGroupvpBinding

class GroupRvFragment: Fragment() {
    private var _binding: FragmentGroupvpBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): GroupRvFragment {
            return GroupRvFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGroupvpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION) ?: 0
        val items = when (position) {
            0 -> listOf(GroupRvItems("대학생 책사모", "인원 1/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 2/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 3/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 4/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 5/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 6/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 7/20", "책읽기를 좋아하는 대학생 모여라!"))
            1 -> listOf(GroupRvItems("독서 모임 이름", "인원 15/20", "모임 한 줄 소개"),
                GroupRvItems("대학생 책사모", "인원 16/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 17/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 18/20", "책읽기를 좋아하는 대학생 모여라!"),
                GroupRvItems("대학생 책사모", "인원 19/20", "책읽기를 좋아하는 대학생 모여라!"))
            2 -> listOf(GroupRvItems("독서 모임 이름", "인원 20/20", "모임 한 줄 소개"))
            else -> {listOf(GroupRvItems("독서 모임 이름", "인원 16/20", "모임 한 줄 소개"))}
        }

        val adapter = GroupRvFragmentAdapter(items) { item ->
            val intent = Intent(context, DialogJoinActivity::class.java).apply {
                putExtra("TITLE", item.title)
                putExtra("COUNT", item.count)
                putExtra("DESCRIPTION", item.description)
            }
            startActivity(intent)
        }

        binding.groupRv.layoutManager = LinearLayoutManager(context)
        binding.groupRv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}