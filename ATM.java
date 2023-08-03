
    import javax.swing.*;

    public class ATM {
        private BankAccount bankAccount;
        
        public ATM(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }
        
        public void withdraw(double amount) {
            // Validate the amount and check if the balance is sufficient
            if (amount <= 0) {
                showMessage("Invalid amount. Please enter a positive value.");
            } else if (amount > bankAccount.getBalance()) {
                showMessage("Insufficient balance.");
            } else {
                bankAccount.withdraw(amount);
                showMessage(" SUCCESS Withdrawal successful. Current balance: " + bankAccount.getBalance());
            }
        }
        
        public void deposit(double amount) {
            // Validate the amount and perform the deposit
            if (amount <= 0) {
                showMessage("Invalid amount. Please enter a positive value.");
            } else {
                bankAccount.deposit(amount);
                showMessage("Deposit successful. Current balance: " + bankAccount.getBalance());
            }
        }
        
        public double checkBalance() {
            return bankAccount.getBalance();
        }
        
        private void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
      

