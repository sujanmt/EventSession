package com.sujan.eventsession.analytics.storage

import android.content.Context
import com.google.gson.Gson
import com.sujan.eventsession.analytics.models.AnalyticsSession
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RoomAnalyticsStorageTest {
    private val mockContext: Context = mockk()
    private val mockRepository: AnalyticsRepository = mockk()
    private lateinit var storage: RoomAnalyticsStorage

    @Before
    fun setup() {
        clearAllMocks()
        mockkObject(AnalyticsDatabase.Companion)
        every { AnalyticsDatabase.getInstance(any()) } returns mockk()
        
        storage = RoomAnalyticsStorage(mockContext, Gson())
        setField(storage, "repository", mockRepository)
    }

    @Test
    fun `saveSession should delegate to repository`() = runTest {
        val session = AnalyticsSession(name = "test")
        coEvery { mockRepository.saveSession(any()) } just Runs

        storage.saveSession(session)

        coVerify { mockRepository.saveSession(session) }
    }

    @Test
    fun `getAllSessions should return sessions from repository`() = runTest {
        val sessions = listOf(AnalyticsSession(name = "test"))
        every { mockRepository.getAllSessions() } returns flowOf(sessions)

        val result = storage.getAllSessions()

        assertEquals(sessions, result)
    }

    private fun setField(target: Any, fieldName: String, value: Any?) {
        val field = target.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(target, value)
    }
} 