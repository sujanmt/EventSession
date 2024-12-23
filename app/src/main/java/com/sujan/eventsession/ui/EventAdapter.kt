package com.sujan.eventsession.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sujan.eventsession.R
import com.sujan.eventsession.analytics.models.AnalyticsEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var events: List<AnalyticsEvent> = emptyList()

    fun updateEvents(newEvents: List<AnalyticsEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEventName: TextView = itemView.findViewById(R.id.tvEventName)
        private val tvEventTime: TextView = itemView.findViewById(R.id.tvEventTime)
        private val tvEventProperties: TextView = itemView.findViewById(R.id.tvEventProperties)
        private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        fun bind(event: AnalyticsEvent) {
            tvEventName.text = event.name
            tvEventTime.text = dateFormat.format(Date(event.timestamp))
            tvEventProperties.text = event.properties.toString()
        }
    }
} 