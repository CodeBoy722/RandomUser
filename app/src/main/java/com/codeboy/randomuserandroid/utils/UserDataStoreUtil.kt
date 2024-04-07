package com.codeboy.randomuserandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.codeboy.randomuserandroid.domain.models.PagingData
import com.codeboy.randomuserandroid.domain.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserDataStoreUtil(val context: Context) {

    private val PREFS_FILENAME = "com.codeboy.randomuserandroid.userPrefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    private val LAST_USERLIST = "Userlist"
    private var lastUserlist :String?
        get() = prefs.getString(LAST_USERLIST, "")
        set(value) = prefs.edit().putString(LAST_USERLIST, value).apply()

    fun getLastUserList(): List<User>{
        var serializedUserlist: ArrayList<User> = ArrayList()
        val gson = Gson()
        val json: String = lastUserlist ?: ""
        gson.fromJson<ArrayList<User>>(json, object : TypeToken<ArrayList<User>>(){}.type)
            .also { serializedUserlist = it?: ArrayList() }
        return serializedUserlist
    }

    fun saveLastUserList(userList: List<User>?){
        val gson = Gson()
        val userListJson: String = gson.toJson(userList)
        lastUserlist = userListJson
    }

    // paging data ---------------------------------------------------------------------------------


    private val LAST_PAGING_DATA = "LastPagingData"

    private var lastPagingData :String?
        get() = prefs.getString(LAST_PAGING_DATA, "")
        set(value) = prefs.edit().putString(LAST_PAGING_DATA, value).apply()

    fun getLastPagingData(): PagingData{
        var serializedPaging: PagingData = PagingData()
        val gson = Gson()
        val json: String = lastPagingData ?: ""
        gson.fromJson<PagingData>(json, object : TypeToken<PagingData>(){}.type)
            .also { serializedPaging = it?: PagingData() }
        return serializedPaging
    }

    fun saveLastPaging(pagingData: PagingData){
        val gson = Gson()
        val pagingJson: String = gson.toJson(pagingData)
        lastPagingData = pagingJson
    }

}