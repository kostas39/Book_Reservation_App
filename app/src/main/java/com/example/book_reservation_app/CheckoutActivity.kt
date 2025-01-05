package com.example.book_reservation_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Get reserved books from the intent
        @Suppress("UNCHECKED_CAST")
        val reservedBooks = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("reservedBooks", ArrayList::class.java) as? ArrayList<Pair<Book, Pair<Long, Long>>>
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("reservedBooks") as? ArrayList<Pair<Book, Pair<Long, Long>>>
        }

        // Format reserved books details
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val reservationsTextView = findViewById<TextView>(R.id.reservationsTextView)

        val reservedDetails = reservedBooks?.joinToString("\n\n") { (book, dates) ->
            val startDate = dateFormatter.format(Date(dates.first))
            val endDate = dateFormatter.format(Date(dates.second))
            """
            Book Name: ${book.bookName}
            Book Authors: ${book.bookAuthors}
            Book reserved from $startDate to $endDate
            """.trimIndent()
        } ?: "No reservations made."

        reservationsTextView.text = reservedDetails

        // Go back button
        findViewById<Button>(R.id.goBackButton).setOnClickListener {
            finish() // Return to the previous activity
        }

        // Close app button
        findViewById<Button>(R.id.closeAppButton).setOnClickListener {
            finishAffinity() // Close the app completely
        }
    }
}
