package library.ui;

import java.util.Scanner;
import library.service.LibrarySystem;

// Main entry point for the Library Management System
public class Main {
    // Shared system instance and input scanner
    private static final LibrarySystem system = new LibrarySystem();
    private static final Scanner scanner = new Scanner(System.in);

    // Main execution loop
    public static void main(String[] args) {
        System.out.println("=== Books Borrowing System ===");
        
        boolean running = true;
        while (running) {
            
            // Display options and get user input
            printMenu();
            String choice = scanner.nextLine();
            
            // Route user selection to appropriate handler
            switch (choice) {
                case "1": addBook(); break;
                case "2": removeBook(); break;
                case "3": listBooks(); break;
                case "4": registerBorrower(); break;
                case "5": listBorrowers(); break;
                case "6": borrowBook(); break;
                case "7": returnBook(); break;
                case "8": listRecords(); break;
                case "9": 
                    // Terminate the application
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Displays the main menu options
    private static void printMenu() {
        System.out.println("\n1. Add Book\n2. Remove Book\n3. List All Books\n4. Register Borrower");
        System.out.println("5. List All Borrowers\n6. Borrow Book\n7. Return Book\n8. List All Records\n9. Exit");
        System.out.print("Choose an option: ");
    }

    // Collects input to add a new book
    private static void addBook() {
        System.out.print("Enter Book ID (leave blank for auto-generate): ");
        String id = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        // Delegate addition to the system and check for success
        if (system.addBook(id, title, author)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error: Book with ID " + id + " already exists.");
        }
    }

    // Removes a book by ID
    private static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String id = scanner.nextLine();
        // Check if removal was successful
        if (system.removeBook(id)) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // Lists all books in the library
    private static void listBooks() {
        System.out.println("\n--- Books List ---");
        // Print each book using its toString method
        system.getAllBooks().forEach(System.out::println);
    }

    // Registers a new borrower
    private static void registerBorrower() {
        System.out.print("Enter Borrower ID (leave blank for auto-generate): ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        // Delegate registration to the system and check for success
        if (system.registerBorrower(id, name)) {
            System.out.println("Borrower registered successfully.");
        } else {
            System.out.println("Error: Borrower with ID " + id + " already exists.");
        }
    }

    // Lists all registered borrowers
    private static void listBorrowers() {
        System.out.println("\n--- Borrowers List ---");
        // Print each borrower using its toString method
        system.getAllBorrowers().forEach(System.out::println);
    }

    // Processes a book borrowing transaction
    private static void borrowBook() {
        System.out.print("Enter Book ID: ");
        String bId = scanner.nextLine();
        System.out.print("Enter Borrower ID: ");
        String brId = scanner.nextLine();
        // Perform borrow operation and display result
        System.out.println(system.borrowBook(bId, brId));
    }

    // Processes a book return transaction
    private static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        String bId = scanner.nextLine();
        // Perform return operation and display result
        System.out.println(system.returnBook(bId));
    }

    // Lists all borrowing records
    private static void listRecords() {
        System.out.println("\n--- Borrowing Records ---");
        // Print each record using its toString method
        system.getAllRecords().forEach(System.out::println);
    }
}
