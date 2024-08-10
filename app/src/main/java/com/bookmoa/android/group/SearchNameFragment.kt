package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.SearchNameFragmentAdapter
import com.bookmoa.android.adapter.SearchNameItems
import com.bookmoa.android.databinding.FragmentSearchnamevpBinding

class SearchNameFragment : Fragment() {
    private var _binding: FragmentSearchnamevpBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameAdapter: SearchNameFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchnamevpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
    }

    private fun setupRecyclerView() {
        binding.searchnameRv.layoutManager = LinearLayoutManager(context)

        val nameItems = listOf(
            SearchNameItems("hi", "hi", "hi"),
            SearchNameItems("hi", "hi", "hi"),
            SearchNameItems("hi", "hi", "hi")
        )

        nameAdapter = SearchNameFragmentAdapter(nameItems)
        binding.searchnameRv.adapter = nameAdapter
    }

    private fun setupSpinner() {
        // Create an ArrayAdapter using the string array and a custom spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dropdown_items,
            R.layout.spinner_layout
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the spinner
            binding.searchnameSpinner.adapter = adapter
        }


        // Set a listener for item selection
        binding.searchnameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Handle the selected item
                // For example: Toast.makeText(this@MainActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}