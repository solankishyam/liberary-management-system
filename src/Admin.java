import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Admin {
    String username;

    private Admin(String username){
        this.username = username;
    }

    static void login() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your Username: ");
        String aUser = s.next();
        System.out.print("Enter your Password: ");
        String aPassw = s.next();
        String encryptedPass = Hashing.hashPassword(aPassw);

        try (Connection c = SQL.makeConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT password FROM User WHERE username = ? and role = 'admin'");) {

            preparedStatement.setString(1, aUser);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                if (res.getObject(1).equals(encryptedPass)) {
                    System.out.println("Login Successful");
                    // Instantiate the Student class with the username
                    Admin loggedInAdmin = new Admin(aUser);
                    // Call the student menu
                    loggedInAdmin.adminMenu();
                } else {
                    System.out.println("Login Unsuccessful");
                }
            } else {
                System.out.println("Username not found.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error during login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private void adminMenu(){
        System.out.flush();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("=== Admin Menu ===");
            System.out.println("1. View Information");
            System.out.println("2. Add Faculty");
            System.out.println("3. Add Course");
            System.out.println("4. View all Students");
            System.out.println("5. Add Student");
            System.out.println("6. Update Student Information");
            System.out.println("7. Change Password");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
//                case 1:
//                    viewFacultyInformation();
//                    break;
//                case 2:
//                    viewAllStudents();
//                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    viewAllStudents();
                    break;
//                case 5:
//                    updateStudentInformation();
//                    break;
//                case 6:
//                    changePassword();
//                    break;
                case 7:
                    changePassword();
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addCourse(){
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("Insert into course(course_name, course_credits, semester) values(?, ?, ?)");){
            int credits;
            String courseName, semester;
            Scanner s = new Scanner(System.in);
            System.out.print("Name of Course: ");
            courseName = s.nextLine();
            System.out.print("SEM: ");
            semester = "SEM " + s.next().toUpperCase();
            System.out.println(semester);
            System.out.print("Credits: ");
            credits = s.nextInt();

            ps.setString(1, courseName);
            ps.setInt(2, credits);
            ps.setString(3, semester);

            int updatedRows = ps.executeUpdate();
            if(updatedRows>0){
                System.out.println("Course Added Successfully");
            }
            else {
                System.out.println("Error adding course");

            }
        }
        catch (Exception e){
            System.out.println("Error Adding Course: " + e.getMessage());
        }
    }

    private void viewAllStudents(){
        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select * from student");
        ){
            ResultSet resultSet = ps.executeQuery();

            // Get the number of columns in the result set
            int columnCount = resultSet.getMetaData().getColumnCount();

            System.out.print("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("| %-20s |", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.print("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            // Print table data with indentation
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("| %-20s |", resultSet.getString(i)));
                }
                System.out.println();
            }

            System.out.print("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");


            // Close the resources
            resultSet.close();
            ps.close();
            c.close();

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
            System.out.println("SQL Error during login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private void addStudent() {
        String firstName, lastName, email, phone, username, birthdate, enrollmentDate;
        Date bday = null, enroll = null; // Initialize enroll with null
        Scanner s = new Scanner(System.in);
        System.out.print("Enter First Name: ");
        firstName = s.next();

        System.out.print("Enter Last Name: ");
        lastName = s.next();

        System.out.print("Enter Email: ");
        email = s.next();

        System.out.print("Enter Phone No.: ");
        phone = s.next();

        System.out.print("Enter Username: ");
        username = s.next();

        System.out.println("Enter Birthdate, (YYYY-MM-DD): ");
        birthdate = s.next();

        System.out.println("Enter Enrollment Date, (YYYY-MM-DD): ");
        enrollmentDate = s.next();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            bday = new java.sql.Date(dateFormat.parse(birthdate).getTime());
            enroll = new java.sql.Date(dateFormat.parse(enrollmentDate).getTime());
        } catch (Exception e) {
            System.out.println("Error while adding student: " + e.getMessage());
        }

        try (Connection c = SQL.makeConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO student(first_name, last_name, email, phone_number, username, birthdate, enrollment_date) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setDate(6, (java.sql.Date) bday);

            if (enroll != null) {
                ps.setDate(7, (java.sql.Date) enroll);
            } else {
                // Handle the case when enroll is still null
                ps.setNull(7, java.sql.Types.DATE);
            }

            System.out.println(ps);
            int rowsAffected = ps.executeUpdate(); // Use executeUpdate instead of executeQuery for INSERT operations
            if (rowsAffected > 0) {
                System.out.println("Student Added!");
                PreparedStatement userTable = c.prepareStatement("INSERT INTO user(username, role) VALUES (?, ?)");
                userTable.setString(1, username);
                userTable.setString(2, "student");
                userTable.executeUpdate();
                System.out.println("User Generated!");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while adding student: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while adding student: " + e.getMessage());
        }
    }

    private void logout(){
        System.out.println("Logout successful. Goodbye!");
        Base.base();
    }

    private void changePassword(){

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

    public static void main(String[] args) {
        new Admin("admin").addCourse();
    }
}
