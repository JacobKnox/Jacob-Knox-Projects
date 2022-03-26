/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

public class FSCscLinkedDevices {
    private FSCscDevice head;
    private int numDevices;

    public FSCscLinkedDevices(){
        head = null;
        numDevices = 0;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public int getNumLinks(){
        return numDevices;
    }

    public boolean search(int ID){
        FSCscDevice hp = head;
        while(hp != null){
            if(hp.getID() == ID){
                return true;
            }
            hp = hp.getNext();
        }
        return false;
    }

    public FSCscDevice findNode(int ID){
        FSCscDevice hp = head;
        while(hp != null){
            if(hp.getID() == ID){
                return hp;
            }
            hp = hp.getNext();
        }
        return null;
    }

    public void insert(FSCscDevice node){
        FSCscDevice hp = head;
        if(head == null){
            head = node;
        }
        else if(head.getID() > node.getID()){
            node.setNext(head);
            head = node;
        }
        else{
            while(hp.getNext() != null){
                if(hp.getNext().getID() > node.getID()){
                    node.setNext(hp.getNext());
                    hp.setNext(node);
                    break;
                }
                hp = hp.getNext();
            }
            if(hp.getNext() == null) {
                hp.setNext(node);
            }
        }
        numDevices++;
    }

    public void delete(int ID){
        FSCscDevice hp = head;
        if(hp == null || hp.getNext() == null){
            head = null;
        }
        else {
            while (hp.getNext() != null) {
                if (hp.getNext().getID() == ID) {
                    hp.setNext(hp.getNext().getNext());
                    break;
                }
                hp = hp.getNext();
            }
        }
        numDevices--;
    }

    public boolean withinRange(FSCstudent studentOne, FSCstudent studentTwo){
        double dis = Math.sqrt(Math.pow(studentOne.getX() - studentTwo.getX(), 2)+Math.pow(studentOne.getY() - studentTwo.getY(), 2));
        int distance = (int) dis;
        return distance <= studentOne.getBroadcastRange();
    }

    public int numInRange(FSCstudent studentOne, FSCscBST tree){
        double dis;
        int num = 0;
        FSCscDevice hp = head;

        while(hp != null){
            if(tree.findNode(hp.getID()) != null) {
                dis = Math.sqrt(Math.pow(studentOne.getX() - tree.findNode(hp.getID()).getX(), 2) + Math.pow(studentOne.getY() - tree.findNode(hp.getID()).getY(), 2));
                int distance = (int) dis;
                if (distance <= studentOne.getBroadcastRange()) {
                    num++;
                }
            }
            hp = hp.getNext();
        }

        return num;
    }

    public void removeAllLinks(){
        head = null;
    }

    public FSCscDevice getHead(){
        return head;
    }
}
