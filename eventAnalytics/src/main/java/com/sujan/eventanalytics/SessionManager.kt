package com.sujan.eventanalytics

interface SessionManager {
    fun startSession(sessionName: String)
    fun trackEvent(eventName: String, properties: Map<String, Any>)
    fun endSession()
} 