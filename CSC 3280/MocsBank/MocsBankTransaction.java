/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package mocsbank;

public class MocsBankTransaction{
    /*           Variables           */
    private String transactionType;
    private int accountNumber1;
    private int accountNumber2;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;
    private static int numTransactions = 0;

    /*           Setter Methods           */
    public void setTransaction(String transaction){ // Sets the type of transaction being made
        transactionType = transaction;
    }
    public void setAccNums(int accNum1, int accNum2){ // Sets the account numbers for the transaction
        accountNumber1 = accNum1;
        accountNumber2 = accNum2;
    }
    public void setAmount(double i){ // Sets the amount of the transaction
        amount = i;
    }
    public void setBalances(double before, double after){ // Sets the before and after balances of the account
        balanceBefore = before;
        balanceAfter = after;
    }

    /*           Getter Methods           */
    public String getTransaction(){ // Gets the type of transaction
        return transactionType;
    }
    public int getAccNum1(){ // Gets the first account number in the transaction
        return accountNumber1;
    }
    public int getAccNum2(){ // Gets the second account number in the transaction
        return accountNumber2;
    }
    public double getAmount(){ // Gets the amount of the transaction
        return amount;
    }
    public double getBefore(){ // Gets the balance before the transaction
        return balanceBefore;
    }
    public double getAfter(){ // Gets the balance after the transaction
        return balanceAfter;
    }
    public static int getTrans(){ // Gets the current number of transactions for the day
        return numTransactions;
    }

    /*           Other Methods           */
    public static void incrementTrans(){ // Increases the number of transactions completed [note: no decrement method, because the number of transactions will never decrease
        numTransactions++;
    }
    public static void resetTrans(){ // Resets the count of transactions to 0 at the end of a day
        numTransactions = 0;
    }

    /*           Constructor Methods           */
    public MocsBankTransaction(String transaction, int num1, int num2, double n, double before, double after){ // Used for creating transfer transactions
        setTransaction(transaction);
        setAmount(n);
        setBalances(before, after);
        setAccNums(num1, num2);
        incrementTrans();
    }
    public MocsBankTransaction(String transaction, int num1, double n, double before, double after){ // Used for creating deposit and withdrawal transactions
        accountNumber1 = num1;
        setTransaction(transaction);
        setAmount(n);
        setBalances(before, after);
        incrementTrans();
    }
    public MocsBankTransaction(String transaction, int num, double n){ // Used for creating transactions when opening and closing an account
        accountNumber1 = num;
        setTransaction(transaction);
        setAmount(n);
        incrementTrans();
    }
}