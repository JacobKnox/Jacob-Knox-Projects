/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package mocsbank;

/*           Java Imports           */
import java.io.File; // Imports the file tools, so the program can create and locate files
import java.io.PrintWriter; // Imports the printwriter tools, so the program can write to an output file
import java.util.Scanner; // Imports the scanner tools, so the program can read from an input file or user input

public class MocsBank{
    /*           Bank Transaction Methods           */

    /*
    *  openAccount Method
    *  Purpose: To write all the information for a newly opened account into the output file.
    *  Parameters:
    *    output - the PrintWriter to write to the output file
    *    account - the newly created account
    */
    public static void openAccount(PrintWriter output, MocsBankAccount account){ // Method for opening a new account
        output.println("\tNew Account Opened");
        output.println("\tAccount:          " + account.getAccNum());
        output.println("\tName:             " + account.customer.getFirstName() + " " + account.customer.getLastName());
        output.println("\tOpening Balance:  " + String.format("%.2f",account.getBalance()));
    }

    /*
     *  printBalance Method
     *  Purpose: To write the current balance on the selected account to the output file.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    account - the selected account
     */
    public static void printBalance(PrintWriter output, MocsBankAccount account){ // Method for getting the current balance of an account
        output.println("\tAccount:          " + account.getAccNum());
        output.println("\tName:             " + account.customer.getFirstName() + " " + account.customer.getLastName());
        output.println("\tCurrent Balance:  " + String.format("%.2f",account.getBalance()));
    }

    /*
     *  deposit Method
     *  Purpose: To deposit a certain amount into a selected account and write the details to the output file.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    account - the selected account
     *    amount - the amount of the deposit
     *  Returns:
     *    transaction - a newly created instance of MocsBankTransaction to be stored in the transactions array
     */
    public static MocsBankTransaction deposit(PrintWriter output, MocsBankAccount account, double amount){ // Method for depositing money into an account
        MocsBankTransaction transaction = new MocsBankTransaction("DEPOSIT", account.getAccNum(), amount, account.getBalance(), account.getBalance() + amount);
        account.setBalance(account.getBalance() + amount);
        output.println("\tAccount:         " + account.getAccNum());
        output.println("\tName:            " + account.customer.getFirstName() + " " + account.customer.getLastName());
        output.println("\tDeposit Amount:  " + String.format("%.2f",amount));
        output.println("\tNew Balance:     " + String.format("%.2f",account.getBalance()));
        return transaction;
    }

    /*
     *  withdraw Method
     *  Purpose: To withdraw a certain amount from a selected account and write the details to the output file.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    account - the selected account
     *    amount - the amount of the withdrawal
     *  Returns:
     *    transaction - a newly created instance of MocsBankTransaction to be stored in the transactions array
     */
    public static MocsBankTransaction withdraw(PrintWriter output, double amount, MocsBankAccount account){ // Method for withdrawing money from an account
        MocsBankTransaction transaction = new MocsBankTransaction("WITHDRAW", account.getAccNum(), amount, account.getBalance(), account.getBalance() - amount);
        account.setBalance(account.getBalance() - amount);
        output.println("\tAccount:            " + account.getAccNum());
        output.println("\tName:               " + account.customer.getFirstName() + " " + account.customer.getLastName());
        output.println("\tWithdrawal Amount:  " + String.format("%.2f",amount));
        output.println("\tNew Balance:        " + String.format("%.2f",account.getBalance()));
        return transaction;
    }

    /*
     *  transfer Method
     *  Purpose: To transfer a certain amount into a selected account from another account and write the details to the output file.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    account1 - the account from which the money will be transferred
     *    account2 - the account to which the money will be transferred
     *    amount - the amount of the transfer
     *  Returns:
     *    transaction - a newly created instance of MocsBankTransaction to be stored in the transactions array
     */
    public static MocsBankTransaction transfer(PrintWriter output, double amount, MocsBankAccount account1, MocsBankAccount account2){ // Method for transferring money from one account to another
        MocsBankTransaction transaction = new MocsBankTransaction("TRANSFER", account1.getAccNum(), account2.getAccNum(), amount, account1.getBalance(), account1.getBalance() - amount);
        account1.setBalance(account1.getBalance() - amount);
        account2.setBalance(account2.getBalance() + amount);
        output.println("\tAccount (from):   " + account1.getAccNum());
        output.println("\tAccount (to):     " + account2.getAccNum());
        output.println("\tTransfer Amount:  " + String.format("%.2f",amount));
        return transaction;
    }

    /*
     *  deposit Method
     *  Purpose: To deposit a certain amount into a selected account and write the details to the output file.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    accounts[] - the array of current accounts
     *    index - the index at which the account to be deleted is at
     *  Returns:
     *    accounts[] - the new version of the accounts array without the deleted account
     */
    public static MocsBankAccount[] closeAccount(PrintWriter output, int index, MocsBankAccount[] accounts){ // Method for closing an account
        output.println("\tAccount Has Been Closed");
        output.println("\tAccount:          " + accounts[index].getAccNum());
        output.println("\tName:             " + accounts[index].customer.getFirstName() + " " + accounts[index].customer.getLastName());
        output.println("\tClosing Balance:  " + String.format("%.2f",accounts[index].getBalance()));
        while(index < accounts.length - 1){
            accounts[index] = accounts[index + 1];
            index++;
        }
        MocsBankAccount.decrement();
        return accounts;
    }

    /*
     *  transactionReport Method
     *  Purpose: To deliver a report of all applicable transactions for the day thus far.
     *  Parameters:
     *    output - the PrintWriter to write to the output file
     *    transactions[] - the array of MocsBankTransaction instances
     *    day - the current day expressed as an integer
     */
    public static void transactionReport(PrintWriter output, MocsBankTransaction[] transactions, int day){ // Method for returning the transaction history of the current day
        output.println("MocsBank Transaction Report");
        output.println("\tDay:                " + day);
        output.println("\t# of Transactions:  " + MocsBankTransaction.getTrans());
        for(int i = 1; i <= MocsBankTransaction.getTrans(); i++){
            output.println("\t-----------");
            output.println("\tTransaction #" + i);
            output.println("\t" + transactions[i - 1].getTransaction());
            switch (transactions[i - 1].getTransaction()) { // Checks which transaction is stored within the transactions array at index i-1
                case "OPENACCOUNT" -> { // If the transaction is open account
                    output.println("\t\tAccount:          " + transactions[i - 1].getAccNum1());
                    output.println("\t\tOpening Balance:  " + String.format("%.2f", transactions[i - 1].getAmount()));
                }
                case "DEPOSIT" -> { // If the transaction is deposit
                    output.println("\t\tAccount:          " + transactions[i - 1].getAccNum1());
                    output.println("\t\tOld Balance:      " + String.format("%.2f", transactions[i - 1].getBefore()));
                    output.println("\t\tDeposit Amount:   " + String.format("%.2f", transactions[i - 1].getAmount()));
                    output.println("\t\tNew Balance:      " + String.format("%.2f", transactions[i - 1].getAfter()));
                }
                case "WITHDRAW" -> { // If the transaction is withdraw
                    output.println("\t\tAccount:          " + transactions[i - 1].getAccNum1());
                    output.println("\t\tOld Balance:      " + String.format("%.2f", transactions[i - 1].getBefore()));
                    output.println("\t\tWithdraw Amount:  " + String.format("%.2f", transactions[i - 1].getAmount()));
                    output.println("\t\tNew Balance:      " + String.format("%.2f", transactions[i - 1].getAfter()));
                }
                case "TRANSFER" -> { // If the transaction is transfer
                    output.println("\t\tAccount 1:        " + transactions[i - 1].getAccNum1());
                    output.println("\t\tAccount 2:        " + transactions[i - 1].getAccNum2());
                    output.println("\t\tOld Balance:      " + String.format("%.2f", transactions[i - 1].getBefore()));
                    output.println("\t\tTransfer Amount:  " + String.format("%.2f", transactions[i - 1].getAmount()));
                    output.println("\t\tNew Balance:      " + String.format("%.2f", transactions[i - 1].getAfter()));
                }
                case "CLOSEACCOUNT" -> { // If the transaction is close account
                    output.println("\t\tAccount:          " + transactions[i - 1].getAccNum1());
                    output.println("\t\tAmount returned:  " + String.format("%.2f", transactions[i - 1].getAmount()));
                }
                default -> { // Default if none of the above are true; empty, because it does nothing and should never happen
                }
            }
        }
    }

    /*           Other Methods           */
    /*
    *  binarySearch Method
    *  Parameters:
    *    accounts[] - the array of current accounts
    *    accountNum - the account number of the account to search for
    *  Returns:
    *    mid - the index of the account, if it is found
    *    -1 - returns the value -1 if the account number is not found
    */
    public static int binarySearch(MocsBankAccount[] accounts, int accountNum){ // Binary search method; reduces redundancy in code
        int low = 0;
        int mid;
        int high = MocsBankAccount.getNumAccounts() - 1;
        while(low <= high){
            mid = (high + low) / 2;
            if(accounts[mid].getAccNum() == accountNum){
                return mid;
            }
            else if(accounts[mid].getAccNum() > accountNum){
                high = mid - 1;
            }
            else{
                low = mid + 1;
            }
        }
        return -1;
    }

    /*           Main Code/Method          */
    public static void main(String[] args) throws Exception{
        /*           Files           */
        File inputFile = new File("src/mocsbank/MocsBank.in"); // Input file
        File outputFile = new File("src/mocsbank/MocsBank.out"); // Output file

        /*           Tools           */
        Scanner input = new Scanner(inputFile); // Scanner for reading input
        PrintWriter output = new PrintWriter(outputFile); // PrintWriter for writing to the output file

        /*           Variables           */
        String command; // Stores which bank command to execute
        int accountNum; // Will also be used for areas requiring ID or account number since they are the same number
        int accountNum2; // Used to store the second account number is a transfer command
        int maxAccounts = input.nextInt(); // The max number of accounts the bank can have open at any given time
        int maxTransactions = input.nextInt(); // The max number of transactions per day permitted
        int numDays = input.nextInt(); // The number of days that will be simulated
        int numTransactions; // The number of transactions in a given day
        int index; // Index of the located number in the array; -1 if not found
        int index2; // Used as a second index variable for transfers
        double transactionAmount; // Stores the value of any transaction (deposit, withdraw, transfer)
        MocsBankAccount[] accounts = new MocsBankAccount[maxAccounts]; // Array of MocsBankAccount instances to store all current accounts
        MocsBankTransaction[] transactions = new MocsBankTransaction[maxTransactions]; // Array of MocsBankTransaction instance to store all applicable transactions

        for(int i = 1; i <= numDays; i++){ // Outer for loop to run through all the days
            output.println("***************************************");
            output.println("Welcome to MocsBank Day #" + i);
            output.println("***************************************");
            numTransactions = input.nextInt();
            for(int j = 0; j < numTransactions; j++){ // Inner for loop to run through all transactions for the day
                command = input.next();
                output.print("\n");
                if(!command.equals("TRANSACTIONREPORT") || MocsBankTransaction.getTrans() == 0){
                    output.println(command + ":");
                }
                switch (command) { // Switch to check the command
                    case "OPENACCOUNT": // If the command is to open an account
                        accountNum = input.nextInt();
                        index = -1;
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            accounts[0] = new MocsBankAccount(accountNum, input.next(), input.next(), input.nextDouble());
                            openAccount(output, accounts[0]);
                            index = 0;
                        } else if (accountNum < accounts[0].getAccNum()) {
                            if (MocsBankAccount.getNumAccounts() - 1 + 1 >= 0){
                                System.arraycopy(accounts, 0, accounts, 1, MocsBankAccount.getNumAccounts() - 1 + 1);
                            }
                            accounts[0] = new MocsBankAccount(accountNum, input.next(), input.next(), input.nextDouble());
                            openAccount(output, accounts[0]);
                            index = 0;
                        } else if (accountNum > accounts[MocsBankAccount.getNumAccounts() - 1].getAccNum()) {
                            accounts[MocsBankAccount.getNumAccounts()] = new MocsBankAccount(accountNum, input.next(), input.next(), input.nextDouble());
                            openAccount(output, accounts[MocsBankAccount.getNumAccounts() - 1]);
                            index = MocsBankAccount.getNumAccounts() - 1;
                        } else {
                            for (int z = 0; z < MocsBankAccount.getNumAccounts(); z++) {
                                if (accounts[z].getAccNum() < accountNum && accounts[z + 1].getAccNum() > accountNum) {
                                    if (MocsBankAccount.getNumAccounts() - z >= 0){
                                        System.arraycopy(accounts, z + 1, accounts, z + 1 + 1, MocsBankAccount.getNumAccounts() - z);
                                    }
                                    accounts[z + 1] = new MocsBankAccount(accountNum, input.next(), input.next(), input.nextDouble());
                                    openAccount(output, accounts[z + 1]);
                                    index = z + 1;
                                    break;
                                }
                            }
                        }
                        transactions[MocsBankTransaction.getTrans()] = new MocsBankTransaction(command, accountNum, accounts[index].getBalance());
                        break;
                    case "PRINTBALANCE": // If the command is to print the balance of an account
                        accountNum = input.nextInt();
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            output.println("\tError: cannot print balance. There are no open accounts in MocsBank.");
                        } else {
                            index = binarySearch(accounts, accountNum);
                            if (index > -1) {
                                printBalance(output, accounts[index]);
                            } else {
                                output.println("\tError: cannot print balance. Account #" + accountNum + " was not found in the system.");
                            }
                        }
                        break;
                    case "DEPOSIT": // If the command is to deposit into an account
                        accountNum = input.nextInt();
                        transactionAmount = input.nextDouble();
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            output.println("\tError: cannot make deposit. There are no open accounts in MocsBank.");
                        } else {
                            index = binarySearch(accounts, accountNum);
                            if (index > -1) {
                                transactions[MocsBankTransaction.getTrans()] = deposit(output, accounts[index], transactionAmount);
                            } else {
                                output.println("\tError: cannot make deposit. Account #" + accountNum + " was not found in the system.");
                            }
                        }
                        break;
                    case "WITHDRAW": // If the command is to withdraw from an account
                        accountNum = input.nextInt();
                        transactionAmount = input.nextDouble();
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            output.println("\tError: cannot make withdrawal. There are no open accounts in MocsBank.");
                        } else {
                            index = binarySearch(accounts, accountNum);
                            if (index > -1) {
                                if (accounts[index].getBalance() > transactionAmount) {
                                    transactions[MocsBankTransaction.getTrans()] = withdraw(output, transactionAmount, accounts[index]);
                                } else {
                                    output.println("\tError: Insufficient funds.");
                                }
                            } else {
                                output.println("\tError: cannot make withdrawal. Account #" + accountNum + " was not found in the system.");
                            }
                        }
                        break;
                    case "TRANSFER": // If the command is to transfer from one account to another
                        accountNum = input.nextInt();
                        accountNum2 = input.nextInt();
                        transactionAmount = input.nextDouble();
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            output.println("\tError: cannot make transfer. There are no open accounts in MocsBank.");
                        } else {
                            index = binarySearch(accounts, accountNum);
                            index2 = binarySearch(accounts, accountNum2);
                            if ((index > -1) && (index2 > -1)) {
                                if (accounts[index].getBalance() > transactionAmount) {
                                    transactions[MocsBankTransaction.getTrans()] = transfer(output, transactionAmount, accounts[index], accounts[index2]);
                                    transactions[MocsBankTransaction.getTrans()] = new MocsBankTransaction("TRANSFER", accounts[index].getAccNum(), accounts[index2].getAccNum(), transactionAmount, accounts[index2].getBalance() - transactionAmount, accounts[index2].getBalance());
                                } else {
                                    output.println("\tError: Insufficient funds.");
                                }
                            } else {
                                output.println("\tError: cannot make transfer. One (or more) of the accounts is not in the system.");
                            }
                        }
                        break;
                    case "CLOSEACCOUNT": // If the command is to close an account
                        accountNum = input.nextInt();
                        if (MocsBankAccount.getNumAccounts() == 0) {
                            output.println("\tError: cannot close account. There are no open accounts in MocsBank.");
                        } else {
                            index = binarySearch(accounts, accountNum);
                            if (index > -1) {
                                transactions[MocsBankTransaction.getTrans()] = new MocsBankTransaction(command, accountNum, accounts[index].getBalance());
                                closeAccount(output, index, accounts);
                            } else {
                                output.println("\tError: cannot close account. Account #" + accountNum + " was not found in the system.");
                            }
                        }
                        break;
                    case "TRANSACTIONREPORT": // If the command is to print a transaction report
                        if (MocsBankTransaction.getTrans() == 0) {
                            output.println("\tError: cannot print Transaction Report. There are no transactions currently in MocsBank.");
                        } else {
                            transactionReport(output, transactions, i);
                        }
                        break;
                }
            }
            MocsBankTransaction.resetTrans(); // Resets the number of transactions, so the new day can start at 0
            output.println("");
        }
        input.close();
        output.close();
    }
}