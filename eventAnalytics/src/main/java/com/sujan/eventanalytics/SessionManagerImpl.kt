package com.sujan.eventanalytics

class SessionManagerImpl(private val storage: AnalyticsStorage) :
    SessionManager {
    private var currentSession: com.sujan.eventanalytics.models.AnalyticsSession? = null

    override fun startSession(sessionName: String) {
        if (currentSession != null) {
            throw IllegalStateException("A session is already in progress")
        }
        currentSession = com.sujan.eventanalytics.models.AnalyticsSession(name = sessionName)
        storage.saveSession(currentSession!!)
    }

    override fun trackEvent(eventName: String, properties: Map<String, Any>) {
        val session = currentSession ?: throw IllegalStateException("No active session")
        val event = com.sujan.eventanalytics.models.AnalyticsEvent(
            name = eventName,
            properties = properties
        )
        session.events.add(event)
        storage.saveSession(session)
    }

    override fun endSession() {
        val session = currentSession ?: throw IllegalStateException("No active session")
        session.endTime = System.currentTimeMillis()
        storage.saveSession(session)
        currentSession = null
    }
} 