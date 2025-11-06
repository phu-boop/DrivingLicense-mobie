package com.example.drivinglicence.app.viewmodel

import android.content.Context
import com.example.drivinglicence.app.connection.SQLiteHelper
import com.example.drivinglicence.app.entity.ItemAction
import com.example.drivinglicence.app.entity.ItemRoadTraffic
import com.example.drivinglicence.component.viewmodel.BaseViewModel

class DataViewModel : BaseViewModel() {

    private fun getAllAction(context: Context): List<ItemAction> {
        val db = SQLiteHelper(context, null)
        val cursor = db.getAction()
        cursor!!.moveToFirst()
        var actionName: String
        var actionImage : String
        val list = mutableListOf<ItemAction>()
        while (cursor.moveToNext()) {
            actionName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.ACTION_NAME) ?: 0)
            actionImage = cursor.getString(cursor.getColumnIndex(SQLiteHelper.ACTION_IMAGE) ?: 0)
            list.add(ItemAction(actionName, actionImage.toInt()))
        }
        cursor.close()
        return list
    }

    private fun getAllRoadTrafficSign(context: Context): List<ItemRoadTraffic> {
        val db = SQLiteHelper(context, null)
        val cursor = db.getRoad()
        cursor!!.moveToFirst()
        var roadName: String
        var roadDescription: String
        var roadImage: String
        val list = mutableListOf<ItemRoadTraffic>()
        while (cursor.moveToNext()) {
            roadName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.ROAD_NAME) ?: 0)
            roadDescription =
                cursor.getString(cursor.getColumnIndex(SQLiteHelper.ROAD_DESCRIPTION) ?: 0)
            roadImage = cursor.getString(cursor.getColumnIndex(SQLiteHelper.ROAD_IMAGE) ?: 0)
            list.add(ItemRoadTraffic(roadName, roadDescription, roadImage.toInt()))
        }
        cursor.close()
        return list
    }

    private fun getAllTip(context: Context): List<ItemAction> {
        val db = SQLiteHelper(context, null)
        val cursor = db.getTip()
        cursor!!.moveToFirst()
        var tipName : String
        var tipContent : String
        val list = mutableListOf<ItemAction>()
        while (cursor.moveToNext()) {
            tipName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.TIP_NAME) ?: 0)
            tipContent = cursor.getString(cursor.getColumnIndex(SQLiteHelper.TIP_IMAGE) ?: 0)
            list.add(ItemAction(tipName, tipContent.toInt()))
        }
        cursor.close()
        return list
    }

    private fun getAllTrafficLaw(context: Context): List<ItemAction> {
        val db = SQLiteHelper(context, null)
        val cursor = db.getTrafficLaw()
        cursor!!.moveToFirst()
        var trafficLawUrl : String
        val list = mutableListOf<ItemAction>()
        while (cursor.moveToNext()) {
            trafficLawUrl =
                cursor.getString(cursor.getColumnIndex(SQLiteHelper.TRAFFIC_LAW_URL) ?: 0)
            list.add(ItemAction(trafficLawUrl, 0))
        }
        cursor.close()
        return list
    }

    private fun getAllDescriptionLicense(context: Context): List<ItemAction> {
        val db = SQLiteHelper(context, null)
        val cursor = db.getDescriptionLicense()
        cursor!!.moveToFirst()
        var licenseInformation : String
        val list = mutableListOf<ItemAction>()
        while (cursor.moveToNext()) {
            licenseInformation =
                cursor.getString(
                    cursor.getColumnIndex(SQLiteHelper.DESCRIPTION_LICENSE_INFORMATION) ?: 0
                )
            list.add(ItemAction(licenseInformation, 0))
        }
        cursor.close()
        return list
    }

    fun getAllData(context: Context) {
        getAllAction(context)
        getAllRoadTrafficSign(context)
        getAllTip(context)
        getAllTrafficLaw(context)
        getAllDescriptionLicense(context)
    }
}