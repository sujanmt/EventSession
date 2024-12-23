package com.sujan.eventsession.analytics

object AnalyticsSDK {
    private var isInitialized = false
    private lateinit var sessionManager: SessionManager
    private lateinit var storage: AnalyticsStorage

    fun initialize(storage: AnalyticsStorage) {
        if (isInitialized) {
            throw IllegalStateException("AnalyticsSDK is already initialized")
        }
        this.storage = storage
        this.sessionManager = SessionManagerImpl(storage)
        isInitialized = true
    }

    fun startSession(sessionName: String) {
        checkInitialization()
        sessionManager.startSession(sessionName)
    }

    fun trackEvent(eventName: String, properties: Map<String, Any> = emptyMap()) {
        checkInitialization()
        sessionManager.trackEvent(eventName, properties)
    }

    fun endSession() {
        checkInitialization()
        sessionManager.endSession()
    }

    private fun checkInitialization() {
        if (!isInitialized) {
            throw IllegalStateException("AnalyticsSDK is not initialized")
        }
    }
} 