/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCscDevice {
    private final int MACnumber;
    private FSCscDevice next;

    public FSCscDevice(int ID){
        MACnumber = ID;
        next = null;
    }

    public int getID(){
        return MACnumber;
    }
    public FSCscDevice getNext(){
        return next;
    }
    public void setNext(FSCscDevice nextNode){
        next = nextNode;
    }
}
