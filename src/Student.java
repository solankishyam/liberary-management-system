import java.sql.*;
import java.util.Scanner;

public class Student {
    private final String username; // Added to store the username after successful login

    // Constructor to initialize the username
    public Student(String username) {
        this.username = username;
    }
    static void login() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your Username: ");
        String sUser = s.next();
        System.out.print("Enter your Password: ");
        String sPassw = s.next();
        String encryptedPass = Hashing.hashPassword(sPassw);

        try (Connection c = SQL.makeConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT password FROM User WHERE username = ?")) {

            preparedStatement.setString(1, sUser);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                if (res.getObject(1).equals(encryptedPass)) {
                    System.out.println("Login Successful");
                    // Instantiate the Student class with the username
                    Student loggedInStudent = new Student(sUser);
                    // Call the student menu
                    loggedInStudent.studentMenu();
                } else {
                    System.out.println("Login Unsuccessful");
                }
            } else {
                System.out.println("Username not found.");
            }

        } catch (Exception e) {
            System.out.println("Login failed. Error: " + e.getMessage());
        }
    }

    public void studentMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Student Menu ===");
            System.out.println("1. View Information");
            System.out.println("2. View Grades");
            System.out.println("3. View Attendance");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewStudentInformation();
                    break;
                case 2:
                    viewStudentGrades();
                    break;
                case 3:
                    viewStudentAttendance();
                    break;

                case 4:
                    changePassword();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewStudentInformation() {
        // Implement the logic to retrieve and display student information
        System.out.println("Viewing student information for username: " + username);
        try (Connection c = SQL.makeConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM student WHERE username = ?")) {

            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();

            try {
                System.out.println();
                res.next();
                System.out.println("Details for user " + username);
                System.out.println("ID: "+ res.getString("student_id"));
                System.out.println("First Name: " + res.getString("first_name"));
                System.out.println("Last Name: " + res.getString("last_name"));
                System.out.println("Email: " + res.getString("email"));
                System.out.println("Phone: " + res.getString("phone_number"));
                System.out.println("Enrollment Date: " +res.getString("enrollment_date"));
                System.out.println("Birthday: " +res.getString("birthdate"));
                System.out.println();
                System.out.println("1. Back\n2. Exit\nEnter your choice: ");
                System.out.println();
                Scanner s = new Scanner(System.in);
                switch (s.nextInt()){
                    case 1:
                        return;
                    case 2:
                        System.exit(0);
                }
            }
            catch (SQLException e) {
                System.out.println("SQL Error while viewing faculty information: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error while viewing faculty information: " + e.getMessage());
            }

        }  catch (SQLException e) {
            System.out.println("SQL Error while viewing faculty information: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while viewing faculty information: " + e.getMessage());
        }
    }

    private void viewStudentGrades() {
        // Implement the logic to retrieve and display student grades
        System.out.println("Viewing student grades for username: " + username);
        // You can query the database for specific student grades based on the username
    }

    private void viewStudentAttendance() {
        // Implement the logic to retrieve and display student attendance
        System.out.println("Viewing student attendance for username: " + username);
        // You can query the database for specific student attendance based on the username
    }

    private void changePassword() {
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select password from user WHERE username = ?");)
        {
            String currPass, newPass,currHashedPass, newHashedPass, actualPass;
            Scanner s = new Scanner(System.in);

            ps.setString(1, this.username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            actualPass = rs.getString("password");
            System.out.print("Enter your current password: ");
            currPass = s.next();
            currHashedPass = Hashing.hashPassword(currPass);


            if(currHashedPass.equals(actualPass)){
                System.out.print("Enter your new password: ");
                newPass = s.next();
                newHashedPass = Hashing.hashPassword(newPass);

                String updateQuery = "UPDATE USER SET " + "password" + " = ? WHERE username = ?";
                PreparedStatement updatePs = c.prepareStatement(updateQuery);
                updatePs.setString(1, newHashedPass);
                updatePs.setString(2, this.username);

                int rowsUpdated = updatePs.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("Failed to update password.");
                }

            }
            else {
                System.out.println("Wrong Password!\n1. Try Again\n2. Back\n3. Exit");
                int choice = s.nextInt();
                switch (choice){
                    case 1:
                        changePassword();
                        break;

                    case 2:
                        return;
                    case 3:
                        System.exit(0);
                        break;
                }
            }

        }
        catch (SQLException e) {
            System.out.println("SQL Error while changing password: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while changing password: " + e.getMessage());
        }
    }

    private void logout(){
        System.out.println("Logout successful. Goodbye!");
        Base.base();
    }
    public static void main(String args[]){
        new Student("sarthakg.here").studentMenu();
    }

}
