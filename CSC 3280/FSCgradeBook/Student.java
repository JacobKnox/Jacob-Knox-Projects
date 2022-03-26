/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package fscgradebook;

public class Student {
    /*           Student Variables           */
    private String courseNumber; // The course number of the specific student instance
    private int ID; // The ID of the student
    private String firstName; // The first name of the student
    private String lastName; // The last name of the student
    private final int[] examGrades = new int[3]; // An array of the student's three exam grades
    private double finalGrade; // The final grade of the student
    private char letterGrade; // The letter grade the student earned
    private static int numStudents; // A static value to keep track of the number of students
    private Student next; // An instance of Student storing the location of the next student in the FSCcourseRoster linked list

    /*           Setter Methods           */
    // setCourseNum Method: sets the courseNumber variable of the student
    private void setCourseNum(String course){
        courseNumber = course;
    }
    // setID Method: sets the ID of the student
    private void setID(int id){
        ID = id;
    }
    // setName Method: sets the first and last name of the student
    private void setName(String first, String last) {
        firstName = first;
        lastName = last;
    }
    // setGrade Method: sets the exam grades of the student, calculates the final grade and letter grade for them
    public void setGrade(int gradeOne, int gradeTwo, int gradeThree){
        examGrades[0] = gradeOne;
        examGrades[1] = gradeTwo;
        examGrades[2] = gradeThree;
        finalGrade = (gradeOne * 0.3) + (gradeTwo * 0.3) + (gradeThree * 0.4);
        if(finalGrade >= 90){
            letterGrade = 'A';
        }
        else if(finalGrade >= 80){
            letterGrade = 'B';
        }
        else if(finalGrade >= 70){
            letterGrade = 'C';
        }
        else if(finalGrade >= 60){
            letterGrade = 'D';
        }
        else{
            letterGrade = 'F';
        }
    }
    // setNext Method: sets where the student's next value should point to in the linked list
    public void setNext(Student nextStudent){
        next = nextStudent;
    }

    /*           Getter Methods           */
    // getID Method: gets the ID of the student
    public int getID(){
        return ID;
    }
    // getNext Method: gets where the student is pointing in the linked list
    public Student getNext(){
        return next;
    }
    // getFirst Method: gets the first name of the student
    public String getFirst(){
        return firstName;
    }
    // getLast Method: gets the last name of the student
    public String getLast(){
        return lastName;
    }
    // getCourse Method: gets the course number of the student
    public String getCourse(){
        return courseNumber;
    }
    // getFinal Method: gets the final grade of the student
    public double getFinal(){
        return finalGrade;
    }
    // getLetter Method: gets the letter grade of the student
    public char getLetter(){
        return letterGrade;
    }
    // getExam Method: gets the exam grade at the specified index of the examGrades array
    public int getExam(int exam){
        return examGrades[exam];
    }
    // getNumStudents Method: gets the current total number of Student instances
    public static int getNumStudents(){
        return numStudents;
    }

    /*           numStudents Managing Methods           */
    // increment Method: increments the number of students
    public static void increment(){
        numStudents++;
    }
    // decrement Method: decrements the number of students
    public static void decrement(){
        numStudents--;
    }

    /*           Constructor Method           */
    public Student(String course, int id, String first, String last, int one, int two, int three){
        // Call the setter methods to set the new student's information
        setCourseNum(course);
        setID(id);
        setName(first, last);
        setGrade(one, two, three);
        // Call the increment method since we added a student
        increment();
    }
}
