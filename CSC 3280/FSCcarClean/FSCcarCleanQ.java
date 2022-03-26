/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCcarCleanQ{
    /*      Variables      */
    // Instance of FSCmember to keep track of the front of the queue
    private FSCmember front;
    // Instance of FSCmember to keep track of the back of the queue
    private FSCmember back;
    // Keep track of the number of customers in the queue
    private int numCustomers;

    /*      Constructor Methods      */
    public FSCcarCleanQ(){
        // Initializes front and back to null to create an empty queue
        front = null;
        back = null;
        // Initializes numCustomers to 0 to indicate that there are no customers to start with
        numCustomers = 0;
    }

    /*
    * enqueue Method - adds a customer to the queue
    * Parameters
    * - FSCmember car: the customer to be added to the queue
    */
    public void enqueue(FSCmember car){
        // If front is null, then the queue is empty
        if(front == null){
            // So we set both front and back to the customer being added
            front = car;
            back = car;
        }
        // Else, there is at least one customer in the queue
        else{
            // Add the customer at the back by changing the back node's next from null to the customer
            back.setNext(car);
            // Set back to the new customer
            back = back.getNext();
        }
        // Increment numCustomers to indicate we have added a customer to the queue
        numCustomers++;
    }

    /*
    * dequeue Method - removes a customer from the queue
    * Output:
    * - FSCmember: returns the first customer in the queue
    */
    public FSCmember dequeue(){
        // Store the customer in a temporary FSCmember
        FSCmember temp = front;
        // Set the front to the next customer
        front = front.getNext();
        // Set the pointer of the customer we are removing to null, so they don't point to the rest of the other line still
        temp.setNext(null);
        // Decrement numCustomers to indicate we have removed a customer from the queue
        numCustomers--;
        // Return the temporary FSCmember holding the customer
        return temp;
    }

    /*
    * peek Method - peeks at the first customer in the queue
    * Output:
    * - FSCmember: returns the first customer in the queue
    */
    public FSCmember peek(){
        // Returns the front of the queue, which is a pointer to the first customer
        return front;
    }

    /*
    * isEmpty Method - returns whether or not the queue is empty
    * Output:
    * - boolean: returns true if the list is empty and false if not
    */
    public boolean isEmpty(){
        // Evaluates the boolean expression front == null (checking if the list is empty) and returns it
        return(front == null);
    }

    /*
    * getLength Method - returns the current length of the queue
    * Output:
    * - int: returns numCustomers, the current length
    */
    public int getLength(){
        // Return numCustomers
        return numCustomers;
    }

    /*
     * clear Method - empties the queue; mainly used just to double check that the queue is empty at the end of the day
     */
    public void clear(){
        // Set front and back to null to empty the queue
        front = null;
        back = null;
        // Set numCustomers to 0 to indicate that there are none left in the queue
        numCustomers = 0;
    }
}