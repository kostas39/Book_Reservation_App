package com.example.book_reservation_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReservationSummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_summary)

        // Get the reservations from the intent
        val reservations = intent.getStringArrayListExtra("reservations")

        // Find the TextView and display reservations
        val reservationsTextView = findViewById<TextView>(R.id.reservationsTextView)
        val summary = reservations?.joinToString(separator = "\n\n") ?: "No reservations made."
        reservationsTextView.text = summary

        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            finish() // Finish this activity to return to the previous one
        }
    }
}
