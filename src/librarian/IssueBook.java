package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

import common.SQL;

public class IssueBook {
    public static void issueBook() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        RemoveBook.viewBooks();
        System.out.println();
        System.out.println("--- Issue a Book ---");

        try {
            // Prompt librarian to enter user ID and book ID
            System.out.print("Enter the username: ");
            String username = scanner.next();
            System.out.print("Enter the BookID: ");
            int BookID = scanner.nextInt();

            // Check if the book is available for borrowing
            if(getAvailableCopies(BookID) <= 0){
                System.out.println("\nSorry, the book is not available for borrowing.");
                return;
            }

            if (!isValidUser(username)) {
                System.out.println("\nInvalid username or user type does not allow borrowing.");
                return;
            }

            if(isBorrowed(BookID, username)){
                System.out.println("\nThe student has already borrowed this book.");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String borrowDate = dateFormat.format(new Date());

            // Insert a new record into BorrowedBooks table
            try {
                Connection c = SQL.makeConnection();
                PreparedStatement ps = c.prepareStatement("INSERT INTO BorrowedBooks (UserID, BookID, BorrowDate, Status) VALUES ((select UserID from user where username = ?), ?, ?, 'Borrowed')");
                ps.setString(1, username); 
                ps.setInt(2, BookID);
                ps.setString(3, borrowDate);
                if(ps.executeUpdate() > 0){
                    ps = c.prepareStatement("UPDATE Book SET AvailableCopies = ? WHERE BookID = ?");
                    ps.setInt(1, getAvailableCopies(BookID) - 1);
                    ps.setInt(2, BookID);
                    ps.executeUpdate();
                    System.out.println("\nBook Issued Successfully!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    private static int getAvailableCopies(int bookID) {
        String query = "SELECT AvailableCopies FROM Book WHERE BookID = ?";
        int availableCopies = 0;
        try (Connection c = SQL.makeConnection();
        PreparedStatement ps = c.prepareStatement(query)){
            ps.setInt(1, bookID);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            availableCopies = resultSet.getInt("AvailableCopies");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return availableCopies;
    }

    private static boolean isValidUser(String username) {
        String query = "SELECT UserType FROM User WHERE Username = ?";
        try (Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("UserType");
                // Check if the user type allows borrowing
                return userType.equals("Student") || userType.equals("OtherUserType");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static boolean isBorrowed(int bookID, String username){
        
        try (Connection c = SQL.makeConnection();
        PreparedStatement ps = c.prepareStatement("select * from BorrowedBooks where BookID = ? and UserID = (Select UserID from user where username = ?)");){
            ps.setInt(1, bookID);
            ps.setString(2, username);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
