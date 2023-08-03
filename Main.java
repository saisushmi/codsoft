
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000); // Set an initial balance
        ATM atm = new ATM(bankAccount);
        
        JFrame frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));
        
        JTextField amountField = new JTextField(10);
        
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(amountField.getText());
                atm.withdraw(amount);
            }
        });
        
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(amountField.getText());
                atm.deposit(amount);
            }
        });
        
        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double balance = atm.checkBalance();
                showMessage("Current balance: " + balance);
            }
        });
        
        frame.add(new JLabel("ATM Machine"));
        frame.add(amountField);
        frame.add(withdrawButton);
        frame.add(depositButton);
        frame.add(checkBalanceButton);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}


