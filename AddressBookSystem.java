
import javax.swing.*;
import java.awt.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class AddressBookSystem extends JFrame {
    private AddressBook addressBook;
    private JTextField nameField, phoneField, emailField;
    private JTextArea displayArea;

    public AddressBookSystem() {
        addressBook = new AddressBook();

        setTitle("Address Book System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadContactsFromFile(); // Load contacts from file on startup
        pack();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JPanel displayPanel = new JPanel(new BorderLayout());

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone Number:");
        JLabel emailLabel = new JLabel("Email Address:");

        JButton addButton = new JButton("Add Contact");
        JButton editButton = new JButton("Edit Contact");
        JButton searchButton = new JButton("Search Contact");
        JButton displayButton = new JButton("Display All Contacts");

        displayArea = new JTextArea(15, 30);
        displayArea.setEditable(false);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(searchButton); // Add the "Search Contact" button

        displayPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        displayPanel.add(displayButton, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        searchButton.addActionListener(e -> searchContact());
        displayButton.addActionListener(e -> displayAllContacts());

        getContentPane().add(mainPanel);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Contact newContact = new Contact(name, phone, email);
            addressBook.addContact(newContact);
            displayArea.append("Contact added successfully!\n");
        } else {
            displayArea.append("Invalid input! Please enter all required fields.\n");
        }

        // Clear input fields
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void editContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        Contact foundContact = addressBook.searchContact(name);
        if (foundContact != null) {
            foundContact.setPhoneNumber(phone);
            foundContact.setEmailAddress(email);
            displayArea.append("Contact information updated!\n");
        } else {
            displayArea.append("Contact not found!\n");
        }
    }
    private void searchContact() {
        String name = nameField.getText();
        Contact foundContact = addressBook.searchContact(name);
        if (foundContact != null) {
            displayArea.setText("Contact found:\n");
            displayArea.append("Name: " + foundContact.getName() + "\n");
            displayArea.append("Phone: " + foundContact.getPhoneNumber() + "\n");
            displayArea.append("Email: " + foundContact.getEmailAddress() + "\n");
        } else {
            displayArea.setText("Contact not found!\n");
        }
    }

    private void displayAllContacts() {
        List<Contact> allContacts = addressBook.getAllContacts();
        if (!allContacts.isEmpty()) {
            displayArea.setText("----- All Contacts -----\n");
            for (Contact contact : allContacts) {
                displayArea.append(contact.toString() + "\n");
            }
        } else {
            displayArea.setText("Address book is empty!\n");
        }
    }

    private void loadContactsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contacts.dat"))) {
            addressBook = (AddressBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file doesn't exist or any other error, create a new address book
            addressBook = new AddressBook();
        }
    }

    // Save contacts to file when the application is closed
    private void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.dat"))) {
            oos.writeObject(addressBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddressBookSystem addressBookSystem = new AddressBookSystem();
            addressBookSystem.setVisible(true);
            addressBookSystem.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    addressBookSystem.saveContactsToFile();
                    System.exit(0);
                }
            });
        });
    }
}

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Phone: " + phoneNumber +
                ", Email: " + emailAddress;
    }
}

class AddressBook implements Serializable {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }
}
