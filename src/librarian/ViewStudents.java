package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.SQL;

public class ViewStudents {
    public static void viewStudents(){
        try {
            Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select FirstName as 'First Name', LastName as 'Last Name', PhoneNumber as 'Phone Number', Gender, Email from student");
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
}
