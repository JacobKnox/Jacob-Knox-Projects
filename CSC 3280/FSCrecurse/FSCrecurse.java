/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

/*           Java Imports           */
import java.util.*; // Imported for the Scanner class

public class FSCrecurse {

    public static void main(String[] args){
        /*           Tools           */
        Scanner input = new Scanner(System.in); // Scanner to read input from the user

        /*           Variables           */
        int numCommands = input.nextInt(); // Stores the number of commands to be run
        String command; // Variable to store each individual command

        /* for loop to loop through the commands */
        for(int i = 0; i < numCommands; i++) {
            command = input.next(); // Gets the next command

            /* switch statement to execute code based on which command is inputted */
            switch (command) {
                case "MocsMath" -> MocsMath(input); // Calls the MocsMath wrapper method
                case "MocsShape" -> MocsShape(input); // Calls the MocsShape wrapper method
                case "MocsGame" -> MocsGame(input); // Calls the MocsGame wrapper method
                case "MocsHop" -> MocsHop(input); // Calls the MocsHop wrapper method
            }
            System.out.println();
        }

        input.close(); // Closes the input Scanner
    }

    /*           Wrapper Methods           */
    /*
    MocsMath Wrapper Method
    Input:
        Scanner input - the Scanner used to read information
     */
    public static void MocsMath(Scanner input){
        int inputNum = input.nextInt(); // Stores the number that we will be performing on
        System.out.println("MocsMath:  " + MocsMath(inputNum)); // Prints the information while calling the actual MocsMath method
    }

    /*
    MocsShape Wrapper Method
    Input:
        Scanner input - the Scanner used to read information
     */
    public static void MocsShape(Scanner input){
        int firstNum = input.nextInt(); // Stores the first number we will be using
        int secondNum = input.nextInt(); // Stores the second number we will be using
        System.out.println("MocsShape:");
        MocsShape(firstNum, secondNum); // Calls the actual MocsShaper method
    }

    /*
    MocsGame Wrapper Method
    Input:
        Scanner input - the Scanner used to read information
     */
    public static void MocsGame(Scanner input){
        int start = input.nextInt(); // Stores the starting value for the game
        if(MocsGame(start)){ // Calls the actual MocsGame method and checks if it returns true
            System.out.println("MocsGame:  Solvable"); // If it returns true, print that the game is solvable
        }
        else{
            System.out.println("MocsGame:  Not Solvable"); // If it returns false, print that it cannot be solved
        }
    }

    /*
    MocsHop Wrapper Method
    Input:
        Scanner input - the Scanner used to read information
     */
    public static void MocsHop(Scanner input){
        /*           Variables           */
        int start = input.nextInt(); // Gets the starting index on the board
        int size = input.nextInt(); // Gets the size of the board
        int[] board = new int[size]; // Creates the board with size size
        boolean[] flags = new boolean[size]; // Array of flags to indicate which places in the board have been visited

        /* for loop to add values to the board */
        for(int i = 0; i < size; i++){
            board[i] = input.nextInt(); // Reads in the values for the board
            flags[i] = false; // Sets all flags to false
        }
        if(MocsHop(start, board, flags)){ // Calls the actual MocsHop method and checks if it returns true
            System.out.println("MocsHop:  Solvable"); // If it returns true, print that the game is solvable
        }
        else{ // Else, it returns false
            System.out.println("MocsHop:  Not Solvable"); // Print that the game is not solvable
        }
    }

    /*            Recursive Methods           */
    /*
    MocsMath Recursive Method
    Input:
        int num - the number we will be operating on
    Output:
        long - a long value that is the sum of all factorials up to and including num
     */
    public static long MocsMath(int num){
        if(num == 0){ // If the number is 0 (base case)
            return 1; // Return 1, because the factorial of 0 is 1
        }
        else{ // Else, we need to compute more
            return factorial(num) + MocsMath(num - 1); // Calls helper method factorial(int num) with num and recursively calls MocsMath(int num) with num - 1
        }
    }

    /*
    MocsShape Recursive Method
    Input:
        int numOne - the starting number of stars
        int numTwo - the ending number of stars
    Output:
        No returns, but prints to the console a partial triangle of stars
     */
    public static void MocsShape(int numOne, int numTwo){
        if(numOne > numTwo){ // If numOne is greater than numTwo (base case)
            return; // Useless return statement simply to indicate that the recursion stops here
        }
        else{ // Else, we still have more rows to make
            /* for loop to print a single row of stars by iteration */
            for(int i = 0; i < numOne; i++){
                System.out.print("* ");
            }
            System.out.println();
            MocsShape(numOne + 1, numTwo); // Recursively calls MocsShape with numOne + 1
            /* another for loop that does the same as the first one, but on the way out of the recursion (reversed) */
            for(int i = 0; i < numOne; i++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    /*
    MocsGame Recursive Method
    Input:
        int start - the starting number for the game
    Output:
        boolean - returns a boolean value to indicate whether winning is possible or not
     */
    public static boolean MocsGame(int start){
        if(start == 42){ // If the starting number is 42, the winning number
            return true; // Then return true, since can won
        }
        if(start < 42){ // If the number is less than 42
            return false; // Return false, because there is no longer any way they can win
        }
        if(start % 2 == 0){ // If the starting number is divisible by 2
            if(MocsGame(start/2)){ // Recursively calls MocsGame with start/2 and check if it is true
                return true; // Return true if it is
            }
        }
        if(start % 3 == 0 || start % 4 == 0){ // If the starting number is divisble by 3 OR 4
            if((start%10)*((start%100)/10) != 0){ // If the product of the last two digits is NOT 0 (0 would result in infinite recursion, causing a StackOverflowError
                if(MocsGame(start - ((start%10)*((start%100)/10)))){ // Recursively calls MocsGame with start minus the product of its last two digits and checks if it is true
                    return true; // Return true if it is
                }
            }
        }
        if(start % 5 == 0){ // If the starting number is divisible by 5
            return MocsGame(start - 42); // Recursively call MocsGame with start - 42 then return what it gives back (true or false)
        }
        return false; // Return false if NONE of the above conditions are met
    }

    /*
    MocsHop Recursive Method
    Input:
        int start - the starting index on the board for the game
        int[] board - the board for the game
        boolean[] flags - array of flags
    Output:
        boolean - returns a boolean value indicating whether or not the hop game is solvable
     */
    public static boolean MocsHop(int start, int[] board, boolean[] flags){
        /*           Variables           */
        int hop = board[start]; // Stores the value in the board at the start index

        // flags[start] = true; // Sets the flag of the current location to true

        if(hop == 0){ // If hop is 0, we've reached the end of the board
            flags[start] = true;
            return true; // Return true
        }
        if(flags[start] || (start - hop < 0 && start + hop > board.length - 1)){ // If the location is already visited OR moving forward or backward would put us out of the board
            return false; // Return false
        }
        flags[start] = true; // Set the flag for the current position to true (needs to be done AFTER the check above)
        if(start - hop > -1){ // If we can move forward
            if(MocsHop(start - hop, board, flags)){ // Recursively call MocsHop with the hop backward and check if it's true
                return true; // Return true if it's true
            }
        }
        if(start + hop < board.length){ // If we can move backward
            return MocsHop(start + hop, board, flags); // Recursively call MocsHop with the hop forward and return what it returns
        }






/* Potentially bugged code. Isolated for now while I work on creating code that may fix the bug.
   Edit: yep, it was definitely bugged. I'll just leave this comment block in, so maybe Dr. Cazalas will see me doing a big dumb haha.

        else if((start - hop >= 0 && hop == board[start - hop]) // if a hop backwards will keep us on the board AND the square that far back is the same as the one we are on
                || // OR
                (start + hop < board.length && hop == board[start + hop]) // if a hop forwards will keep us on the board AND the square that far forward is the same as the one we are on
        ){
            if(start - hop < 0){ // If hopping backwards puts us outside the board (the only way to hop is forward, putting us in an infinite loop
                return false; // Return false
            }
            else if(start + hop > board.length - 1){ // Else if hopping forwards puts us outside the board (the only way to hop is backward, putting us in an infinite loop
                return false; // Return false
            }
            else{ // Otherwise, we can safely hop in the respective direction
                if(start - hop >= 0){ // If hopping back is on the board
                    if(MocsHop(start - hop,board)){ // Recursively call MocsHop with the hop and check if it's true
                        return true; // Return true if it is
                    }
                }
                if(start + hop < board.length){ // If hopping forward is on the board
                    return MocsHop(start + hop, board); // Recursively call MocsHop with the hop and return the return
                }
            }
        }
        else{
            if(start - hop >= 0){ // If a hop backward is still on the board
                if(MocsHop(start - hop,board)){
                    return true; // Recursively call MocsHop with the hop and check if it's true
                }
            }
            if(start + hop < board.length){ // If hopping forward is on the board
                return MocsHop(start + hop, board); // Recursively call MocsHop with the hop and return the return
            }
        }

 */





        return false; // If all else fails, return false
    }

    /*           Helper Methods           */
    /*
    Factorial Recursive (Helper) Method
    Input:
        int num - the number we will be finding the factorial of
    Output:
        long - a long value that is the factorials of num
     */
    public static long factorial(int num){
        if(num == 0){ // If num is 0 (base case)
            return 1; // Return 1 (since the factorial of 0 is 1)
        }
        else{ // Else, we still need to calculate
            return num * factorial(num - 1); // Recursively call factorial with num - 1 using the mathematical rule that n! = n * (n-1)!
        }
    }
}