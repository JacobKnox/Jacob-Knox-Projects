/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

// Import all packages in java.util, mainly to use the Scanner class
import java.util.*;

public class FSCcarWash{
    public static void main(String[] args){
        // Create the Scanner input that reads from the user
        Scanner input = new Scanner(System.in);

        /*      Variables      */

        // Stores the maximum length of the wait line
        int maxLine = input.nextInt();
        // Stores the number of days for the simulation
        int numDays = input.nextInt();
        /*
        * Initialize int variables related to each day
        *  - washTime: stores the time it will take to wash a car on that specific day
        *  - waxTime: stores the time it will take to wax a car on that specific day
        *  - vacuumTime: stores the time it will take to vacuum a car on a specific day
        *  - numCustomers: stores the number of customers on that specific day
        *  - time: variable to keep track of the time (in minutes since 10:00 AM) each day
        */
        int washTime, waxTime, vacuumTime, numCustomers, time;
        /*
        * Initialize int variables related to each customer
        *  - serviceTime: the total amount of time it will take to service the customer based on the times of their services that day
        *  - arrivalTime: the time (in minutes since 10:00 AM) that they will be arriving
        *  - id: the customer's student/employee ID number
        */
        int serviceTime, arrivalTime, id;
        /*
        * Initialize int variables related to tracking time
        * - hourNum: used to store the current hour (note: named hourNum due to String variable hour below; implementation explained later)
        * - minute: used to store the current minute (from 0 to 59)
        */
        int hourNum, minute;
        // String for printing the hour due to concatenation of " " and the hour if the hour is less than 10 (only done to match output specifications)
        String hour;
        // String to keep track of whether it is AM or PM, initialized to "AM" because the time starts at 10:00 AM
        String amPM = "AM";
        /*
        * Initialize String variables related to each customer
        * - firstName: stores the first name of the specific customer
        * - lastName: stores the last name of the specific customer
        * - voucherCode: stores the code of the specific customer's voucher
        */
        String firstName, lastName, voucherCode;
        // boolean value to represent whether or not there is someone being serviced currently; intialized to false, because noobdy is being helped at first
        boolean busy = false;
        // An instance of FSCcarCleanQ for keeping track of the line of people expected to arrive on a given day
        FSCcarCleanQ outsideLine = new FSCcarCleanQ();
        // An instance of FSCcarCleanQ for keeping track of the people currently waiting to be serviced
        FSCcarCleanQ waitLine = new FSCcarCleanQ();
        // An instance of FSCmember used to hold the person currently being serviced
        FSCmember holder = null;
        // An instance of FSCvouchers to keep track of the vouchers from the people who have been serviced
        FSCvouchers voucherStack = new FSCvouchers();

        // Print statement welcoming the user to the simulation
        System.out.println("Welcome to the FSC Car Clean Simulator\n");

        // for loop to iterate through the days in the program
        for(int i = 1; i < numDays + 1; i++){
            // Print statement to print the beginning of each day
            System.out.println("**********\nDay " + i + ":\n**********\n");

            // Resets time to 0 for the upcoming while loop
            time = 0;
            // Reads in the wash time (in minutes) for the current day
            washTime = input.nextInt();
            // Reads in the wax time (in minutes) for the current day
            waxTime = input.nextInt();
            // Reads in the vacuum time (in minutes) for the current day
            vacuumTime = input.nextInt();
            // Reads in the number of customers for the current day
            numCustomers = input.nextInt();

            // for loop to read in the information for all of the customers that day
            for(int k = 0; k < numCustomers; k++){
                // Reads in the arrival time of the customer (in minutes after 10:00 AM)
                arrivalTime = input.nextInt();
                // Reads in the student/employee ID number of the customer
                id = input.nextInt();
                // Reads in the first name of the customer
                firstName = input.next();
                // Reads in the last name of the customer
                lastName = input.next();
                // Reads in the voucher code assigned to the customer
                voucherCode = input.next();

                // If the voucher code is only one character long, then they're getting a wash only (unless they're a minion, but that is ignored)
                if(voucherCode.length() == 1){
                    // Assign their total service time as just the wash time
                    serviceTime = washTime;
                }
                // Else, if the voucher code is two characters long, then they're getting a wash and a wax
                else if(voucherCode.length() == 2){
                    // Assign their total service time to the wash time plus the wax time
                    serviceTime = washTime + waxTime;
                }
                // Else, the voucher code is three characters long, then they're getting a wash, wax, and vacuum
                else{
                    // Assign their total service time to the wash time plus the wax time plus the vacuum time
                    serviceTime = washTime + waxTime + vacuumTime;
                }

                // Create a new instance of FSCmember with their information and enqueue it into the outside line
                outsideLine.enqueue(new FSCmember(arrivalTime, id, firstName, lastName, voucherCode, serviceTime));
            }

            // while loop that loops until the time reaches 4:00 PM OR until the waiting line is empty (since all customers waiting at closing time must be served before the day can end)
            while(time != 360 || !waitLine.isEmpty()){
                // time / 60 gives the time rounded (floored) to the nearest hour since 10:00 AM then we add 10 to represent the starting time of 10:00 AM
                hourNum = 10 + ((time / 60) % 12); // I just realized the % 12 is probably redundant, but I'm too afraid to remove it and unknowingly break my entire code
                // If the hour is greater than 12
                if(hourNum > 12){
                    // Need to subtract 12 for formatting, since we aren't using the 24 hour clock
                    hourNum = hourNum - 12;
                }
                // If the hour is less than 10
                if(hourNum < 10){
                    // Add a space in front for formatting
                    hour = " " + hourNum;
                }
                // Else, the hour is greater than or equal to 10
                else{
                    // Add a blank string in front
                    hour = "" + hourNum;
                }
                // Sets the minute to the remainder of the time divided by 60, which will yield 0-59
                minute = time % 60;
                // If the time (in minutes since 10:00 AM) is greater than or equal to 120 (meaning the time is at or past 12:00 PM)
                if(time >= 120){
                    // Set the amPM tracker to "PM"
                    amPM = "PM";
                }
                // If there is currently someone being serviced AND their remaining service time is now 0
                if(holder != null && holder.getRemaining() == 0){
                    // Print their information and wait time
                    System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  The car for " + holder.getFirst() + " " + holder.getLast() + " is now finished.");
                    System.out.println("           Waiting time in line: " + (holder.getStart() - holder.getArrival()) + " minutes");
                    System.out.println("           Service time: " + (time - holder.getStart()) + " minutes");
                    System.out.println("           Total time: " + (time - holder.getArrival()) + " minutes");
                    // If the wait line is not empty
                    if(!waitLine.isEmpty()){
                        // Move someone from the wait line to be serviced
                        holder = waitLine.dequeue();
                        // Set their start time as the current time
                        holder.setStart(time);
                        // Push a new FSSCvoucher (created using the customer now being serviced) onto the voucher stack
                        voucherStack.push(new FSCvoucher(holder));
                        // Print stating that they have begun being serviced
                        System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  " + holder.getFirst() + " " + holder.getLast() + " has now started Class " + holder.getCode() + " service.");
                        // (Redundantly) set busy to true to mark that someone is being serviced
                        busy = true;
                    }
                    // Else, the waitline is empty
                    else{
                        // Set holder to null, since nobody is being serviced now
                        holder = null;
                        // Set busy to false, since nobody is being serviced now
                        busy = false;
                    }
                    // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                    continue;
                }
                // Else, if the outside line is not empty AND it is time for the next person in the outside line to arrive
                else if(!outsideLine.isEmpty() && time == outsideLine.peek().getArrival()){
                    // If their code is "Z" (meaning they are the lowly minion)
                    if(outsideLine.peek().getCode().equals("Z")){
                        // If the voucher stack is empty
                        if(voucherStack.peek() == null){
                            // Print that they found the voucher box empty
                            System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  LOWLY Minion came but found the voucher box empty.");
                        }
                        // Else, there are vouchers in the stack
                        else{
                            // Print that they found the following vouchers:
                            System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  LOWLY Minion came and collected the following vouchers:");
                            // Call printAll method on the voucher stack to print all of the vouchers in the stack
                            voucherStack.printAll();
                        }
                        // Dequeue them from the outside line and discard them
                        outsideLine.dequeue();
                        // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                        continue;
                    }
                    // Else, if the time (in minutes after 10:00 AM) is greater than or equal to 360 (meaning the car wash is closed)
                    else if(time >= 360){
                        // Dequeue the customer from the outside line and discard them
                        outsideLine.dequeue();
                        // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                        continue;
                    }
                    // Else, if the waitline is empty and there is not currently someone being serviced
                    else if(waitLine.isEmpty() && !busy){
                        // Take the customer from the outside line and move them immediately to being serviced
                        holder = outsideLine.dequeue();
                        // Set their start time as the current start time
                        holder.setStart(time);
                        // Push a new FSCvoucher (using the customer's information) onto the voucher stack
                        voucherStack.push(new FSCvoucher(holder));
                        // Print their information
                        System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  " + holder.getFirst() + " " + holder.getLast() + " arrived at the FSC Car Clean and immediately started Class " + holder.getCode() + " service.");
                        // Set busy to true to indicate that someone is now being serviced
                        busy = true;
                        // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                        continue;
                    }
                    // Else, if the wait line is full
                    else if(waitLine.getLength() == maxLine){
                        // Print that they got turned away and left disappointed
                        System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  " + outsideLine.peek().getFirst() + " " + outsideLine.peek().getLast() + " arrived at the FSC Car Clean. Unfortunately, the Wash Queue is full, and the customer left disappointed.");
                        // Dequeue them from the outside line and discard them
                        outsideLine.dequeue();
                        // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                        continue;
                    }
                    // Else, there is no special case to consider and there is room in the waitline
                    else{
                        // Print that they arrived and joined the wait line
                        System.out.println(hour + ":" + String.format("%02d", minute) + " " + amPM + ":  " + outsideLine.peek().getFirst() + " " + outsideLine.peek().getLast() + " arrived at the FSC Car Clean and entered Wash Queue.");
                        // Enqueue them into the wait line
                        waitLine.enqueue(outsideLine.dequeue());
                        // Continue statement without incrementing time to make sure we don't neglect multiple things occuring in the same minute
                        continue;
                    }
                }
                // If, after everything else has been taken care of, there is someone currently being serviced
                if(busy && holder != null){
                    // Decrement their time remaining
                    holder.decrementTime();
                }
                // Increment time
                time++;
            }
            // At the end of the day
            // If the voucher stack is empty
            if(voucherStack.peek() == null){
                // Print that the lowly minion found the voucher box empty
                System.out.println(" 4:00 PM:  LOWLY Minion came but found the voucher box empty.");
            }
            // Else, there are vouchers in the stack
            else{
                // Print that the lowly minion found the following vouchers:
                System.out.println(" 4:00 PM:  LOWLY Minion came and collected the following vouchers:");
                // Call the printAll method on the voucher stack to print all of the vouchers
                voucherStack.printAll();
            }
            // Print two new lines for formatting
            System.out.println("\n");
            // Clear the stacks and queue just to make sure they are ready for the next day
            voucherStack.clear();
            outsideLine.clear();
            waitLine.clear();
            // Reset the amPM tracker to "AM" for the next day
            amPM = "AM";
            // Make sure busy is reset to false for the next day
            busy = false;
        }
        // Close the input Scanner
        input.close();
    }
}