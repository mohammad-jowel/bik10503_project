package library.service;

import library.model.Book;
import library.model.Borrower;
import library.model.Record;
import library.util.CsvHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Orchestrates library operations and data persistence
public class LibrarySystem {
    private List<Book> books;
    private List<Borrower> borrowers;
    private List<Record> records;

    // Initializes the system and loads existing data
    public LibrarySystem() {
        // Create data directory if it doesn't exist
        CsvHelper.ensureDataDirExists();
        // Load existing collections from CSV files
        this.books = CsvHelper.loadBooks();
        this.borrowers = CsvHelper.loadBorrowers();
        this.records = CsvHelper.loadRecords();
    }

    // Adds a new book to the collection
    public void addBook(String id, String title, String author) {
        // Create new book object (available by default)
        books.add(new Book(id, title, author, true));
        // Persist updated book list
        CsvHelper.saveBooks(books);
    }

    // Removes a book by its ID
    public boolean removeBook(String id) {
        // Filter out book with matching ID
        boolean removed = books.removeIf(b -> b.getId().equals(id));
        // Save changes if a book was removed
        if (removed) CsvHelper.saveBooks(books);
        return removed;
    }

    // Retrieves all books in the system
    public List<Book> getAllBooks() {
        return books;
    }

    // Registers a new borrower in the system
    public void registerBorrower(String id, String name) {
        // Create and add new borrower
        borrowers.add(new Borrower(id, name));
        // Persist updated borrower list
        CsvHelper.saveBorrowers(borrowers);
    }

    // Retrieves all registered borrowers
    public List<Borrower> getAllBorrowers() {
        return borrowers;
    }

    // Handles the borrowing logic including validation and record creation
    public String borrowBook(String bookId, String borrowerId) {
        // Validate book availability and existence
        Optional<Book> bookOpt = books.stream()
                .filter(b -> b.getId().equals(bookId) && b.isAvailable())
                .findFirst();

        if (bookOpt.isEmpty()) return "Book not available or not found.";

        // Validate borrower existence
        Optional<Borrower> borrowerOpt = borrowers.stream()
                .filter(b -> b.getId().equals(borrowerId))
                .findFirst();

        if (borrowerOpt.isEmpty()) return "Borrower not found.";

        // Update book status to unavailable
        Book book = bookOpt.get();
        book.setAvailable(false);

        // Generate unique record ID and calculate dates
        String recordId = UUID.randomUUID().toString().substring(0, 8);
        LocalDate borrowedDate = LocalDate.now();
        LocalDate dueDate = borrowedDate.plusDays(14); // 2-week loan period

        // Create new borrowing record
        Record record = new Record(recordId, bookId, borrowerId, 
                borrowedDate.toString(), dueDate.toString(), "null");
        
        records.add(record);
        
        // Save all changes to files
        CsvHelper.saveBooks(books);
        CsvHelper.saveRecords(records);
        
        return "Book borrowed successfully. Record ID: " + recordId;
    }

    // Handles returning a book and updating records
    public String returnBook(String bookId) {
        // Find the active borrowing record (where return date is null)
        Optional<Record> recordOpt = records.stream()
                .filter(r -> r.getBookId().equals(bookId) && r.getReturnDate().equals("null"))
                .findFirst();

        if (recordOpt.isEmpty()) return "No active record found for this book.";

        // Update return date to today
        Record record = recordOpt.get();
        record.setReturnDate(LocalDate.now().toString());

        // Set the book to available again
        books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .ifPresent(b -> b.setAvailable(true));

        // Save updated data to files
        CsvHelper.saveBooks(books);
        CsvHelper.saveRecords(records);

        return "Book returned successfully.";
    }

    // Retrieves all borrowing records
    public List<Record> getAllRecords() {
        return records;
    }
}
