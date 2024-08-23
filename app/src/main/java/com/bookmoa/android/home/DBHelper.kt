package com.bookmoa.android.home

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "books.db"
        private const val TABLE_BOOKS = "books"
        private const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_PUBLISHER = "publisher"
        const val COLUMN_ISBN = "isbn"
        const val COLUMN_PAGE = "page"
        const val COLUMN_COVER = "cover"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_BOOKS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TITLE TEXT, "
                + "$COLUMN_AUTHOR TEXT, "
                + "$COLUMN_PUBLISHER TEXT, "
                + "$COLUMN_ISBN TEXT, "
                + "$COLUMN_PAGE INTEGER, "
                + "$COLUMN_COVER TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    fun addBook(title: String, author: String, publisher: String, isbn: String, page: Int, coverPath: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_PUBLISHER, publisher)
            put(COLUMN_ISBN, isbn)
            put(COLUMN_PAGE, page)
            put(COLUMN_COVER, coverPath)
        }
        try {
            db.insert(TABLE_BOOKS, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun getBook(): Cursor? {
        val db = this.readableDatabase
        return try {
            db.query(TABLE_BOOKS, null, null, null, null, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}