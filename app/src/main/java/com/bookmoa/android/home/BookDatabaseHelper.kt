package com.bookmoa.android.home

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log

class BookDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_STATUS TEXT, " +
                    "$COLUMN_PAGE INTEGER, " +
                    "$COLUMN_START_DATE TEXT, " +
                    "$COLUMN_END_DATE TEXT" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 페이지를 삽입하는 함수
    fun insertPage(page: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAGE, page)
        }
        db.insert(TABLE_NAME, null, values)
    }

    // 상태를 삽입하는 함수
    fun insertStatus(status: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STATUS, status)
        }
        val result = db.insert(TABLE_NAME, null, values)
        if (result == -1L) {
            Log.e("BookDatabaseHelper", "Failed to insert status: $status")
        } else {
            Log.d("BookDatabaseHelper", "Insert status result: $status, ID: $result")
        }
    }

    // 시작 날짜 삽입하는 함수
    fun insertStartDate(start_date: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_START_DATE, start_date)
        }
        val result = db.insert(TABLE_NAME, null, values)
    }

    // 완료 날짜 삽입하는 함수
    fun insertEndDate(end_date: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_END_DATE, end_date)
        }
        val result = db.insert(TABLE_NAME, null, values)
    }

    // 마지막으로 저장된 상태를 가져오는 함수
    fun getLastStatus(): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_STATUS),
            "$COLUMN_STATUS IS NOT NULL", // 추가: NULL 상태값이 없는 데이터만 가져오도록 필터링
            null, null, null,
            "$COLUMN_ID DESC",
            "1"
        )
        with(cursor) {
            if (moveToFirst()) {
                val status = getString(getColumnIndexOrThrow(COLUMN_STATUS))
                Log.d("BookDatabaseHelper", "Last status: $status")
                return status
            }
        }
        Log.d("BookDatabaseHelper", "Last status: null")
        return null
    }

    // 마지막으로 입력된 페이지를 가져오는 함수
    fun getLastPage(): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_PAGE),
            null, null, null, null,
            "$COLUMN_ID DESC",
            "1"
        )
        with(cursor) {
            if (moveToFirst()) {
                return getInt(getColumnIndexOrThrow(COLUMN_PAGE))
            }
        }
        return 0
    }

    // 마지막으로 저장된 시작 날짜 가져오는 함수
    fun getLastStartDate(): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_START_DATE),
            "$COLUMN_START_DATE IS NOT NULL", // 추가: NULL 상태값이 없는 데이터만 가져오도록 필터링
            null, null, null,
            "$COLUMN_ID DESC",
            "1"
        )
        with(cursor) {
            if (moveToFirst()) {
                val start_date = getString(getColumnIndexOrThrow(COLUMN_START_DATE))
                Log.d("BookDatabaseHelper", "Last start date: $start_date")
                return start_date
            }
        }
        Log.d("BookDatabaseHelper", "Last start date: null")
        return null
    }

    // 마지막으로 저장된 완료 날짜 가져오는 함수
    fun getLastEndDate(): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_END_DATE),
            "$COLUMN_END_DATE IS NOT NULL", // 추가: NULL 상태값이 없는 데이터만 가져오도록 필터링
            null, null, null,
            "$COLUMN_ID DESC",
            "1"
        )
        with(cursor) {
            if (moveToFirst()) {
                val end_date = getString(getColumnIndexOrThrow(COLUMN_END_DATE))
                Log.d("BookDatabaseHelper", "Last start date: $end_date")
                return end_date
            }
        }
        Log.d("BookDatabaseHelper", "Last start date: null")
        return null
    }


    companion object {
        private const val DATABASE_NAME = "books.db"
        private const val DATABASE_VERSION = 13
        const val TABLE_NAME = "BookStatus"
        const val COLUMN_ID = "id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_PAGE = "page"
        const val COLUMN_START_DATE = "start_date"
        const val COLUMN_END_DATE = "end_date"
    }
}