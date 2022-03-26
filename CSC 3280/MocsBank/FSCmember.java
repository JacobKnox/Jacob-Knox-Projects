/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

package mocsbank;

public class FSCmember{
    /*           Variables           */
    private int ID; // Stores the ID, also account number, of an instance of FSCmember
    private String firstName; // Stores the first name of an instance of FSCmember
    private String lastName; // Stores the last name of an instance of FSCmember

    /*           Setter Methods           */
    /*
    *  setID Method
    *  Parameters:
    *    id - the id of the FSCmember, same as the account number for the member's account
    */
    public void setID(int id){ // Sets the ID of the member
        ID = id;
    }
    /*
    *  setName Method
    *  Parameters:
    *    first - the first name of the FSCmember
    *    last - the last name of the FSCmember
    */
    public void setName(String first, String last){ // Sets the first and last name of the member; done at the same time since they will never be given at separate times
        firstName = first;
        lastName = last;
    }

    /*           Getter Methods           */
    public int getID(){ // Gets the ID of the member
        return ID;
    }
    public String getFirstName(){ // Gets the first name of the member
        return firstName;
    }
    public String getLastName(){ // Gets the last name of the member
        return lastName;
    }

    /*           Constructor Method           */
    /*
    *  Parameters:
    *    id - the id number of the member, same as their account number
    *    first - the first name of the member
    *    last - the last name of the member
    */
    public FSCmember(int id, String first, String last){
        setID(id);
        setName(first, last);
    }
}