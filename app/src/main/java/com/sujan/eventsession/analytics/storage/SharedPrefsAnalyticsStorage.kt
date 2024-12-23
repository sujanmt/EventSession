package com.sujan.eventsession.analytics.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sujan.eventsession.analytics.AnalyticsStorage
import com.sujan.eventsession.analytics.models.AnalyticsSession

class SharedPrefsAnalyticsStorage(context: Context) : AnalyticsStorage {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveSession(session: AnalyticsSession) {
        val sessions = getAllSessions().toMutableList()
        val existingIndex = sessions.indexOfFirst { it.id == session.id }
        
        if (existingIndex != -1) {
            sessions[existingIndex] = session
        } else {
            sessions.add(session)
        }
        
        prefs.edit().putString(KEY_SESSIONS, gson.toJson(sessions)).apply()
    }

    override fun getSession(sessionId: String): AnalyticsSession? {
        return getAllSessions().find { it.id == sessionId }
    }

    override fun getAllSessions(): List<AnalyticsSession> {
        val sessionsJson = prefs.getString(KEY_SESSIONS, "[]")
        val type = object : TypeToken<List<AnalyticsSession>>() {}.type
        return gson.fromJson(sessionsJson, type) ?: emptyList()
    }

    override fun deleteSession(sessionId: String) {
        val sessions = getAllSessions().filterNot { it.id == sessionId }
        prefs.edit().putString(KEY_SESSIONS, gson.toJson(sessions)).apply()
    }

    companion object {
        private const val PREFS_NAME = "analytics_prefs"
        private const val KEY_SESSIONS = "sessions"
    }
} 