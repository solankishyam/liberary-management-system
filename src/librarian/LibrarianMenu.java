package librarian;
import java.util.Scanner;

public class LibrarianMenu {
    public static void librarianMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. Add a book");
            System.out.println("2. View All Books");
            System.out.println("3. Remove a book");
            System.out.println("4. Issue a book");
            System.out.println("5. Return a book");
            System.out.println("6. View borrowed books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    AddBook.addBook();
                    break;
                case 2:
                    ViewBooks.viewBooks();
                    break;
                case 3:
                    RemoveBook.removeBook();
                    break;
                case 4:
                    IssueBook.issueBook();
                    break;
                case 5:
                    ReturnBook.returnBook();
                    break;
                case 6:
                    ViewBorrowedBooks.viewBorrowedBooks();
                    break;
                case 7:
                    System.out.println("Exiting librarian menu...");
                    common.Base.base();
                    return;
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
