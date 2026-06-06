package library.model;

// Represents a library borrower
public class Borrower extends BaseEntity {
    private String name;

    // Constructor to initialize borrower details
    public Borrower(String borrowerId, String name) {
        super(borrowerId);
        this.name = name;
    }

    // Getter and setter for borrower name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Returns a string with borrower information
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s", getId(), name);
    }

    // Converts data to CSV format for persistence
    public String toCsv() {
        return String.format("%s,%s", getId(), name);
    }
}
