package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import common.SQL;

public class ReturnBook {
    public static void returnBook(){
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

            if(!isValidBook(BookID)){
                System.out.println("The book doesnt exist!");
                return;
            }

            if (!isValidUser(username)) {
                System.out.println("\nInvalid username");
                return;
            }

            if(!isBorrowed(BookID, username)){
                System.out.println("\nThe Book was not issued to the student");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String returnDate = dateFormat.format(new Date());

            try {
                Connection c = SQL.makeConnection();
                PreparedStatement ps = c.prepareStatement("UPDATE BorrowedBooks SET ReturnDate = ?, Status = 'Returned' where BookID = ? and UserID = (select UserID from user where username = ?)");
                ps.setString(1, returnDate); 
                ps.setInt(2, BookID);
                ps.setString(3, username);
                if(ps.executeUpdate() > 0){
                    ps = c.prepareStatement("UPDATE Book SET AvailableCopies = ? WHERE BookID = ?");
                    ps.setInt(1, getAvailableCopies(BookID) + 1);
                    ps.setInt(2, BookID);
                    ps.executeUpdate();
                    System.out.println("\nBook Returned!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
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

    private static boolean isValidBook(int BookID){
        try (Connection c = SQL.makeConnection();
        PreparedStatement ps = c.prepareStatement("select * from book where BookID = ?")){
            ps.setInt(1, BookID);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()){
                return false;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
