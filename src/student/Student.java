package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import common.SQL;

public class Student {

    int UserID;
    public Student(int UserID){
        this.UserID = UserID;
        System.out.println("\nLogin Successful\n");
        StudentMenu.displayMenu(UserID);

    }

    public static void login(){
        try {
            int userID;
            Connection c = SQL.makeConnection();
            System.out.print("Enter your username: ");
            @SuppressWarnings("resource")
            Scanner s = new Scanner(System.in);
            String username = s.next();
            System.out.print("Enter your password: ");
            String password = s.next();

            PreparedStatement ps = c.prepareStatement("select UserID, password from user where username = ? and usertype = 'Student'");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                if(password.equals(resultSet.getString("password"))){
                    userID = resultSet.getInt("userID");
                    new Student(userID);
                }
                else{
                    System.out.println("Wrong password, try again!");
                    login();
                }
            }
            else{
                System.out.println("User not found");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
}
