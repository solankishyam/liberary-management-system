package common;
import java.util.Scanner;
import librarian.*;
import student.*;

public class Base {
    public static void base(){
        while (true){
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1. Student Login");
            System.out.println("2. Librarian Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            @SuppressWarnings("resource")
            Scanner s = new Scanner(System.in);
            int choice = s.nextInt();
            switch (choice){
                case 1:
                    Student.login();
                    break;
                case 2:
                    Librarian.login();
                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Enter a valid choice: ");
                    break;
            }

        }


    }
}
