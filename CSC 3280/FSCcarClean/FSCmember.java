/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCmember{
    /*      Variables      */
    // Stores the time that the customer arrives; final because it is only ever accessed, not altered
    private final int arrivalTime;
    // Stores the time that the customer began being serviced
    private int timeStarted;
    // Stores the student/employee ID number of the customer; final because it is only ever accessed, not altered
    private final int ID;
    // Stores the first name of the customer; final because it is only ever accessed, not altered
    private final String firstName;
    // Stores the first name of the customer; final because it is only ever accessed, not altered
    private final String lastName;
    // Stores the voucher code the customer received; final because it is only ever accessed, not altered
    private final String code;
    // Keeps track of how long the customer has left until they are done being serviced
    private int minutesRemaining;
    // Since customers are stored in a Linked List, we must store a pointer to the next customer in the line
    private FSCmember next;

    /* Constructor Methods */
    public FSCmember(int arrival, int id, String first, String last, String voucherCode, int serviceTime){
        arrivalTime = arrival;
        ID = id;
        firstName = first;
        lastName = last;
        code = voucherCode;
        minutesRemaining = serviceTime;
        next = null;
    }

    /*      Getter Methods      */
    // getNext Method: gets the next customer in the line (Linked List)
    public FSCmember getNext(){
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
    // getRemaining Method: gets the remaining time of the customer's service
    public int getRemaining(){
        return minutesRemaining;
    }

    /*      Setter Methods      */
    // setNext Method: sets the next customer in the line (Linked List)
    public void setNext(FSCmember nextCar){
        next = nextCar;
    }
    // setStart Method: sets the start time of the customer's services
    public void setStart(int time){
        timeStarted = time;
    }

    /*      Other Methods      */
    // decrementTime Method: decrements the time remaining for the customer's services
    public void decrementTime(){
        minutesRemaining--;
    }

}