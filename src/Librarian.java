import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Librarian {
    private String username;
    public Librarian(String username) {
        this.username = username;
    }
    static void login() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your Username: ");
        String fUser = s.next();
        System.out.print("Enter your Password: ");
        String passw = s.next();

        try (Connection c = SQL.makeConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT password FROM User WHERE username = ? and role = 'faculty'")) {

            preparedStatement.setString(1, fUser);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                if (res.getObject(1).equals(passw)) {
                    System.out.println("Login Successful");
                    // Instantiate the Student class with the username
                    Librarian loggedInStudent = new Librarian(fUser);
                    // Call the student menu
                    loggedInLibrarian.LibrarianMenu();
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

    private void LibrarianMenu(){
        System.out.flush();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("=== Librarian Menu ===");
            System.out.println("1. Add Books");
            System.out.println("2. View Books");
            System.out.println("3. Issue Books");
            System.out.println("4. View Issued Books");
            System.out.println("5. Return Books");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBooks();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    issueBooks();
                    break;
               case 4:
                   viewIssuedbooks();
                  break;
                case 5:
                    returnBooks();
                    break;
                case 6:
                    changePassword();
                    break;
                case 7:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addLibrarian(){

        try (Connection c = SQL.makeConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM faculty WHERE username = ?")) {

            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();

            try {
                System.out.println();
                res.next();
                System.out.println("Details for user " + username);
                System.out.println("ID: "+ res.getString("userid"));
                System.out.println("First Name: " + res.getString("FirstName"));
                System.out.println("Last Name: " + res.getString("LastName"));
                System.out.println("Username: " + res.getString("Username"));
                System.out.println("Phone: " + res.getString("Contact"));
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
                System.out.println("SQL Error while viewing librarian information: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error while viewing librarian information: " + e.getMessage());
            }

        }  catch (SQLException e) {
            System.out.println("SQL Error while viewing librarian information: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while viewing librarian information: " + e.getMessage());
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

    private void updateStudentInformation() {

        try {
            String stdUsername, column;
            Scanner s = new Scanner(System.in);
            System.out.println("Enter the username of the student: ");
            stdUsername = s.next();
            Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Student WHERE username = ?");
            ps.setString(1, stdUsername);
            ResultSet resultSet = ps.executeQuery();

            // Move to the first row in ResultSet
            if (!resultSet.next()) {
                System.out.println("No matching student found");
                return;
            }

            int columnCount = resultSet.getMetaData().getColumnCount();

            // Print table header with indentation
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(String.format("%-20s", resultSet.getMetaData().getColumnName(i)));
            }
            System.out.println();

            // Print table data with indentation
            do {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(String.format("%-20s", resultSet.getString(i)));
                }
                System.out.println();
            } while (resultSet.next());

            System.out.print("Enter the Column to change: ");
            column = s.next();

            // Check if the column exists
            ResultSetMetaData metaData = resultSet.getMetaData();
            boolean columnExists = false;
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (column.equalsIgnoreCase(metaData.getColumnName(i))) {
                    columnExists = true;
                    break;
                }
            }

            if (!columnExists) {
                System.out.println("Invalid column name");
                return;
            }

            // Get the new value for the column
            System.out.print("Enter the new value for the column: ");
            String newValue = s.next();

            // Execute update query
            String updateQuery = "UPDATE Student SET " + column + " = ? WHERE username = ?";
            PreparedStatement updatePs = c.prepareStatement(updateQuery);
            updatePs.setString(1, newValue);
            updatePs.setString(2, stdUsername);
            int rowsUpdated = updatePs.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student information updated successfully.");
            } else {
                System.out.println("Failed to update student information.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error while updating student information: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while updating student information: " + e.getMessage());
        }
    }

    private void changePassword(){

        try(Connection c = SQL.makeConnection();
            PreparedStatement ps = c.prepareStatement("select password from user WHERE username = ?");)
        {
            String currPass, newPass, actualPass;
            Scanner s = new Scanner(System.in);

            ps.setString(1, this.username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            actualPass = rs.getString("password");
            System.out.print("Enter your current password: ");
            currPass = s.next();

            if(currPass.equals(actualPass)){
                System.out.print("Enter your new password: ");
                newPass = s.next();

                String updateQuery = "UPDATE USER SET " + "password" + " = ? WHERE username = ?";
                PreparedStatement updatePs = c.prepareStatement(updateQuery);
                updatePs.setString(1, newPass);
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

}



