package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import common.SQL;

public class Student {

    String username;
    public Student(String username){
        this.username = username;
        System.out.println("Login Successful");
    }

    public static void login(){
        try {
            Connection c = SQL.makeConnection();
            System.out.print("Enter your username: ");
            @SuppressWarnings("resource")
            Scanner s = new Scanner(System.in);
            String username = s.next();
            System.out.print("Enter your password: ");
            String password = s.next();

            PreparedStatement ps = c.prepareStatement("select password from user where username = ? and usertype = 'Student'");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                if(password.equals(resultSet.getString("password"))){
                    new Student(username);
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
