package com.bookmoa.android.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityRegisteredBookBinding

class RegisteredBookActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisteredBookBinding.inflate(layoutInflater)
    }

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        displayBookData()
    }

    private fun displayBookData() {
        val cursor = dbHelper.getBook()
        cursor?.use {
            if (it.moveToFirst()) {
                val coverColumnIndex = it.getColumnIndex(DBHelper.COLUMN_COVER)
                val titleColumnIndex = it.getColumnIndex(DBHelper.COLUMN_TITLE)
                val authorColumnIndex = it.getColumnIndex(DBHelper.COLUMN_AUTHOR)
                val publisherColumnIndex = it.getColumnIndex(DBHelper.COLUMN_PUBLISHER)
                val isbnColumnIndex = it.getColumnIndex(DBHelper.COLUMN_ISBN)
                val pageColumnIndex = it.getColumnIndex(DBHelper.COLUMN_PAGE)

                if (coverColumnIndex != -1 && titleColumnIndex != -1 && authorColumnIndex != -1 &&
                    publisherColumnIndex != -1 && isbnColumnIndex != -1 && pageColumnIndex != -1) {

                    val coverPath = it.getString(coverColumnIndex)
                    val bitmap = BitmapFactory.decodeFile(coverPath)
                    val rotatedBitmap = coverPath?.let { path -> rotateBitmapIfNeeded(bitmap, path) }

                    binding.imgNewBookCover.setImageBitmap(rotatedBitmap)
                    binding.etNewTitle.setText(it.getString(titleColumnIndex))
                    binding.etNewAuthour.setText(it.getString(authorColumnIndex))
                    binding.etNewPublisher.setText(it.getString(publisherColumnIndex))
                    binding.etNewIsbn.setText(it.getString(isbnColumnIndex))
                    binding.etNewPage.setText(it.getInt(pageColumnIndex).toString())
                } else {
                    binding.imgNewBookCover.setImageBitmap(null)
                    binding.etNewTitle.setText("Unknown")
                    binding.etNewAuthour.setText("Unknown")
                    binding.etNewPublisher.setText("Unknown")
                    binding.etNewIsbn.setText("Unknown")
                    binding.etNewPage.setText("0")
                }
            }
        }
    }

    private fun rotateBitmapIfNeeded(bitmap: Bitmap, photoPath: String): Bitmap {
        val exif = ExifInterface(photoPath)
        val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        return if (rotation != 0) {
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            bitmap
        }
    }
}