/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCvouchers{
    /*      Variables      */
    // Keeps track of the voucher at the top of the stack
    private FSCvoucher top;

    /*      Constructor Methods      */
    public FSCvouchers(){
        // Initializes top to null, making an empty stack
        top = null;
    }

    /*
    * push Method - pushes a voucher onto the stack
    * Parameters:
    * - FSCvoucher voucher: the voucher to be pushed onto the top of the stack
    */
    public void push(FSCvoucher voucher){
        // Sets the vouchers next to the voucher currently on top
        voucher.setNext(top);
        // Sets top to the new voucher
        top = voucher;
    }

    /*
    * peek Method - peeks at the voucher on top of the staack without removing it
    * Output:
    * - FSCvoucher: returns the voucher currently on top of the stack
    */
    public FSCvoucher peek(){
        // Returns top, which points to the voucher at the top of the stack
        return top;
    }

    /*
    * printAll Method - prints all of the vouchers in the stack
    */
    public void printAll(){
        // while loop to traverse the whole stack from top to bottom
        while(top != null){
            // Print statement to print the relevant information
            System.out.println("           " + top.getFirst() + " " + top.getLast() + " (" + top.getID() + ")");
            // Sets top to the next voucher in the stack
            top = top.getNext();
        }
    }

    /*
    * clear Method - empties the stack; mainly used just to double check that the stack is empty at the end of the day
    */
    public void clear(){
        // Resets top to null to empty the list
        top = null;
    }
}