package com.sujan.eventanalytics

import com.sujan.eventanalytics.models.AnalyticsSession


interface AnalyticsStorage {
    fun saveSession(session: AnalyticsSession)
    fun getSession(sessionId: String): AnalyticsSession?
    fun getAllSessions(): List<AnalyticsSession>
    fun deleteSession(sessionId: String)
} 