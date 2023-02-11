package com.example.repeate4mlesson1.utilits

import android.content.Context
import android.net.Uri

class Preferences(private val context: Context) {

    companion object {
        private const val HAVE_SEEN_BOARDING_KEY = "have_seen_onboarding"
        private const val PROFILE_TITLE_KEY = "save_text"
        private const val PROFILE_PICTURE_KEY = "save_picture"
    }

    private val prefs = context.getSharedPreferences(
        "utils",
        Context.MODE_PRIVATE

    )

    fun setHaveSeenOnBoarding() {
        prefs.edit().putBoolean(HAVE_SEEN_BOARDING_KEY, true).apply()
    }

    fun getHaveSeenOnBoarding(): Boolean {
        return prefs.getBoolean(HAVE_SEEN_BOARDING_KEY, false)
    }

    fun setPrefTitle(string: String) {
        prefs.edit().putString(PROFILE_TITLE_KEY, string).apply()
    }

    fun getPrefTitle() = prefs.getString(PROFILE_TITLE_KEY, "")

    fun savePrefImage(pictureUri: Uri) {
        prefs.edit().putString(PROFILE_PICTURE_KEY, pictureUri.toString()).apply()
    }

    fun getPrefImage(): Uri? {
        val uriString = prefs.getString(PROFILE_PICTURE_KEY, null)
        return if (uriString != null) Uri.parse(uriString) else null

    }


}