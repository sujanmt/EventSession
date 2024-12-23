package com.sujan.eventsession.analytics.storage

import app.cash.turbine.test
import com.google.gson.Gson
import com.sujan.eventsession.analytics.models.AnalyticsEvent
import com.sujan.eventsession.analytics.models.AnalyticsSession
import com.sujan.eventsession.analytics.storage.dao.AnalyticsDao
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AnalyticsRepositoryTest {
    private val mockDao: AnalyticsDao = mockk()
    private val mockDatabase: AnalyticsDatabase = mockk {
        every { analyticsDao() } returns mockDao
    }
    private lateinit var repository: AnalyticsRepository

    @Before
    fun setup() {
        clearAllMocks()
        repository = AnalyticsRepository(mockDatabase, Gson())
    }

    @Test
    fun `saveSession should convert and save session correctly`() = runTest {
        val session = AnalyticsSession(
            id = "test_id",
            name = "test_session",
            startTime = 1000L,
            events = mutableListOf(
                AnalyticsEvent(
                    name = "test_event",
                    properties = mapOf("key" to "value"),
                    timestamp = 2000L
                )
            )
        )

        coEvery { mockDao.updateSession(any()) } just Runs

        repository.saveSession(session)

        coVerify { mockDao.updateSession(match { 
            it.session.id == session.id && 
            it.session.name == session.name &&
            it.events.size == session.events.size
        })}
    }

    @Test
    fun `getAllSessions should return mapped sessions`() = runTest {
        val sessionWithEvents = createMockSessionWithEvents()
        every { mockDao.getAllSessionsWithEvents() } returns flowOf(listOf(sessionWithEvents))

        repository.getAllSessions().test {
            val sessions = awaitItem()
            assertEquals(1, sessions.size)
            assertEquals(sessionWithEvents.session.id, sessions[0].id)
            assertEquals(sessionWithEvents.session.name, sessions[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createMockSessionWithEvents() = SessionWithEvents(
        session = SessionEntity(
            id = "test_id",
            name = "test_session",
            startTime = 1000L,
            endTime = null
        ),
        events = listOf(
            EventEntity(
                id = "event_id",
                sessionId = "test_id",
                name = "test_event",
                properties = """{"key":"value"}""",
                timestamp = 2000L
            )
        )
    )
} 