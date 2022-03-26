/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCvoucher{
    /*      Variables      */
    // Stores the time that the customer arrives; final because it is only ever accessed, not altered
    private final int arrivalTime;
    // Stores the student/employee ID number of the customer; final because it is only ever accessed, not altered
    private final int ID;
    // Stores the first name of the customer; final because it is only ever accessed, not altered
    private final String firstName;
    // Stores the first name of the customer; final because it is only ever accessed, not altered
    private final String lastName;
    // Stores the voucher code the customer received; final because it is only ever accessed, not altered
    private final String code;
    // Stores the time that their services were started; final because it is only ever access, not altered
    private final int timeStarted;
    // Stores the time that their services are finished
    private int timeFinished;
    // Stores a pointer to the next voucher in the stack
    private FSCvoucher next;

    /*      Constructor Methods      */
    public FSCvoucher(FSCmember voucherHolder){
        // Method takes in a member and uses all of their information via getters to set the values for the voucher
        arrivalTime = voucherHolder.getArrival();
        ID = voucherHolder.getID();
        firstName = voucherHolder.getFirst();
        lastName = voucherHolder.getLast();
        code = voucherHolder.getCode();
        timeStarted = voucherHolder.getStart();
        timeFinished = timeStarted + voucherHolder.getRemaining();
        next = null;
    }

    /*      Getter Methods      */
    // getNext Method: gets the next voucher in the stack (Linked List)
    public FSCvoucher getNext(){
        return next;
    }
    // getArrival Method: gets the arrival time of the customer
    public int getArrival(){
        return arrivalTime;
    }
    // getStart Method: gets the start time of the customer's service
    public int getStart(){
        return timeStarted;
    }
    // getID Method: gets the student/employee ID number of the customer
    public int getID(){
        return ID;
    }
    // getFirst Method: gets the first name of the customer
    public String getFirst(){
        return firstName;
    }
    // getLast Method: gets the last name of the customer
    public String getLast(){
        return lastName;
    }
    // getCode Method: gets the voucher code assigned to the customer
    public String getCode(){
        return code;
    }
    // getFinish Method: gets the time that the customer's services were finished
    public int getFinish(){
        return timeFinished;
    }

    /*      Setter Methods      */
    // setNext Method: sets the next voucher in the stack
    public void setNext(FSCvoucher nextVoucher){
        next = nextVoucher;
    }
    // setEnd Method: sets the time that the customer's services were finished
    public void setEnd(int endTime){
        timeFinished = endTime;
    }

}