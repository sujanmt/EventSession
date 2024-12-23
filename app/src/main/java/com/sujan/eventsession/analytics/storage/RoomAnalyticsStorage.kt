package com.sujan.eventsession.analytics.storage

import android.content.Context
import com.google.gson.Gson
import com.sujan.eventsession.analytics.AnalyticsStorage
import com.sujan.eventsession.analytics.models.AnalyticsSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RoomAnalyticsStorage(
    context: Context,
    private val gson: Gson = Gson()
) : AnalyticsStorage {
    private val database = AnalyticsDatabase.getInstance(context)
    private val repository = AnalyticsRepository(database, gson)
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun saveSession(session: AnalyticsSession) {
        scope.launch {
            repository.saveSession(session)
        }
    }

    override fun getSession(sessionId: String): AnalyticsSession? = runBlocking {
        repository.getSession(sessionId)
    }

    override fun getAllSessions(): List<AnalyticsSession> = runBlocking {
        repository.getAllSessions().first()
    }

    override fun deleteSession(sessionId: String) {
        scope.launch {
            repository.deleteSession(sessionId)
        }
    }
} 