/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package mocsbank;

public class MocsBankAccount{
    /*           Variables           */
    private int accountNumber; // The account number of a specific instance of MocsBankAccount
    private double accountBalance; // The balance of a specific instance of MocsBankAccount
    private static int numAccounts = 0; // The current number of accounts open in the bank
    FSCmember customer; // An instance of FSCmember for each instance of MocsBankAccount

    /*           Setter Methods           */
    /*
    *  setAccNum Method
    *  Parameters:
    *    num - the account number
    */
    public void setAccNum(int num){ // Sets the account number
        accountNumber = num;
    }
    /*
     *  setCustomer Method
     *  Parameters:
     *    accountNumber - the account number
     *    firstName - the first name of the customer
     *    lastName - the last name of the customer
     */
    public void setCustomer(int accountNumber, String firstName, String lastName){ // Creates a new instance of FSCmember stored as "customer"
        customer = new FSCmember(accountNumber, firstName, lastName);
    }
    /*
     *  setBalance Method
     *  Parameters:
     *    balance - the balance of the account
     */
    public void setBalance(double balance){ // Sets the account balance
        accountBalance = balance;
    }

    /*           Getter Methods           */
    public int getAccNum(){ // Gets the account number
        return accountNumber;
    }
    public double getBalance(){ // Gets the account balance
        return accountBalance;
    }
    public static int getNumAccounts(){ // Gets the current number of accounts
        return numAccounts;
    }

    /*           Other Methods           */
    public static void increment(){ // Increments the number of accounts for when one is created
        numAccounts++;
    }
    public static void decrement(){ // Decrements the number of accounts for when one is deleted
        numAccounts--;
    }

    /*           Constructor Method           */
    /*
    *  Parameters:
    *    accNum - the account number of the created account
    *    firstName - the first name of the account holder
    *    lastName - the last name of the account holder
    *    balance - the opening balance of the account
    */
    public MocsBankAccount(int accNum, String firstName, String lastName, double balance){
        setCustomer(accNum, firstName, lastName);
        setAccNum(accNum);
        setBalance(balance);
        customer = new FSCmember(accNum, firstName, lastName); // Creates a new instance of FSCmember and stores it in customer
        increment(); // Calls the increment method, so the number of accounts is increased when an account is opened
    }
}