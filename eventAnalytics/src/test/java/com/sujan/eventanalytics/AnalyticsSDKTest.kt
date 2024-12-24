package com.sujan.eventanalytics

import io.mockk.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class AnalyticsSDKTest {
    private val mockStorage: AnalyticsStorage = mockk(relaxed = true)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `initialize should set up SDK correctly`() {
        AnalyticsSDK.initialize(mockStorage)
        verify { mockStorage wasNot Called }
    }

    @Test
    fun `initialize should throw when called twice`() {
        AnalyticsSDK.initialize(mockStorage)

        assertFailsWith<IllegalStateException> {
            AnalyticsSDK.initialize(mockStorage)
        }
    }

    @Test
    fun `startSession should throw when SDK not initialized`() {
        assertFailsWith<IllegalStateException> {
            AnalyticsSDK.startSession("test")
        }
    }

    @Test
    fun `startSession should call storage correctly`() {
        AnalyticsSDK.initialize(mockStorage)
        AnalyticsSDK.startSession("test_session")

        verify { mockStorage.saveSession(any()) }
    }

    @Test
    fun `trackEvent should add event to current session`() {
        AnalyticsSDK.initialize(mockStorage)
        AnalyticsSDK.startSession("test_session")

        val properties = mapOf("key" to "value")
        AnalyticsSDK.trackEvent("test_event", properties)

        verify { mockStorage.saveSession(match {
            it.events.any { event ->
                event.name == "test_event" && event.properties == properties
            }
        })}
    }
} 