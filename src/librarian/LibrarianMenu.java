package librarian;

import java.util.Scanner;

public class LibrarianMenu {
    public static void librarianMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. View Self Information");
            System.out.println("2. Add a book");
            System.out.println("3. View All Books");
            System.out.println("4. Remove a book");
            System.out.println("5. Issue a book");
            System.out.println("6. Return a book");
            System.out.println("7. View borrowed books");
            System.out.println("8. Add Student"); //
            System.out.println("9. View Students Information");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    ViewInformation.viewInfo(Librarian.UserID);
                    break;
                case 2:
                    AddBook.addBook();
                    break;
                case 3:
                    ViewBooks.viewBooks();
                    break;
                case 4:
                    RemoveBook.removeBook();
                    break;
                case 5:
                    IssueBook.issueBook();
                    break;
                case 6:
                    ReturnBook.returnBook();
                    break;
                case 7:
                    ViewBorrowedBooks.viewBorrowedBooks();
                    break;
                case 8:
                    AddStudent.addStudent();
                    break;
                case 9:
                    ViewStudents.viewStudents();
                    break;
                case 10:
                    System.out.println("Exiting librarian menu...");
                    common.Base.base();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 10.");
            }
        } while (choice != 10);
        
        scanner.close();
    }

    
}
