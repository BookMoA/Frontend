package com.bookmoa.android.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bookmoa.android.databinding.ActivityRegisterbookBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class RegisterbookActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityRegisterbookBinding.inflate(layoutInflater)
    }

    private lateinit var dbHelper: DBHelper
    val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String? = null
    var currentPhotoFileName: String? = null  // 현재 이미지 파일명 저장


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.imgBookCover.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.btnSave.setOnClickListener {
            saveBookData()
        }
    }

    private fun dispatchTakePictureIntent() {   // 원본 사진 요청
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(packageManager) != null){
            val photoFile: File? = try{
                createImageFile()
            } catch (e: IOException){
                e.printStackTrace()
                null
            }
            if (photoFile != null){
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.umc6.bookmoa.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == RESULT_OK) {
                    setPic()
                }
            }
        }
    }

    private fun saveBookData() {
        val title = binding.etTitle.text.toString()
        val author = binding.etAuthour.text.toString()
        val publisher = binding.etPublisher.text.toString()
        val isbn = binding.etIsbn.text.toString()
        val page = binding.etPage.text.toString().toIntOrNull() ?: 0
        val coverPath = currentPhotoPath

        if (title.isNotBlank() && author.isNotBlank() && publisher.isNotBlank() && isbn.isNotBlank() && coverPath != null) {
            dbHelper.addBook(title, author, publisher, isbn, page, coverPath)
            val intent = Intent(this, RegisteredBookActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please fill in all fields and take a cover photo.", Toast.LENGTH_SHORT).show()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val file = File ("${storageDir?.path}/${timeStamp}.jpg")

        currentPhotoFileName = file.name
        currentPhotoPath = file.absolutePath
        return file
    }

    private fun setPic() { //이제 사진 찍은거 화면에 보여주는 함수

        val targetW: Int = binding.imgBookCover.width
        val targetH: Int = binding.imgBookCover.height

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            val rotatedBitmap = currentPhotoPath?.let { rotateBitmapIfNeeded(bitmap, it) }
            binding.imgBookCover.setImageBitmap(rotatedBitmap)
        }
    }

    // 사진 안돌아가게 하는 함수
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