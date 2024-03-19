package librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import common.SQL;

public class AddBook {
    public static void addBook(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Add a New Book ---");
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter available copies: ");
        int availableCopies = scanner.nextInt();
        
        System.out.print("Enter total copies: ");
        int totalCopies = scanner.nextInt();

        addBookToDatabase(title, author, availableCopies, totalCopies);

        new LibrarianMenu();
        
    }

    private static void addBookToDatabase(String title, String Author, int availableCopies, int totalCopies){
        try {
            Connection con = SQL.makeConnection();
            PreparedStatement ps = con.prepareStatement("Insert into book(title, author, availableCopies, TotalCopies) values(?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, Author);
            ps.setInt(3, availableCopies);
            ps.setInt(4, totalCopies);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Books entered Successfully");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
