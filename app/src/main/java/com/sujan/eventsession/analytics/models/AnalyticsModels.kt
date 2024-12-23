package com.sujan.eventsession.analytics.models

import java.util.UUID

data class AnalyticsEvent(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val properties: Map<String, Any>,
    val timestamp: Long = System.currentTimeMillis()
)

data class AnalyticsSession(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startTime: Long = System.currentTimeMillis(),
    var endTime: Long? = null,
    val events: MutableList<AnalyticsEvent> = mutableListOf()
) 