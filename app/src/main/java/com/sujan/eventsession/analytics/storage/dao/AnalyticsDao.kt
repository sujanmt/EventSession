package com.sujan.eventsession.analytics.storage.dao

import androidx.room.*
import com.sujan.eventsession.analytics.storage.entities.EventEntity
import com.sujan.eventsession.analytics.storage.entities.SessionEntity
import com.sujan.eventsession.analytics.storage.entities.SessionWithEvents
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsDao {
    @Transaction
    @Query("SELECT * FROM sessions WHERE id = :sessionId")
    suspend fun getSessionWithEvents(sessionId: String): SessionWithEvents?

    @Transaction
    @Query("SELECT * FROM sessions")
    fun getAllSessionsWithEvents(): Flow<List<SessionWithEvents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("DELETE FROM sessions WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: String)

    @Transaction
    suspend fun updateSession(sessionWithEvents: SessionWithEvents) {
        insertSession(sessionWithEvents.session)
        sessionWithEvents.events.forEach { event ->
            insertEvent(event)
        }
    }
} 