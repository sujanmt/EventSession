package com.sujan.eventsession

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sujan.eventanalytics.AnalyticsSDK
import com.sujan.eventsession.analytics.storage.RoomAnalyticsStorage
import com.sujan.eventsession.ui.SessionAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var isSessionActive = false
    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var storage: RoomAnalyticsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SDK with Room storage implementation
        storage = RoomAnalyticsStorage(applicationContext, Gson())
        AnalyticsSDK.initialize(storage)

        setupRecyclerView()
        setupButtons()
        loadSessions()
    }

    private fun setupRecyclerView() {
        sessionAdapter = SessionAdapter()
        findViewById<RecyclerView>(R.id.rvSessions).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = sessionAdapter
        }
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnStartSession).setOnClickListener {
            if (!isSessionActive) {
                try {
                    AnalyticsSDK.startSession("test_session")
                    isSessionActive = true
                    showToast("Session started")
                    updateButtonStates()
                } catch (e: Exception) {
                    showToast("Error: ${e.message}")
                }
            }
        }

        findViewById<Button>(R.id.btnEndSession).setOnClickListener {
            if (isSessionActive) {
                try {
                    AnalyticsSDK.endSession()
                    isSessionActive = false
                    showToast("Session ended")
                    updateButtonStates()
                } catch (e: Exception) {
                    showToast("Error: ${e.message}")
                }
            }
        }

        findViewById<Button>(R.id.btnTrackEvent).setOnClickListener {
            if (isSessionActive) {
                try {
                    AnalyticsSDK.trackEvent(
                        "button_clicked",
                        mapOf(
                            "timestamp" to System.currentTimeMillis(),
                            "button_name" to "track_event",
                            "screen" to "main"
                        )
                    )
                    showToast("Event tracked")
                } catch (e: Exception) {
                    showToast("Error: ${e.message}")
                }
            } else {
                showToast("No active session")
            }
        }

        // Add refresh button handler
        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            loadSessions()
        }

        updateButtonStates()
    }

    private fun updateButtonStates() {
        findViewById<Button>(R.id.btnStartSession).isEnabled = !isSessionActive
        findViewById<Button>(R.id.btnEndSession).isEnabled = isSessionActive
        findViewById<Button>(R.id.btnTrackEvent).isEnabled = isSessionActive
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadSessions() {
        lifecycleScope.launch {
            try {
                val sessions = storage.getAllSessions()
                sessionAdapter.updateSessions(sessions)
            } catch (e: Exception) {
                showToast("Error loading sessions: ${e.message}")
            }
        }
    }
}