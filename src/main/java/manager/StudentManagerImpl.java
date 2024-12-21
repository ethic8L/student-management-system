package manager;

import database.DatabaseConnection;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentManagerImpl implements StudentManager {
    private static final Logger logger = Logger.getLogger(StudentManagerImpl.class.getName());
    private Connection connection;

    public StudentManagerImpl() {
        connection = DatabaseConnection.getConnection();
    }

    private boolean validateStudent(Student student) {
        if (student.getGrade() < 0.0 || student.getGrade() > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0");
        }
        if (student.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be a positive number");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return true;
    }

    private boolean isStudentIDExist(String studentID) {
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking if student ID exists", e);
        }
        return false;
    }

    @Override
    public void addStudent(Student student) {
        if (validateStudent(student)) {
            if (isStudentIDExist(student.getStudentID())) {
                logger.warning("A student with this ID already exists!");
                return;
            }

            String sql = "INSERT INTO students (name, age, grade, studentID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, student.getName());
                pstmt.setInt(2, student.getAge());
                pstmt.setDouble(3, student.getGrade());
                pstmt.setString(4, student.getStudentID());
                pstmt.executeUpdate();
                logger.info("Student added successfully.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error adding student", e);
            }
        }
    }

    @Override
    public boolean removeStudent(String studentID) {
        String sql = "DELETE FROM students WHERE studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                logger.info("Student removed successfully.");
                return true;
            } else {
                logger.warning("Student ID not found.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error removing student", e);
        }
        return false;
    }

    @Override
    public void updateStudent(String studentID, Student updatedStudent) {
        if (validateStudent(updatedStudent)) {
            String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, updatedStudent.getName());
                pstmt.setInt(2, updatedStudent.getAge());
                pstmt.setDouble(3, updatedStudent.getGrade());
                pstmt.setString(4, studentID);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0){
                    logger.info("Student updated successfully.");
                } else {
                    logger.warning("Student ID not found for update.");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error updating student", e);
            }
        }
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("grade"),
                        rs.getString("studentID")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error displaying all students", e);
        }
        return students;
    }

    @Override
    public double calculateAverageGrade() {
        String sql = "SELECT AVG(grade) AS avgGrade FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("avgGrade");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error calculating average grade", e);
        }
        return 0.0;
    }

    // Ensure to close the connection if it's not needed anymore (may require adjustment depending on your connection pool management)
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing connection", e);
        }
    }
}
