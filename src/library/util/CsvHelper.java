package library.util;

import library.model.Book;
import library.model.Borrower;
import library.model.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Utility class for managing CSV data storage
public class CsvHelper {
    // File path constants
    private static final String DATA_DIR = "data/";
    private static final String BOOKS_FILE = DATA_DIR + "books.csv";
    private static final String BORROWERS_FILE = DATA_DIR + "borrowers.csv";
    private static final String RECORDS_FILE = DATA_DIR + "records.csv";

    // Header constants for CSV files
    private static final String BOOKS_HEADER = "id,title,author,available";
    private static final String BORROWERS_HEADER = "id,name";
    private static final String RECORDS_HEADER = "id,bookId,borrowerId,borrowDate,dueDate,returnDate";

    // Ensures that the data directory exists
    public static void ensureDataDirExists() {
        File dir = new File(DATA_DIR);
        // Create directory and any missing parent directories
        if (!dir.exists()) dir.mkdirs();
    }

    // Loads book data from the CSV file
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            // Skip header if it exists
            String header = br.readLine();
            // Read file line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Parse CSV parts into Book object
                if (parts.length == 4) {
                    books.add(new Book(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
                }
            }
        } catch (IOException e) {
            // File might not exist on first run; ignore error
        }
        return books;
    }

    // Saves the list of books to the CSV file
    public static void saveBooks(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            // Write header
            pw.println(BOOKS_HEADER);
            // Write each book as a CSV line
            for (Book book : books) pw.println(book.toCsv());
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    // Loads borrower data from the CSV file
    public static List<Borrower> loadBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BORROWERS_FILE))) {
            String line;
            // Skip header if it exists
            String header = br.readLine();
            // Read file line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Parse CSV parts into Borrower object
                if (parts.length == 2) {
                    borrowers.add(new Borrower(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            // File might not exist on first run; ignore error
        }
        return borrowers;
    }

    // Saves the list of borrowers to the CSV file
    public static void saveBorrowers(List<Borrower> borrowers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BORROWERS_FILE))) {
            // Write header
            pw.println(BORROWERS_HEADER);
            // Write each borrower as a CSV line
            for (Borrower borrower : borrowers) pw.println(borrower.toCsv());
        } catch (IOException e) {
            System.err.println("Error saving borrowers: " + e.getMessage());
        }
    }

    // Loads borrowing records from the CSV file
    public static List<Record> loadRecords() {
        List<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            // Skip header if it exists
            String header = br.readLine();
            // Read file line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Parse CSV parts into Record object
                if (parts.length == 6) {
                    records.add(new Record(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            // File might not exist on first run; ignore error
        }
        return records;
    }

    // Saves the list of borrowing records to the CSV file
    public static void saveRecords(List<Record> records) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RECORDS_FILE))) {
            // Write header
            pw.println(RECORDS_HEADER);
            // Write each record as a CSV line
            for (Record record : records) pw.println(record.toCsv());
        } catch (IOException e) {
            System.err.println("Error saving records: " + e.getMessage());
        }
    }
}
