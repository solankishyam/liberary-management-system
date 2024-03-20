package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common.SQL;

public class RemoveBook {
    public static void removeBook(){
        viewBooks();
        System.out.println("Enter the ID of the you wish to remove: ");
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        int ID = s.nextInt();

        try (Connection c = SQL.makeConnection();
        PreparedStatement ps = c.prepareStatement("delete from Book where bookid = ?");){
            ps.setInt(1, ID);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Book removed successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    public static void viewBooks(){
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select * from Book");
        ){
            ResultSet resultSet = ps.executeQuery();

            // Get the number of columns in the result set
            int columnCount = resultSet.getMetaData().getColumnCount();

            System.out.print("\n------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("| %-20s |", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.print("\n--------------------------------------------------------------------------------------------------------------------------------------------------\n");
            // Print table data with indentation
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("| %-20s |", resultSet.getString(i)));
                }
                System.out.println();
            }

            System.out.print("--------------------------------------------------------------------------------------------------------------------------------------------------\n");


            // Close the resources
            resultSet.close();
            ps.close();
            c.close();

            System.out.println();
        }
        catch (SQLException e) {
            System.out.println("SQL Error during login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }    
    }
}
