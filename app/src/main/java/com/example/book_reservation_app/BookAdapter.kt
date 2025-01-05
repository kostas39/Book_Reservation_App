import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book_reservation_app.Book
import com.example.book_reservation_app.R

class BookAdapter(
    private val books: List<Book>,
    private val onReserveClick: (Book, Boolean) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // Track reservation state for each book
    private val reservedBooks = mutableSetOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        val isReserved = reservedBooks.contains(book)
        holder.bind(book, isReserved)
    }

    override fun getItemCount(): Int = books.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookName: TextView = itemView.findViewById(R.id.bookTitle)
        private val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        private val bookPublisher: TextView = itemView.findViewById(R.id.bookPublisher)
        private val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
        private val reserveButton: Button = itemView.findViewById(R.id.reserveButton)

        fun bind(book: Book, isReserved: Boolean) {
            bookName.text = book.bookName
            bookAuthor.text = book.bookAuthors
            bookPublisher.text = book.bookPublisher

            Glide.with(itemView.context)
                .load(book.bookImage)
                .into(bookImage)

            // Update button based on reservation state
            updateButtonState(isReserved)

            // Set button click listener
            reserveButton.setOnClickListener {
                val newState = !isReserved // Toggle state
                if (newState) {
                    reservedBooks.add(book)
                } else {
                    reservedBooks.remove(book)
                }
                updateButtonState(newState)
                onReserveClick(book, newState)
            }
        }

        private fun updateButtonState(isReserved: Boolean) {
            if (isReserved) {
                reserveButton.text = "Remove Reservation"
                reserveButton.setBackgroundColor(itemView.context.getColor(R.color.orange))
            } else {
                reserveButton.text = "Reserve Book"
                reserveButton.setBackgroundColor(itemView.context.getColor(R.color.purple_500))
            }
        }
    }
}
