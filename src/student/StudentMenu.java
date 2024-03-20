package student;
import java.util.Scanner;

public class StudentMenu {
    public static void displayMenu(int UserID) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View available books");
            System.out.println("2. View borrowed books");
            System.out.println("3. View account information");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ViewBooks.viewBooks();
                    break;
                case 2:
                    ViewBorrowedBooks.viewBorrowedBooks(UserID);
                    break;
                case 3:
                    ViewInformation.viewInfo(UserID);
                    break;
                case 4:
                    System.out.println("Exiting student menu...");
                    common.Base.base();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    break;
            }
        } while (choice != 4);

        scanner.close();
    }

    public static void main(String[] args) {
        displayMenu(1);
    }
}
