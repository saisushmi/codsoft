import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentInfoApp extends JFrame {
    private JTextField rollNumberField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField gradeField;
    private JTextArea displayArea;
    private ArrayList<Student> students;

    public StudentInfoApp() {
        // Initialize ArrayList to hold student data
        students = new ArrayList<>();

        // Set up the JFrame
        setTitle("Student Information Application");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel gradeLabel = new JLabel("Grade:");

        rollNumberField = new JTextField(10);
        nameField = new JTextField(20);
        ageField = new JTextField(5);
        gradeField = new JTextField(5);

        JButton addButton = new JButton("Add Student");
        JButton displayButton = new JButton("Display All Students");
        JButton exitButton = new JButton("Exit");

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add components to the JFrame
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(rollNumberLabel, gbc);
        gbc.gridx = 1;
        add(rollNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ageLabel, gbc);
        gbc.gridx = 1;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(gradeLabel, gbc);
        gbc.gridx = 1;
        add(gradeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(addButton, gbc);
        gbc.gridx = 1;
        add(displayButton, gbc);
        gbc.gridx = 2;
        add(exitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void addStudent() {
        String rollNumber = rollNumberField.getText().trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        // Validate input fields
        if (rollNumber.isEmpty() || name.isEmpty() || ageStr.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        double grade;

        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age format. Age should be a positive integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            grade = Double.parseDouble(gradeStr);
            if (grade < 0 || grade > 100) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade format. Grade should be a number between 0 and 100.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(rollNumber, name, age, grade);
        students.add(student);

        rollNumberField.setText("");
        nameField.setText("");
        ageField.setText("");
        gradeField.setText("");
    }

    private void displayAllStudents() {
        if (students.isEmpty()) {
            displayArea.setText("No students found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(student).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentInfoApp().setVisible(true);
            }
        });
    }

    private class Student {
        private String rollNumber;
        private String name;
        private int age;
        private double grade;

        public Student(String rollNumber, String name, int age, double grade) {
            this.rollNumber = rollNumber;
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Roll Number: " + rollNumber + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
        }
    }
}
