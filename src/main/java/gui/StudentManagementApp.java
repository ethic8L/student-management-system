package gui;

import manager.StudentManagerImpl;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementApp {

    // Function to validate age
    public static boolean validateAge(int age, int minAge, int maxAge) {
        return age >= minAge && age <= maxAge;
    }

    // Function to validate name (ensuring name starts with a capital letter)
    public static boolean validateName(String name) {
        // Check if name is not null, not empty, and starts with a capital letter
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Check that name contains only letters and spaces
        if (!name.matches("[a-zA-Z ]+")) {
            return false;
        }
        // Ensure the first letter is capitalized
        char firstChar = name.charAt(0);
        return Character.isUpperCase(firstChar);
    }

    // Function to validate grade
    public static boolean validateGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    public static void main(String[] args) {
        StudentManagerImpl manager = new StudentManagerImpl();

        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // increased window size for better layout

        // custom look for better consistency across platforms
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // main panel with a custom background color
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(228, 228, 228)); // light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and input fields
        JLabel labelID = new JLabel("Student ID:");
        JTextField fieldID = new JTextField();
        JLabel labelName = new JLabel("Name:");
        JTextField fieldName = new JTextField();
        JLabel labelAge = new JLabel("Age:");
        JTextField fieldAge = new JTextField();
        JLabel labelGrade = new JLabel("Grade:");
        JTextField fieldGrade = new JTextField();

        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton averageButton = new JButton("Calculate Average");

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setBackground(new Color(228, 228, 228)); // light gray background for output area
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // font and style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        addButton.setFont(buttonFont);
        removeButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        displayButton.setFont(buttonFont);
        averageButton.setFont(buttonFont);

        // GridBagLayout to organize components nicely
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelID, gbc);
        gbc.gridx = 1;
        panel.add(fieldID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelName, gbc);
        gbc.gridx = 1;
        panel.add(fieldName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelAge, gbc);
        gbc.gridx = 1;
        panel.add(fieldAge, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelGrade, gbc);
        gbc.gridx = 1;
        panel.add(fieldGrade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(removeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(updateButton, gbc);
        gbc.gridx = 1;
        panel.add(displayButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(averageButton, gbc);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Action listener for Add Student button
        addButton.addActionListener(e -> {
            try {
                String id = fieldID.getText();
                String name = fieldName.getText();
                int age = Integer.parseInt(fieldAge.getText());
                double grade = Double.parseDouble(fieldGrade.getText());

                // Check if a student with the same ID already exists in the manager's list
                boolean studentExists = false;
                for (Student student : manager.displayAllStudents()) {
                    if (student.getStudentID().equals(id)) {
                        studentExists = true;
                        break;
                    }
                }

                if (studentExists) {
                    outputArea.setForeground(Color.RED);  // Set text color to red for error
                    outputArea.setText("Error: A student with this ID already exists!");
                } else {
                    // Validations
                    if (!validateName(name)) {
                        outputArea.setForeground(Color.RED);  // Set text color to red for error
                        outputArea.setText("Error: Name must start with a capital letter, containing only letters and spaces, and cannot be empty.");
                    } else if (!validateAge(age, 18, 100)) { // Check if age is between 18 and 100
                        outputArea.setForeground(Color.RED);  // Set text color to red for error
                        outputArea.setText("Error: Age must be between 18 and 100.");
                    } else if (!validateGrade(grade)) { // Check if grade is between 0 and 100
                        outputArea.setForeground(Color.RED);  // Set text color to red for error
                        outputArea.setText("Error: Grade must be between 0 and 100.");
                    } else {
                        manager.addStudent(new Student(name, age, grade, id));
                        outputArea.setForeground(Color.GREEN); // Set text color to green for success
                        outputArea.setText("Student added successfully!");
                    }
                }
            } catch (NumberFormatException ex) {
                outputArea.setForeground(Color.RED);  // Set text color to red for error
                outputArea.setText("Error: Invalid input. Please enter valid data.");
            }
        });

        // Action listener for Remove Student button
        removeButton.addActionListener(e -> {
            String id = fieldID.getText();
            boolean success = manager.removeStudent(id);
            if (success) {
                outputArea.setForeground(Color.GREEN); // Set text color to green for success
                outputArea.setText("Student removed successfully!");
            } else {
                outputArea.setForeground(Color.RED);  // Set text color to red for error
                outputArea.setText("Error: Student ID not found.");
            }
        });

        // Action listener for Update Student button
        updateButton.addActionListener(e -> {
            try {
                String id = fieldID.getText();
                String name = fieldName.getText();
                int age = Integer.parseInt(fieldAge.getText());
                double grade = Double.parseDouble(fieldGrade.getText());

                // Validations
                if (!validateName(name)) {
                    outputArea.setForeground(Color.RED);  // Set text color to red for error
                    outputArea.setText("Error: Name must contain only letters and spaces, and start with a capital letter.");
                } else if (!validateAge(age, 18, 100)) {
                    outputArea.setForeground(Color.RED);  // Set text color to red for error
                    outputArea.setText("Error: Age must be between 18 and 100.");
                } else if (!validateGrade(grade)) {
                    outputArea.setForeground(Color.RED);  // Set text color to red for error
                    outputArea.setText("Error: Grade must be between 0 and 100.");
                } else {
                    manager.updateStudent(id, new Student(name, age, grade, id));
                    outputArea.setForeground(Color.GREEN); // Set text color to green for success
                    outputArea.setText("Student updated successfully!");
                }
            } catch (NumberFormatException ex) {
                outputArea.setForeground(Color.RED);  // Set text color to red for error
                outputArea.setText("Error: Invalid input. Please enter valid data.");
            }
        });

        // Action listener for Display All Students button
        displayButton.addActionListener(e -> {
            outputArea.setText("");  // Clear output area before displaying all students
            for (Student student : manager.displayAllStudents()) {
                outputArea.append(student + "\n");
            }
        });

        // Action listener for Calculate Average Grade button
        averageButton.addActionListener(e -> {
            double avgGrade = manager.calculateAverageGrade();
            outputArea.setForeground(Color.BLACK);  // Set text color to black for average
            outputArea.setText("Average grade: " + avgGrade);
        });

        // Display the frame
        frame.setVisible(true);
    }
}
