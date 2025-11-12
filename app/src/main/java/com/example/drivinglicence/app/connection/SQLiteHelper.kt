package com.example.drivinglicence.app.connection

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.drivinglicence.app.entity.Question

class SQLiteHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "GEEKS_FOR_GEEKS"
        private const val DATABASE_VERSION = 1

        //Table action
        const val TABLE_NAME_ACTION = "action"
        const val ACTION_ID = "actionId"
        const val ACTION_NAME = "actionName"
        const val ACTION_IMAGE = "actionImage"

        //Table roadTrafficSign
        const val TABLE_NAME_ROAD_TRAFFIC_SIGN = "roadTrafficSign"
        const val ROAD_ID = "roadTrafficSignId"
        const val ROAD_NAME = "roadTrafficSignName"
        const val ROAD_DESCRIPTION = "roadTrafficSignDescription"
        const val ROAD_IMAGE = "roadTrafficSignImage"

        //Table tips
        const val TABLE_NAME_TIP = "tips"
        const val TIP_ID = "tipId"
        const val TIP_NAME = "tipName"
        const val TIP_IMAGE = "tipImage"

        //Table descriptionLicense
        const val TABLE_NAME_DESCRIPTION_LICENSE = "descriptionLicense"
        const val DESCRIPTION_LICENSE_ID = "licenseId"
        const val DESCRIPTION_LICENSE_INFORMATION = "licenseInformation"

        //Table trafficLaw
        const val TABLE_NAME_TRAFFIC_LAW = "trafficLaw"
        const val TRAFFIC_LAW_ID = "trafficLawId"
        const val TRAFFIC_LAW_URL = "trafficLawUrl"

        //Table exam
        const val TABLE_NAME_EXAMS = "exams"
        const val EXAM_ID = "exam_id"
        const val EXAM_NAME = "name"
        const val EXAM_TYPE = "type"

        //Table question
        const val TABLE_NAME_QUESTION = "question"
        const val QUESTION_ID = "question_id"
        const val CONTENT = "content"
        const val ISIMPORTANT = "isImportant"
        const val IMAGE = "image"

        //Table answer
        const val TABLE_NAME_ANSWER = "answer"
        const val ANSWER_ID = "answerId"
        const val IS_CORRECT = "isCorrect"
        const val EXPLAIN_ANSWER = "explain_answer"

        //Table submitAnswer
        const val TABLE_NAME_SUBMITANSWER = "submitanswer"
        const val SUBMITANSWER_ID = "submitanswerId"

        //Table Result
        const val TABLE_NAME_RESULT = "result"
        const val RESULT_ID = "resultId"
        const val COUNT_CORRECT = "countCorrect"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val queryAction = ("CREATE TABLE " + TABLE_NAME_ACTION + " ("
                + ACTION_ID + " INTEGER PRIMARY KEY, " +
                ACTION_NAME + " TEXT," +
                ACTION_IMAGE + " TEXT" + ")")

        val queryRoadTrafficSign = ("CREATE TABLE " + TABLE_NAME_ROAD_TRAFFIC_SIGN + " ("
                + ROAD_ID + " INTEGER PRIMARY KEY, " +
                ROAD_NAME + " TEXT," +
                ROAD_DESCRIPTION + " TEXT," +
                ROAD_IMAGE + " TEXT" + ")")

        val queryTip = ("CREATE TABLE " + TABLE_NAME_TIP + " ("
                + TIP_ID + " INTEGER PRIMARY KEY, " +
                TIP_NAME + " TEXT," +
                TIP_IMAGE + " TEXT" + ")")

        val queryTrafficLaw = ("CREATE TABLE " + TABLE_NAME_TRAFFIC_LAW + " ("
                + TRAFFIC_LAW_ID + " INTEGER PRIMARY KEY, " +
                TRAFFIC_LAW_URL + " TEXT" + ")")

        db.execSQL(queryAction)
        db.execSQL(queryRoadTrafficSign)
        db.execSQL(queryTip)
        db.execSQL(queryTrafficLaw)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACTION)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROAD_TRAFFIC_SIGN)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TIP)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRAFFIC_LAW)
        onCreate(db)
    }

    fun addAction(name: String, image: String) {
        val values = ContentValues()
        values.put(ACTION_NAME, name)
        values.put(ACTION_IMAGE, image)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_ACTION, null, values)
        db.close()
    }

    fun getAction(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_ACTION, null)
    }

    fun addRoad(name: String, description: String, image: String) {
        val values = ContentValues()
        values.put(ROAD_NAME, name)
        values.put(ROAD_DESCRIPTION, description)
        values.put(ROAD_IMAGE, image)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_ROAD_TRAFFIC_SIGN, null, values)
        db.close()
    }

    fun getRoad(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_ROAD_TRAFFIC_SIGN, null)
    }

    fun getTip(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_TIP, null)
    }

    fun getTrafficLaw(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_TRAFFIC_LAW, null)
    }

    fun getDescriptionLicense(): Cursor? {
        val db = this.readableDatabase
        val check = insertSubmitAnswer(1, 1, 1)
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_DESCRIPTION_LICENSE, null)
    }

    private fun insertSubmitAnswer(resultId: Int, questionId: Int, answerId: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RESULT_ID, resultId)
            put(QUESTION_ID, questionId)
            put(ANSWER_ID, answerId)
            put(CONTENT, 0)
            put(ISIMPORTANT, 0)
            put(IMAGE, 0)
            put(IS_CORRECT, 0)
            put(EXPLAIN_ANSWER, 0)
            put(EXAM_NAME, 0)
            put(EXAM_TYPE, 0)
            put(COUNT_CORRECT, 0)
        }
        getResult()
        val newRowId = db?.insert(TABLE_NAME_SUBMITANSWER, null, values)
        return newRowId!! > 0
    }

    fun getResult(): Cursor? {
        val db = this.readableDatabase
        val check = insertSubmitAnswer(1, 1, 1)
        return db.rawQuery(
            "SELECT * FROM " + TABLE_NAME_RESULT + "INNER JOIN " + TABLE_NAME_EXAMS
                    + "INNER JOIN " + TABLE_NAME_QUESTION
                    + "INNER JOIN " + TABLE_NAME_EXAMS
                    + "INNER JOIN " + TABLE_NAME_ANSWER, null
        )
    }

}