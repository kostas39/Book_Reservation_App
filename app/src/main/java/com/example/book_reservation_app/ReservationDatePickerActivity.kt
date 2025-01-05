import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.book_reservation_app.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ReservationDatePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)

        // Initialize the Save button
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Create the Date Range Picker
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select a date range")
            .build()

        // Show the Date Range Picker when the Activity starts
        dateRangePicker.show(supportFragmentManager, "date_range_picker")

        // Listen for date selection
        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            // Extract start and end dates
            val startDate = selection.first
            val endDate = selection.second

            if (startDate != null && endDate != null) {
                // Format the dates
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedStartDate = dateFormat.format(Date(startDate))
                val formattedEndDate = dateFormat.format(Date(endDate))

                Log.d("DatePicker", "Start Date: $formattedStartDate, End Date: $formattedEndDate")

                // Pass the selected dates back to MainActivity
                val resultIntent = intent
                resultIntent.putExtra("startDate", startDate)
                resultIntent.putExtra("endDate", endDate)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // Handle Save button click (optional if you use the Date Picker confirmation)
        saveButton.setOnClickListener {
            // Close the activity if no action needed
            finish()
        }
    }
}
