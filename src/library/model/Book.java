package library.model;

// Represents a book in the library
public class Book extends BaseEntity {
    private String title;
    private String author;
    private boolean available;

    // Constructor to initialize book details
    public Book(String bookId, String title, String author, boolean available) {
        super(bookId);
        this.title = title;
        this.author = author;
        this.available = available;
    }

    // Getters and setters for book fields
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // Returns a formatted string with book details
    @Override
    public String toString() {
        return String.format("ID: %s | Title: %-20s | Author: %-20s | Available: %s",
                getId(), title, author, available ? "Yes" : "No");
    }

    // Converts data to CSV format for persistence
    public String toCsv() {
        return String.format("%s,%s,%s,%b", getId(), title, author, available);
    }
}
