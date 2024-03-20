package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import common.SQL;

public class AddStudent {
    
    public static void addStudent(){
        final String DEFAULT_PASSWORD = "BVIMR123";
        try{
            @SuppressWarnings("resource")
            Scanner s = new Scanner(System.in);
            System.out.print("Enter the username: ");
            String username = s.next();

            System.out.print("Enter First Name: ");
            String firstName = s.next();

            System.out.print("Enter Last Name: ");
            String lastName = s.next();

            System.out.print("Enter the Phone Number: ");
            String phNo = s.next();

            System.out.print("Enter Gender(Male, Female, Others): ");
            String gender = s.next();

            System.out.print("Enter Email: ");
            String email = s.next();

            Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("Insert Into USER(Username, Password, UserType) Values(?, ?, 'Student')");
            ps.setString(1, username);
            ps.setString(2, DEFAULT_PASSWORD);
            if(ps.executeUpdate() > 0){
                ps = c.prepareStatement("Insert Into Student(UserID, FirstName, LastName, PhoneNumber, Gender, Email) Values((select userID from user where username = ?), ?, ?, ?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, phNo);
                ps.setString(5, gender);
                ps.setString(6, email);

                if(ps.executeUpdate()>0){
                    System.out.println("\nStudent Added Successfully");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
