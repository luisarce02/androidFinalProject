package com.example.finalproject

import android.content.Context
import com.example.finalproject.models.UserDataStore

class SharedPreferencesUserDataStore(context: Context) : UserDataStore {

    companion object {
        private const val PREFS_NAME = "UserData"
        private const val KEY_USER_ID = "user_id"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveUserId(userId: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_USER_ID, userId)
            apply()
        }
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }
}