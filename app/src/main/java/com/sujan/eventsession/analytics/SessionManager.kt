package com.sujan.eventsession.analytics

interface SessionManager {
    fun startSession(sessionName: String)
    fun trackEvent(eventName: String, properties: Map<String, Any>)
    fun endSession()
} 