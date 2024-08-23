package com.bookmoa.android.study

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentListDetailAddBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class ListDetailAddFragment : Fragment() {
    private lateinit var binding: FragmentListDetailAddBinding
    private var selectedImageUri: Uri? = null

    // Register the image picker launcher
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            // Display the selected image

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailAddBinding.inflate(inflater, container, false)

        binding.listDetailAddBackIcon.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.listDetailAddImgCarview.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }


        binding.listDetailAddAddBookIc.setOnClickListener {
            // Handle book addition logic here
        }

        return binding.root
    }

    private fun uriToFile(uri: Uri): File {
        val fileName = getFileName(uri)
        val file = File(requireContext().cacheDir, fileName)
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    private fun getFileName(uri: Uri): String {
        var name = "temp_file"
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }
}