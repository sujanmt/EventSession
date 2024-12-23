package com.sujan.eventsession.analytics.storage.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startTime: Long,
    val endTime: Long?
)

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sessionId")]
)
data class EventEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val sessionId: String,
    val name: String,
    val properties: String, // JSON string
    val timestamp: Long
)

data class SessionWithEvents(
    @Embedded
    val session: SessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    )
    val events: List<EventEntity>
) 