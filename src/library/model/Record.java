package library.model;

// Represents a record of a borrowed book
public class Record extends BaseEntity {
    private String bookId;
    private String borrowerId;
    private String dateBorrowed;
    private String dueDate;
    private String returnDate;

    // Constructor to initialize a borrowing record
    public Record(String recordId, String bookId, String borrowerId, String dateBorrowed, String dueDate, String returnDate) {
        super(recordId);
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    // Getters and setters for record details
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getBorrowerId() { return borrowerId; }
    public void setBorrowerId(String borrowerId) { this.borrowerId = borrowerId; }

    public String getDateBorrowed() { return dateBorrowed; }
    public void setDateBorrowed(String dateBorrowed) { this.dateBorrowed = dateBorrowed; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    // Display formatted record information
    @Override
    public String toString() {
        return String.format("ID: %s | Book ID: %s | Borrower ID: %s | Borrowed: %s | Due: %s | Returned: %s",
                getId(), bookId, borrowerId, dateBorrowed, dueDate, (returnDate.equals("null") ? "N/A" : returnDate));
    }

    // Converts data to CSV format for persistence
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s", getId(), bookId, borrowerId, dateBorrowed, dueDate, returnDate);
    }
}
