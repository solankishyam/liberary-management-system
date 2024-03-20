package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.SQL;

public class ViewBorrowedBooks {
    public static void viewBorrowedBooks(int UserID){
        try {
            Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT b.Title, b.Author, bb.BorrowDate, bb.ReturnDate, bb.Status " +
            "FROM BorrowedBooks bb " +
            "JOIN Book b ON bb.BookID = b.BookID " +
            "JOIN User u ON bb.UserID = u.UserID " +
            "WHERE u.userid = ?");
            ps.setInt(1, UserID);
            
            ps.setInt(1, UserID);
            ResultSet resultSet = ps.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();

            System.out.print("------------------------------------------------------------------------------------------------------------------------\n");

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("| %-20s |", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.println();
            System.out.print("------------------------------------------------------------------------------------------------------------------------\n");
            // Print table data with indentation
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("| %-20s |", resultSet.getString(i)));
                }
                System.out.println();
            }

            System.out.print("------------------------------------------------------------------------------------------------------------------------\n");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        viewBorrowedBooks(6);
    }
}
