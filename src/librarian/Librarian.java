package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import common.SQL;

public class Librarian {
    String username;
    public Librarian(String username){
        this.username = username;
        LibrarianMenu.librarianMenu();
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

            PreparedStatement ps = c.prepareStatement("select password from user where username = ? and usertype = 'Librarian'");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                if(password.equals(resultSet.getString("password"))){
                    new Librarian(username);
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
