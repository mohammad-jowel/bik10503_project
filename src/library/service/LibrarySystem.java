package library.service;

import library.model.Book;
import library.model.Borrower;
import library.model.Record;
import library.util.CsvHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // Generates the next sequential ID based on a prefix and existing items
    private String generateNextId(String prefix, List<? extends library.model.BaseEntity> entities) {
        int maxId = 0;
        for (library.model.BaseEntity entity : entities) {
            String id = entity.getId();
            if (id != null && id.startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(id.substring(prefix.length()));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("%s%03d", prefix, maxId + 1);
    }

    // Adds a new book to the collection
    public boolean addBook(String id, String title, String author) {
        // If id is empty or null, generate it
        if (id == null || id.trim().isEmpty()) {
            id = generateNextId("B", books);
        }

        final String finalId = id;
        // Check if book ID already exists
        if (books.stream().anyMatch(b -> b.getId().equals(finalId))) {
            return false;
        }
        // Create new book object (available by default)
        books.add(new Book(finalId, title, author, true));
        // Persist updated book list
        CsvHelper.saveBooks(books);
        return true;
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
    public boolean registerBorrower(String id, String name) {
        // If id is empty or null, generate it
        if (id == null || id.trim().isEmpty()) {
            id = generateNextId("BR", borrowers);
        }

        final String finalId = id;
        // Check if borrower ID already exists
        if (borrowers.stream().anyMatch(b -> b.getId().equals(finalId))) {
            return false;
        }
        // Create and add new borrower
        borrowers.add(new Borrower(finalId, name));
        // Persist updated borrower list
        CsvHelper.saveBorrowers(borrowers);
        return true;
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

        // Generate sequential record ID
        String recordId = generateNextId("REC", records);
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
