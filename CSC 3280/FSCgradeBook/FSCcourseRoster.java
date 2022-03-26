/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package fscgradebook;

public class FSCcourseRoster {
    /*           FSCcourseRoster Variables           */
    private Student head; // The head of the linked list, points to the first student in the list
    private String courseNumber; // The course number of the specific linked list created

    /*           Constructor Method           */
    public FSCcourseRoster(String course){
        head = null; // Sets head to null as the list is empty when created
        setCourse(course);
    }

    /*           Setter Methods           */
    // setCourse Method: sets the course number of the linked list
    public void setCourse(String course){
        courseNumber = course;
    }

    /*           Getter Methods          */
    // getCourseNum Method: gets the course number of the linked list
    public String getCourseNum(){
        return courseNumber;
    }
    // getHead Method: gets the current head of the linked list
    public Student getHead(){
        return head;
    }

    /*           List Manipulation Methods           */
    // insert Method: inserts a new student into the list
    public void insert(Student student){
        /*           Method Variables          */
        Student helpPtr = head; // creates a helper variable to help us traverse the list without losing it

        // If the list is empty or the student will be the new first value
        if(helpPtr == null || helpPtr.getID() > student.getID()){
            // Set the student's next to the current help pointer (point new to successor)
            student.setNext(helpPtr);
            // Point the head to the new student (point predecessor to new)
            head = student;
        }
        // Else, the list has students in it and the new one will not be the first student in the list
        else{
            // While the helpPtr does not have a next of null (stops us at the predecessor)
            while(helpPtr.getNext() != null){
                // If the ID of the student after the helpPtr's student is greater than the student's ID
                if(helpPtr.getNext().getID() > student.getID()){
                    // Break out of the while loop, because we found our spot
                    break;
                }
                // Traverse the helpPtr to the next student, so we aren't stuck in an infinite loop
                helpPtr = helpPtr.getNext();
            }
            // Set the student's next to the helpPtr's next (point new to successor)
            student.setNext(helpPtr.getNext());
            // Set the helpPtr's student's next to the new student (point predecessor to new)
            helpPtr.setNext(student);
        }
    }
    // delete Method: deletes a student from the list; returns a boolean value indicating whether or not the student was deleted successfully
    public boolean delete(int id){
        /*           Method Variables           */
        Student helpPtr = head; // Creates a helper variable to help us traverse the list without losing it

        // If the list is not empty and the first student is the one we want to delete
        if(head != null && head.getID() == id){
            // Set the head to the student after the first student (deleting the first student since nobody is pointing to them now
            head = head.getNext();
            // Call the decrement method in Student to decrease the number of students since we deleted one
            Student.decrement();
            // Return true since the student was successfully deleted
            return true;
        }
        // If the above code does not execute, then while helpPtr is not null and the one after helpPtr is not null
        while(helpPtr != null && helpPtr.getNext() != null){
            // If the student after helpPtr's student is the one we are looking to delete
            if(helpPtr.getNext().getID() == id){
                // Point helpPtr to the student after the one we are looking to delete
                helpPtr.setNext(helpPtr.getNext().getNext());
                // Call the decrement method in Student to decrease the number of students since we deleted one
                Student.decrement();
                // Return true since the student was successfully deleted
                return true;
            }
            // Set the helpPtr to its next, so we traverse the list and are not stuck in an infinite loop
            helpPtr = helpPtr.getNext();
        }
        // If we exit the loop and nothing has been returned, return false as the student was not in the list
        return false;
    }

    /*           List Search Methods           */
    // searchByID Method: looks for a student in the list given their ID; returns the student if they are found and null if not
    public Student searchByID(int id){
        /*           Method Variables           */
        Student helpPtr = head; // A helper variable to help us traverse the list wthout losing it

        // While the helpPtr is not at the end of the list
        while(helpPtr != null){
            // If the ID of the current helpPtr is the one we are looking for
            if(helpPtr.getID() == id){
                // Return the student helpPtr is pointing at
                return helpPtr;
            }
            // Traverse the list, so we don't get stuck in an infinite loop
            helpPtr = helpPtr.getNext();
        }
        // If we have yet to return anything when the while loops exits, then the student was not in the course and it returns null
        return null;
    }
    // searchByName Method: searches for a student in the list given their full name; returns the student if they are found and null if not
    public Student searchByName(String name){
        /*           Method Variables           */
        Student helpPtr = head; // A helper variable to traverse the list without losing it
        String studentName; // The name of the student at the helpPtr

        // If there are not students in the system
        if(Student.getNumStudents() == 0){
            // Return null
            return null;
        }
        // While helpPtr is not pointing at a null value
        while(helpPtr != null){
            // Set the student's name
            studentName = helpPtr.getFirst() + " " + helpPtr.getLast();
            // If the name we are looking for matches the student's name
            if(name.equals(studentName)){
                // Return the student that helpPtr is pointing at
                return helpPtr;
            }
            // Traverse the list, so we don't get stuck in an infinite loop
            helpPtr = helpPtr.getNext();
        }
        // If nothing has been returned once the while loop ends, then the student does not exist in the course and we return null
        return null;
    }
}
