package librarian;
import java.util.Scanner;

public class LibrarianMenu {
    public static void librarianMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. Add a book");
            System.out.println("2. Remove a book");
            System.out.println("3. Issue a book");
            System.out.println("4. Return a book");
            System.out.println("5. View borrowed books");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    AddBook.addBook();
                    break;
                case 2:
                    // Call method to remove a book
                    break;
                case 3:
                    // Call method to issue a book
                    break;
                case 4:
                    // Call method to return a book
                    break;
                case 5:
                    // Call method to view borrowed books
                    break;
                case 6:
                    System.out.println("Exiting librarian menu...");
                    new common.Base();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);
        
        scanner.close();
    }

    public static void main(String[] args) {
        new LibrarianMenu();
    }
}
