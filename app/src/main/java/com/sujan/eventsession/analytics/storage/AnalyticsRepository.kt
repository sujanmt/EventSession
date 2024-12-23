package com.sujan.eventsession.analytics.storage

import com.google.gson.Gson
import com.sujan.eventsession.analytics.models.AnalyticsEvent
import com.sujan.eventsession.analytics.models.AnalyticsSession
import com.sujan.eventsession.analytics.storage.entities.EventEntity
import com.sujan.eventsession.analytics.storage.entities.SessionEntity
import com.sujan.eventsession.analytics.storage.entities.SessionWithEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnalyticsRepository(
    private val database: AnalyticsDatabase,
    private val gson: Gson = Gson()
) {
    private val dao = database.analyticsDao()

    suspend fun saveSession(session: AnalyticsSession) = withContext(Dispatchers.IO) {
        val sessionEntity = session.toSessionEntity()
        val eventEntities = session.events.map { it.toEventEntity(session.id) }
        
        dao.updateSession(SessionWithEvents(sessionEntity, eventEntities))
    }

    suspend fun getSession(sessionId: String): AnalyticsSession? = withContext(Dispatchers.IO) {
        dao.getSessionWithEvents(sessionId)?.toAnalyticsSession()
    }

    fun getAllSessions(): Flow<List<AnalyticsSession>> {
        return dao.getAllSessionsWithEvents()
            .map { sessions -> sessions.map { it.toAnalyticsSession() } }
    }

    suspend fun deleteSession(sessionId: String) = withContext(Dispatchers.IO) {
        dao.deleteSession(sessionId)
    }

    private fun AnalyticsSession.toSessionEntity() = SessionEntity(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime
    )

    private fun AnalyticsEvent.toEventEntity(sessionId: String) = EventEntity(
        id = id,
        sessionId = sessionId,
        name = name,
        properties = gson.toJson(properties),
        timestamp = timestamp
    )

    private fun SessionWithEvents.toAnalyticsSession() = AnalyticsSession(
        id = session.id,
        name = session.name,
        startTime = session.startTime,
        endTime = session.endTime,
        events = events.map { it.toAnalyticsEvent() }.toMutableList()
    )

    private fun EventEntity.toAnalyticsEvent() = AnalyticsEvent(
        id = id,
        name = name,
        properties = gson.fromJson(properties, Map::class.java) as Map<String, Any>,
        timestamp = timestamp
    )
} 