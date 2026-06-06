# Library Management System

A simple Java-based command-line application for managing book borrowings, borrowers, and library inventory.

## Features

- **Book Management**: Add, remove, and list books in the inventory.
- **Borrower Management**: Register and list library borrowers.
- **Borrowing System**: Process book borrowings with automated due date calculation (14 days).
- **Return System**: Process book returns and update borrowing records.
- **Data Persistence**: All data is saved to and loaded from CSV files in the `data/` directory.

## Project Structure

- `src/library/model/`: Data models for Books, Borrowers, and Records.
- `src/library/service/`: Core business logic (LibrarySystem).
- `src/library/ui/`: Command-line interface (Main).
- `src/library/util/`: Utility classes for CSV file handling.
- `data/`: CSV files used for data persistence.

## How to Run

1. **Compile the program**:
   ```bash
   javac -d bin src/library/**/*.java
   ```

2. **Run the application**:
   ```bash
   java -cp bin library.ui.Main
   ```

## Data Storage

The system uses three CSV files for persistence:
- `books.csv`: Stores book details and availability.
- `borrowers.csv`: Stores registered borrower information.
- `records.csv`: Stores the history of borrowing transactions.
