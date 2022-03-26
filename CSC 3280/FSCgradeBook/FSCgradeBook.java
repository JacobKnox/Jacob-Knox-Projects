/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package fscgradebook;

/*           Java Tools           */
import java.util.Scanner; // Scanner to read inputs
import java.io.PrintWriter; // PrintWriter to write to a file
import java.io.File; // File to create and manage files

public class FSCgradeBook{

    /*           printStudentInfo Method           */
    /*
    * Inputs:
    *   Student student - an instance of a Student node; the specific student whose information the method will be printing
    *   PrintWriter output - the PrintWriter to write to the output file
    */
    public static void printStudentInfo(Student student, PrintWriter output){
        output.println("\t\tExam 1:       " + student.getExam(0));
        output.println("\t\tExam 2:       " + student.getExam(1));
        output.println("\t\tFinal Exam:   " + student.getExam(2));
        output.println("\t\tFinal Grade:  " + String.format("%.2f", student.getFinal()));
        output.println("\t\tLetter Grade: " + student.getLetter());
    }

    /*           addRecord Method           */
    /*
    * Inputs:
    *   Scanner input - the scanner to receive input from the file
    *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
    *   PrintWriter output - the PrintWriter to write to the output file
    */
    public static void addRecord(Scanner input, FSCcourseRoster[] courses, PrintWriter output){
        /*           Method Variables           */
        String course = input.next(); // Gets the course in which we will be adding the student
        int id = input.nextInt(); // Gets the ID of the new student
        String first = input.next(); // Gets the first name of the new student
        String last = input.next(); // Gets the last name of the new student
        int exam1 = input.nextInt(); // Gets the first exam grade of the new student
        int exam2 = input.nextInt(); // Gets the second exam grade of the new student
        int exam3 = input.nextInt(); // Gets the third exam grade of the new student
        Student newStudent = new Student(course, id, first, last, exam1, exam2, exam3); // Creates the new student as a new Student instance using the constructor in Student.java

        // For loop to loop through the instances of FSCcourseRoster in the array
        for(int i = 0; i < courses.length; i++) {
            // If the course number of the course at the current index is equal to the one we are looking for
            if (courses[i].getCourseNum().equals(course)) {
                // Call the insert method in FSCcourseRoster to add the new student to the linked list
                courses[i].insert(newStudent);
                // Print proper output to the file
                output.println("\t" + newStudent.getFirst() + " " + newStudent.getLast() + " (ID# " + newStudent.getID() + ") has been added to " + newStudent.getCourse() + ".");
                output.println("\tFinal Grade: " + String.format("%.2f", newStudent.getFinal()) + " (" + newStudent.getLetter() + ").");
                // Break out of the for loop
                break;
            }
            // If we loop through the whole array and the last course's number is not the one we're looking for
            else if(i == courses.length - 1 && !courses[i].getCourseNum().equals(course)){
                // Print an error message to the file
                output.println("\tERROR: cannot add student. Course \"" + course + "\" does not exist.");
            }
        }
    }

    /*           deleteRecord Method           */
    /*
    * Input:
    *   Scanner input - the scanner to receive input from the file
    *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
    *   PrintWriter output - the PrintWriter to write to the output file
    */
    public static void deleteRecord(Scanner input, FSCcourseRoster[] courses, PrintWriter output){
        /*           Method Variables           */
        int studentID = input.nextInt(); // Gets the ID of the student we want to delete
        Student searchedStudent; // New instance of Student to store the data of the student temporarily before we delete them
        boolean flagOne; // Used to check if the student was deleted successfully from the course
        boolean flagTwo = false; // Used to indicate whether the student was found and deleted in ANY courses; initialized to false

        // For loop to loop through each course in the array of FSCcourseRoster instances
        for (FSCcourseRoster cours : courses) {
            // Call searchByID method to find the student in the class; returns the student if they're in the course or null if they are not
            searchedStudent = cours.searchByID(studentID);
            // Call the delete method to delete the found student; returns true if they were found and deleted, returns false if not
            flagOne = cours.delete(studentID);
            // Check if the student was deleted successfully
            if (flagOne) {
                // If they were, change flagTwo to true to indicate that they were found in at least one course
                flagTwo = true;

                /*
                * An issue I encountered while programming that I left a comment about (can see implementation of solution above):
                *   Issue to solve - get student's information BEFORE deleting them from list OR find a way to access student before deleting them
                *   Solution - use searchByID method when it is created to get the student prior to deletion
                */

                // Print proper information to the file
                output.println("\t" + searchedStudent.getFirst() + " " + searchedStudent.getLast() + " (ID# " + searchedStudent.getID() + ") has been deleted from " + searchedStudent.getCourse() + ".");
            }
        }
        // Once we exit the loop, check to see if the student was found in any courses
        if(!flagTwo){
            // If they were not, print an error to the output file
            output.println("\tERROR: cannot delete student. ID# " + studentID + " does not exist.");
        }
    }

    /*           searchByID Method           */
    /*
     * Input:
     *   Scanner input - the scanner to receive input from the file
     *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
     *   PrintWriter output - the PrintWriter to write to the output file
     */
    public static void searchByID(Scanner input, FSCcourseRoster[] courses, PrintWriter output){
        /*           Method Variables           */
        Student searchedStudent; // A new instance of Student to store the student's information
        int studentID = input.nextInt(); // Gets the ID of the student we are looking for
        boolean flag = false; // Flag to check if the student is found in at least one course; initialized to false

        // For loop to loop through the FSCcourseRoster instances in the array
        for (FSCcourseRoster cours : courses) {
            // Call searchByID and store the returned student (null if not found) into the searchedStudent variable
            searchedStudent = cours.searchByID(studentID);
            // If searchedStudent is not null, meaning a student was found in the particular course
            if (searchedStudent != null) {
                // If this is the first time that the student is found (flag is still false)
                if (!flag) {
                    // Print a line stating whose records are following
                    output.println("Student Record for " + searchedStudent.getFirst() + " " + searchedStudent.getLast() + " (ID# " + searchedStudent.getID() + "):");
                }
                // Print the specific course
                output.println("\tCourse: " + cours.getCourseNum());
                // Call printStudentInfo to print the student's information for that course
                printStudentInfo(searchedStudent, output);
                // Set the flag to true to indicate the student was found in at least one course
                flag = true;
            }
        }
        // If the student was not found in any courses
        if(!flag){
            // Print an error to the file
            output.println("\tERROR: there is no record for student ID# " + studentID + ".");
        }
    }

    /*           searchByName Method           */
    /*
     * Input:
     *   Scanner input - the scanner to receive input from the file
     *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
     *   PrintWriter output - the PrintWriter to write to the output file
     */
    public static void searchByName(Scanner input, PrintWriter output, FSCcourseRoster[] courses){
        /*           Method Variables           */
        Student searchedStudent; // Student object to store the student when we find them
        String name = input.next() + " " + input.next(); // Gets the name of the student we are looking for and concatenates the first and last names
        boolean flag = false; // Flag to indicate whether or not the student was found; initialized to false

        // For loop to loop through each instance of FSCcourseRoster in the array
        for (FSCcourseRoster cours : courses) {
            // Call the searchByName method in FSCcourseRoster and store the result in searchedStudent
            searchedStudent = cours.searchByName(name);
            // If the student was found
            if (searchedStudent != null) {
                // If the student has not been found in any other courses
                if (!flag) {
                    // Print whose information we are printing
                    output.println("Student Record for " + searchedStudent.getFirst() + " " + searchedStudent.getLast() + " (ID# " + searchedStudent.getID() + "):");
                }
                // Print the course they were found in
                output.println("\tCourse: " + cours.getCourseNum());
                // Call printStudentInfo method to print the student's information for that course
                printStudentInfo(searchedStudent, output);
                // Set flag to true to indicate that we found the student in at least one course
                flag = true;
            }
        }
        // If we exit the for loop and the flag is still false, we did not find the student
        if(!flag){
            // Print an error message to the file
            output.println("\tERROR: there is no record for student \"" + name + "\".");
        }
    }

    /*           displayStats Method           */
    /*
     * Input:
     *   Scanner input - the scanner to receive input from the file
     *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
     *   PrintWriter output - the PrintWriter to write to the output file
     */
    public static void displayStats(Scanner input, PrintWriter output, FSCcourseRoster[] courses){
        /*           Method Variables           */
        String course = input.next(); // Gets the course whose stats we are printing
        boolean flag = false; // Flag to indicate whether or not we found the course we're looking for; initialized to false
        int studentCount = 0; // Variable to keep track of the number of students total; initialized to 0
        double runningTotal = 0, average, highest = 0, lowest = 100; // Variables for the running total of final grades, the average final grade, and the highest and lowest final grades
        int aTotal = 0, bTotal = 0, cTotal = 0, dTotal = 0, fTotal = 0; // Variables to keep track of the total number of each letter grade
        double aPercent, bPercent, cPercent, dPercent, fPercent; // Variables for storing the percentage of the total students who earned each letter grade
        Student helpPtr; // A helper variable to travers the lists without losing them

        // Print which course we are looking at
        output.println(" (" + course + ")");
        // If we are looking at all courses
        if(course.equals("ALL")){
            // The flag will automatically be true then
            flag = true;
            // For each course in the array
            for (FSCcourseRoster cours : courses) {
                // Set the helpPtr variable to the first student
                helpPtr = cours.getHead();
                // While the helpPtr is not pointing at a null value
                while (helpPtr != null) {
                    // Increase the student count
                    studentCount++;
                    // Add the student's final grade to the running total
                    runningTotal = runningTotal + helpPtr.getFinal();
                    // Switch statement to check which letter grade they got; increments respective total
                    switch (helpPtr.getLetter()) {
                        case 'A' -> aTotal++;
                        case 'B' -> bTotal++;
                        case 'C' -> cTotal++;
                        case 'D' -> dTotal++;
                        case 'F' -> fTotal++;
                    }
                    // If their final grade is greater than the current highest
                    if (helpPtr.getFinal() > highest) {
                        // Set highest to their grade
                        highest = helpPtr.getFinal();
                    }
                    // If their final grade is lower than the current lowest
                    if (helpPtr.getFinal() < lowest) {
                        // Set lowest to their grade
                        lowest = helpPtr.getFinal();
                    }
                    // Traverse the list so we are not stuck in an infinite loop
                    helpPtr = helpPtr.getNext();
                }
            }
            // Print that we are showing results for all courses
            output.println("Statistical Results for All Courses:");
        }
        // Else, we are looking for a specific course
        else{
            // For each course in the array
            for (FSCcourseRoster cours : courses) {
                // If the current course is the one we are looking for
                if ((cours.getCourseNum()).equals(course)) {
                    // Set helpPtr to the first student in that course
                    helpPtr = cours.getHead();
                    // While helpPtr is not pointing at a null value
                    while (helpPtr != null) {
                        // Increase student count
                        studentCount++;
                        // Add their final score to the running total
                        runningTotal = runningTotal + helpPtr.getFinal();
                        // Switch statement to check what letter grade they got and increment the respective total
                        switch (helpPtr.getLetter()) {
                            case 'A' -> aTotal++;
                            case 'B' -> bTotal++;
                            case 'C' -> cTotal++;
                            case 'D' -> dTotal++;
                            case 'F' -> fTotal++;
                        }
                        // If their grade is higher than the highest
                        if (helpPtr.getFinal() > highest) {
                            // Set highest equal to their grade
                            highest = helpPtr.getFinal();
                        }
                        // If their grade is lower than the lowest
                        if (helpPtr.getFinal() < lowest) {
                            // Set lowest equal to their grade
                            lowest = helpPtr.getFinal();
                        }
                        // Traverse the lsit so we are not stuck in an infinite loop
                        helpPtr = helpPtr.getNext();
                    }
                    // Print which course we are displaying
                    output.println("Statistical Results of " + cours.getCourseNum() + ":");
                    // Set the flag to true, indicating we have found the course
                    flag = true;
                    // Break out of the for loop
                    break;
                }
            }
        }
        // If the flag is still set to false, the course does not exist
        if(!flag){
            // Print an error message to the file
            output.println("\tERROR: cannot display statistics. Course \"" + course + "\" does not exist.");
        }
        // Else, we found the course
        else{
            // If there are no students
            if(studentCount == 0){
                // Set everything to 0
                average = 0;
                aPercent = 0;
                bPercent = 0;
                cPercent = 0;
                dPercent = 0;
                fPercent = 0;
                highest = 0;
                lowest = 0;
            }
            // Else, there is at least one student
            else{
                // Calculate the average
                average = runningTotal / studentCount;
                // Calculate the percentages of each letter grade
                aPercent = (float) aTotal * 100 / studentCount;
                bPercent = (float) bTotal * 100 / studentCount;
                cPercent = (float) cTotal * 100 / studentCount;
                dPercent = (float) dTotal * 100 / studentCount;
                fPercent = (float) fTotal * 100 / studentCount;
            }
            // Print all of the statistical information found above
            output.println("\tTotal number of student records: " + studentCount);
            output.println("\tAverage Score: " + String.format("%.2f", average));
            output.println("\tHighest Score: " + String.format("%.2f", highest));
            output.println("\tLowest Score:  " + String.format("%.2f", lowest));
            output.println("\tTotal 'A' Grades: " + aTotal + " (" + String.format("%.2f", aPercent) + "% of class)");
            output.println("\tTotal 'B' Grades: " + bTotal + " (" + String.format("%.2f", bPercent) + "% of class)");
            output.println("\tTotal 'C' Grades: " + cTotal + " (" + String.format("%.2f", cPercent) + "% of class)");
            output.println("\tTotal 'D' Grades: " + dTotal + " (" + String.format("%.2f", dPercent) + "% of class)");
            output.println("\tTotal 'F' Grades: " + fTotal + " (" + String.format("%.2f", fPercent) + "% of class)");
        }
    }

    /*           displayStudents Method           */
    /*
     * Input:
     *   Scanner input - the scanner to receive input from the file
     *   FSCcourseRoster[] courses - the array of FSCcourseRoster instances
     *   PrintWriter output - the PrintWriter to write to the output file
     */
    public static void displayStudents(Scanner input, PrintWriter output, FSCcourseRoster[] courses){
        /*           Method Variables           */
        String course = input.next(); // Gets the course we are looking for
        Student helpPtr; // A helper variable to traverse through the list and make sure we don't delete it

        // Print the course we are looking for
        output.println(" (" + course + ")");
        // If there are no students
        if(Student.getNumStudents() == 0){
            // Print an error message to the file
            output.println("\tERROR: there are no students currently in the system.");
        }
        // Else, we have at least one student
        else{
            // If we are looking for all courses
            if(course.equals("ALL")){
                // For loop to loop through the courses in the array
                for (FSCcourseRoster cours : courses) {
                    // Set the helpPtr to the first student in the list
                    helpPtr = cours.getHead();
                    // If there are students in the list
                    if(cours.getHead() != null){
                        // Print which course roster we are dispalying
                        output.println("Course Roster for " + cours.getCourseNum() + ":");
                    }
                    // While helpPtr is not pointing to a null value
                    while (helpPtr != null) {
                        // Print the student's name and ID
                        output.println("\t" + helpPtr.getFirst() + " " + helpPtr.getLast() + " (ID# " + helpPtr.getID() + "):");
                        // Call the printStudentInfo method to print the rest of their information
                        printStudentInfo(helpPtr, output);
                        // Traverse the list so we are not stuck in an infinite loop
                        helpPtr = helpPtr.getNext();
                    }
                }
            }
            // Else, we are looking for a specific course
            else{
                // For loop to loop through the courses in the array
                for (FSCcourseRoster cours : courses) {
                    // If the current course is the one we are looking for
                    if ((cours.getCourseNum()).equals(course)) {
                        // Set the helpPtr variable to the first student in the list
                        helpPtr = cours.getHead();
                        // If there are no students in the list
                        if(helpPtr == null){
                            // Print an error message to the console
                            output.println("\tERROR: there are no student records for " + cours.getCourseNum() + ".");
                        }
                        // Else, there are students in the list
                        else{
                            // Print which course's roster we are displaying
                            output.println("Course Roster for " + cours.getCourseNum() + ":");
                            // While helpPtr is not pointing to a null value
                            while (helpPtr != null) {
                                // Print the student's name and ID
                                output.println("\t" + helpPtr.getFirst() + " " + helpPtr.getLast() + " (ID# " + helpPtr.getID() + "):");
                                // Call printStudentInfo method to print the rest of their information
                                printStudentInfo(helpPtr, output);
                                // Traverse the list so we are not stuck in an infinite loop
                                helpPtr = helpPtr.getNext();
                            }
                        }
                        // Break out of the for loop as we found the course
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        /*           Files           */
        File inputFile = new File("FSCgradeBook.in"); // Creates the input file
        File outputFile = new File("FSCgradeBook.out"); // Creates the output file

        /*           Tools           */
        Scanner input = new Scanner(inputFile); // Scanner that reads from the input file
        PrintWriter output = new PrintWriter(outputFile); // PrintWriter that writes to the output file

        /*           Variables           */
        int numCourses = input.nextInt(); // Gets the number of courses that will be in the file
        String command; // String to hold the next command to be executed
        String course; // String to hold the course number of a specific course
        FSCcourseRoster[] courses = new FSCcourseRoster[numCourses]; // Array of courses where it's length is the number of courses

        // Prints welcome message and relevant message(s)
        output.println("Welcome to the FSC Grade Book.");
        output.println();
        output.println("The following course(s) have been added to the database:");
        // For loop to add the courses to the array
        for(int i = 0; i < numCourses; i++){
            // Gets the course number
            course = input.next();
            // Creates a new instance of FSCcourseRoster using the course number at the current position in the array
            courses[i] = new FSCcourseRoster(course);
            // Prints the course added
            output.println("\t" + course);
        }
        // Prints a blank line
        output.println();
        // Read the first command
        command = input.next();
        // While the command is not to quit
        while(!command.equals("QUIT")){
            // Switch statement to handle each command case; each one prints the command because two cases must use print instead of println
            switch (command) {
                case "ADDRECORD" -> {
                    output.println("Command: " + command);
                    // Calls addRecord method to add a student
                    addRecord(input, courses, output);
                }
                case "DELETERECORD" -> {
                    output.println("Command: " + command);
                    // Calls deleteRecord method to delete a student
                    deleteRecord(input, courses, output);
                }
                case "SEARCHBYID" -> {
                    output.println("Command: " + command);
                    // Calls searchByID method to look for a student by their ID number
                    searchByID(input, courses, output);
                }
                case "SEARCHBYNAME" -> {
                    output.println("Command: " + command);
                    // Calls searchByName method to look for a student by their name
                    searchByName(input, output, courses);
                }
                case "DISPLAYSTATS" -> {
                    output.print("Command: " + command);
                    // Calls displayStats method to display the stats of one or all courses
                    displayStats(input, output, courses);
                }
                case "DISPLAYSTUDENTS" -> {
                    output.print("Command: " + command);
                    // Calls the displayStudents method to display the students in one or all courses
                    displayStudents(input, output, courses);
                }
            }
            // Prints blank line
            output.println();
            // Gets the next command
            command = input.next();
        }
        // Prints a thank you and goodbye message
        output.println("Thank you for using the the FSC Grade Book.");
        output.println();
        output.println("Goodbye.");

        // Close the input Scanner
        input.close();
        // Close the output PrintWriter
        output.close();
    }
}
