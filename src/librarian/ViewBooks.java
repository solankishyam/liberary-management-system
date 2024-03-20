package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common.SQL;

public class ViewBooks {
    public static void viewBooks(){
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select * from Book");
        ){
            ResultSet resultSet = ps.executeQuery();

            // Get the number of columns in the result set
            int columnCount = resultSet.getMetaData().getColumnCount();

            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------\n");

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("| %-20s |", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------\n");
            // Print table data with indentation
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("| %-20s |", resultSet.getString(i)));
                }
                System.out.println();
            }

            System.out.print("\n-----------------------------------------------------------------------------------------------------------------------\n");


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
    public static void main(String[] args) {
        viewBooks();
    }
}   
