package com.sujan.eventsession.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sujan.eventsession.R
import com.sujan.eventsession.analytics.models.AnalyticsSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionAdapter : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {
    private var sessions: List<AnalyticsSession> = emptyList()

    fun updateSessions(newSessions: List<AnalyticsSession>) {
        sessions = newSessions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessions[position])
    }

    override fun getItemCount(): Int = sessions.size

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSessionName: TextView = itemView.findViewById(R.id.tvSessionName)
        private val tvSessionTime: TextView = itemView.findViewById(R.id.tvSessionTime)
        private val tvEventCount: TextView = itemView.findViewById(R.id.tvEventCount)
        private val rvEvents: RecyclerView = itemView.findViewById(R.id.rvEvents)
        private val eventAdapter = EventAdapter()
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        init {
            rvEvents.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = eventAdapter
            }

            itemView.setOnClickListener {
                if (rvEvents.visibility == View.VISIBLE) {
                    rvEvents.visibility = View.GONE
                } else {
                    rvEvents.visibility = View.VISIBLE
                }
            }
        }

        fun bind(session: AnalyticsSession) {
            tvSessionName.text = session.name
            tvSessionTime.text = "Started: ${dateFormat.format(Date(session.startTime))}"
            tvEventCount.text = "${session.events.size} events"
            eventAdapter.updateEvents(session.events)
        }
    }
} 