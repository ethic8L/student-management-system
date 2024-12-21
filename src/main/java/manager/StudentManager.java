package manager;

import model.Student;
import java.util.ArrayList;

public interface StudentManager {
     void addStudent(Student student);
     boolean removeStudent(String studentID);
     void updateStudent(String studentID, Student updatedStudent);
     ArrayList<Student> displayAllStudents();
     double calculateAverageGrade();
}
