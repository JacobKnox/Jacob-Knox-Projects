/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCstudent {
    private String firstName;
    private String lastName;
    private int MACnumber;
    private int broadcastRange;
    private int x;
    private int y;
    private FSCscLinkedDevices allowedMACs;
    private FSCstudent right;
    private FSCstudent left;

    public FSCstudent(int ID, String first, String last, int range, int X, int Y){
        MACnumber = ID;
        firstName = first;
        lastName = last;
        broadcastRange = range;
        x = X;
        y = Y;
        allowedMACs = new FSCscLinkedDevices();
        right = null;
        left = null;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getID(){
        return MACnumber;
    }
    public int getBroadcastRange(){
        return broadcastRange;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getNumLinks(){
        return allowedMACs.getNumLinks();
    }
    public FSCscLinkedDevices getAllowedMACs(){
        return allowedMACs;
    }
    public FSCstudent getRight(){
        return right;
    }
    public FSCstudent getLeft(){
        return left;
    }

    public void setAllowedMACs(FSCscLinkedDevices newList){
        allowedMACs = newList;
    }
    public void setRange(int range){
        broadcastRange = range;
    }
    public void setFirst(String first){
        firstName = first;
    }
    public void setLast(String last){
        lastName = last;
    }
    public void setID(int ID){
        MACnumber = ID;
    }
    public void setX(int newX){
        x = newX;
    }
    public void setY(int newY){
        y = newY;
    }
    public void setRight(FSCstudent rightChild){
        right = rightChild;
    }
    public void setLeft(FSCstudent leftChild){
        left = leftChild;
    }

    public void showConnections(FSCscBST tree){
        FSCscDevice hp = allowedMACs.getHead();
        FSCstudent holder;
        if(allowedMACs.isEmpty()){
            System.out.println("MAC " + MACnumber + " has no links.");
        }
        else {
            System.out.println("Connections for MAC " + MACnumber + ", " + firstName + " " + lastName + ", currently at position (" + x + ", " + y + "):");
            System.out.println("\tThere are a total of " + allowedMACs.getNumLinks() + " link(s).");
            if(allowedMACs.numInRange(this, tree) == 0){
                System.out.println("\tThere are NO active links within the broadcast range of " + broadcastRange + ".");
            }
            else {
                System.out.println("\tThere are " + allowedMACs.numInRange(this, tree) + " active link(s) within the broadcast range of " + broadcastRange + ":");
            }
            while (hp != null) {
                if(tree.findNode(hp.getID()) != null) {
                    if (allowedMACs.withinRange(this, tree.findNode(hp.getID()))) {
                        holder = tree.findNode(hp.getID());
                        System.out.println("\t\tMAC " + hp.getID() + ", " + holder.getFirstName() + " " + holder.getLastName() + ", currently at position (" + holder.getX() + ", " + holder.getY() + ")");
                    }
                }
                hp = hp.getNext();
            }
        }
    }
}
