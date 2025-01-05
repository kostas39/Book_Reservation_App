package com.example.book_reservation_app

import BookAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val reservedBooks = ArrayList<Pair<Book, Pair<Long, Long>>>()
    private lateinit var books: MutableList<Book>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the book list
        books = mutableListOf(
            Book(
                bookName = "Wheel of Time",
                bookAuthors = "Robert Jordan",
                bookPublisher = "Tor Books (Macmillan)",
                bookImage = "https://images.macmillan.com/folio-assets/macmillan_us_frontbookcovers_1000H/9780765376862.jpg"
            ),
            Book(
                bookName = "Lord of the Rings",
                bookAuthors = "J. R. R. Tolkien",
                bookPublisher = "Allen & Unwin",
                bookImage = "https://www.bibdsl.co.uk/imagegallery/bookdata/cd427/9780261103252.JPG"
            ),
            Book(
                bookName = "Harry Potter Series",
                bookAuthors = "J. K. Rowling",
                bookPublisher = "Bloomsbury",
                bookImage = "https://i2.wp.com/geekdad.com/wp-content/uploads/2013/02/HP1-Kibuishi.jpg"
            ),
            Book(
                bookName = "A song of Ice and Fire",
                bookAuthors = "George R. R. Martin",
                bookPublisher = "Bantam Books",
                bookImage = "https://upload.wikimedia.org/wikipedia/en/d/dc/A_Song_of_Ice_and_Fire_book_collection_box_set_cover.jpg"
            )
        )

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up adapter
        adapter = BookAdapter(books) { book, isReserved ->
            if (isReserved) {
                // Show date range picker to reserve the book
                showDateRangePicker(book)
            } else {
                // Remove the reservation
                removeReservation(book)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun showDateRangePicker(book: Book) {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select a date range")
            .build()

        dateRangePicker.show(supportFragmentManager, "date_range_picker")

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection.first
            val endDate = selection.second

            if (startDate != null && endDate != null) {
                reservedBooks.add(Pair(book, Pair(startDate, endDate)))
                Log.d("DatePicker", "Start Date: $startDate, End Date: $endDate")
                showReservationDialog()
            } else {
                Toast.makeText(this, "Please select valid dates", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeReservation(book: Book) {
        val removed = reservedBooks.removeIf { it.first == book }
        if (removed) {
            Toast.makeText(this, "${book.bookName} reservation removed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showReservationDialog() {
        val builder = AlertDialog.Builder(this)

        // Define a date formatter
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Format reserved details
        val reservedDetails = reservedBooks.joinToString("\n\n") { (book, dates) ->
            val startDate = dateFormatter.format(Date(dates.first))
            val endDate = dateFormatter.format(Date(dates.second))
            """
            Book Name: ${book.bookName}
            Book Authors: ${book.bookAuthors}
            Book reserved from $startDate to $endDate
            """.trimIndent()
        }

        // Build the dialog
        builder.setMessage("My Book Reservations\n\n$reservedDetails\n\nDo you want to continue browsing?")
        builder.setPositiveButton("Go to Checkout") { _, _ ->
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("reservedBooks", reservedBooks) // Pass the reserved books list
            startActivity(intent)
        }
        builder.setNegativeButton("Stay on the Same Page") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
