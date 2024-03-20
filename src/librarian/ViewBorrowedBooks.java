package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common.SQL;

public class ViewBorrowedBooks {
    public static void viewBorrowedBooks(){
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT bb.UserID, CONCAT(s.FirstName, ' ', s.LastName) AS 'Full Name', bb.BookID, b.Title, bb.BorrowDate, bb.ReturnDate, bb.Status " + 
                                "FROM BorrowedBooks bb " + 
                                "JOIN Student s ON bb.UserID = s.UserID " + 
                                "JOIN Book b ON bb.BookID = b.BookID;");
        ){
            ResultSet resultSet = ps.executeQuery();

            // Get the number of columns in the result set
            int columnCount = resultSet.getMetaData().getColumnCount();

            System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("| %-20s |", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.println();
            System.out.print("------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            // Print table data with indentation
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("| %-20s |", resultSet.getString(i)));
                }
                System.out.println();
            }

            System.out.print("------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");


            // Close the resources
            resultSet.close();
            ps.close();
            c.close();

            System.out.println();
            System.out.println("1. Back\n2. Exit\nEnter your choice: ");
            System.out.println();
            @SuppressWarnings("resource")
            Scanner s = new Scanner(System.in);
            switch (s.nextInt()){
                case 1:
                    return;
                case 2:
                    System.exit(0);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error during login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }    
    }

}
