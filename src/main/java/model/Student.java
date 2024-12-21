package model;

public class Student {
    private String id;
    private String name;
    private int age;
    private double grade;

    // Constructor
    public Student(String name, int age, double grade, String id) {
        this.name = capitalizeName(name);  // Ensure name starts with a capital letter
        this.age = age;
        this.grade = grade;
        this.id = id;
    }

    // Method to capitalize the first letter of the name
    private String capitalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    // Getter and Setter for ID
    public String getStudentID() {
        return id;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = capitalizeName(name);  // Ensure the name is always capitalized
    }

    // Getter and Setter for Age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 100) {  // Ensure age is between 18 and 100
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 18 and 100.");
        }
    }

    // Getter and Setter for Grade
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade >= 0 && grade <= 100) {  // Ensure grade is between 0 and 100
            this.grade = grade;
        } else {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
    }

    // Override toString to return student details as a string
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}
