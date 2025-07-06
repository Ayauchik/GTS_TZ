package kz.petproject.gts_tz.data.local

import android.content.SharedPreferences

class TokenManager(private val prefs: SharedPreferences) {

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit().remove(KEY_AUTH_TOKEN).apply()
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
}