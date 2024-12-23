package com.sujan.eventsession.analytics

import com.sujan.eventsession.analytics.models.AnalyticsSession

interface AnalyticsStorage {
    fun saveSession(session: AnalyticsSession)
    fun getSession(sessionId: String): AnalyticsSession?
    fun getAllSessions(): List<AnalyticsSession>
    fun deleteSession(sessionId: String)
} 