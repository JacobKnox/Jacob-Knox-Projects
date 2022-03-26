/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

import java.util.*;

public class FSCscBST {
    private FSCstudent root;

    public FSCscBST(){
        root = null;
    }
    public FSCstudent getRoot(){
        return root;
    }
    public boolean isEmpty(){
        return root == null;
    }
    public boolean search(int ID){
        FSCstudent hp = root;
        while (hp != null) {
            if(hp.getID() == ID){
                return true;
            }
            else if(hp.getID() > ID){
                hp = hp.getLeft();
            }
            else{
                hp = hp.getRight();
            }
        }
        return false;
    }
    public FSCstudent findNode(int ID){
        FSCstudent hp = root;
        while (hp != null) {
            if(hp.getID() == ID){
                return hp;
            }
            else if(hp.getID() > ID){
                hp = hp.getLeft();
            }
            else{
                hp = hp.getRight();
            }
        }
        return null;
    }
    public void printMembers(FSCstudent student){
        if(student.getLeft() != null){
            printMembers(student.getLeft());
        }
        if(student.getNumLinks() == 1){
            System.out.println("\tMAC " + student.getID() + ", " + student.getFirstName() + " " + student.getLastName() + ", currently at position (" + student.getX() + ", " + student.getY() + "), " + student.getNumLinks() + " Link");
        }
        else{
            System.out.println("\tMAC " + student.getID() + ", " + student.getFirstName() + " " + student.getLastName() + ", currently at position (" + student.getX() + ", " + student.getY() + "), " + student.getNumLinks() + " Links");
        }
        if(student.getRight() != null){
            printMembers(student.getRight());
        }
    }
    public void moveDevices(int xSize, int ySize, Random rng, FSCstudent root){
        if(root.getLeft() != null) {
            moveDevices(xSize, ySize, rng, root.getLeft());
        }
        root.setX(rng.nextInt(xSize));
        root.setY(rng.nextInt(ySize));
        if(root.getRight() != null) {
            moveDevices(xSize, ySize, rng, root.getRight());
        }
    }
    public void insert(FSCstudent student){
        FSCstudent hp = root;
        if(root == null){
            root = student;
        }
        else {
            while (hp != null) {
                if (student.getID() < hp.getID()) {
                    if (hp.getLeft() == null) {
                        hp.setLeft(student);
                        break;
                    }
                    hp = hp.getLeft();
                }
                else {
                    if (hp.getRight() == null) {
                        hp.setRight(student);
                        break;
                    }
                    hp = hp.getRight();
                }
            }
        }
    }

    //
    // void | delete(int)
    //
    public void delete(int data) {
        root = delete(root, data);
    }
    //
    // BSTnode | delete(BSTnode, int)
    //
    private FSCstudent delete(FSCstudent p, int data) {
        FSCstudent node2delete, newnode2delete, node2save, parent;
        int saveValue;

        // Step 1: Find the node we want to delete
        node2delete = findNode(data);
        // If node is not found (does not exist in tree), we clearly cannot delete it.
        if (node2delete == null) {
            return root;
        }

        // Step 2: Find the parent of the node we want to delete
        parent = parent(p, node2delete);

        // Step 3: Perform Deletion based on three possibilities

        // **1** :  node2delete is a leaf node
        if (isLeaf(node2delete)) {
            // if parent is null, this means that node2delete is the ONLY node in the tree
            if (parent == null) {
                return null; // we return null as the updated root of the tree
            }

            // Delete node if it is a left child
            if (data < parent.getID()) {
                parent.setLeft(null);
            }
            // Delete node if it is a right child
            else {
                parent.setRight(null);
            }

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **2** : node2delete has only a left child
        if (hasOnlyLeftChild(node2delete)) {
            // if node2delete is the root
            if (parent == null) {
                return node2delete.getLeft();
            }

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (data < parent.getID()) {
                parent.setLeft(parent.getLeft().getLeft());
            }
            // ELSE it is the right child of some node
            else {
                parent.setRight(parent.getRight().getLeft());
            }

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **3** :  node2delete has only a right child
        if (hasOnlyRightChild(node2delete)) {
            // if node2delete is the root
            if (parent == null)
                return node2delete.getRight();

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (data < parent.getID())
                parent.setLeft(parent.getLeft().getRight());
                // ELSE it is the right child of some node
            else
                parent.setRight(parent.getRight().getRight());

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **4** :  node2delete has TWO children.
        // Remember, we have two choices: the minVal from the right subtree (of the deleted node)
        // or the maxVal from the left subtree (of the deleted node)
        // We choose to find the minVal from the right subtree.
        newnode2delete = minNode(node2delete.getRight());
        // Now we need to temporarily save the data value(s) from this node
        saveValue = newnode2delete.getID();
        String saveFirst = newnode2delete.getFirstName();
        String saveLast = newnode2delete.getLastName();
        int saveRange = newnode2delete.getBroadcastRange();
        FSCscLinkedDevices saveDevices = newnode2delete.getAllowedMACs();
        int saveX = newnode2delete.getX();
        int saveY = newnode2delete.getY();

        // Now, we recursively call our delete method to actually delete this node that we just copied the data from
        p = delete(p, saveValue);

        // Now, put the saved data (in saveValue) into the correct place (the original node we wanted to delete)
        node2delete.setID(saveValue);
        node2delete.setFirst(saveFirst);
        node2delete.setLast(saveLast);
        node2delete.setRange(saveRange);
        node2delete.setAllowedMACs(saveDevices);
        node2delete.setX(saveX);
        newnode2delete.setY(saveY);

        // Finally, return the root of the tree (in case the root got updated)
        return p;
    }




    /*

    public void delete(FSCstudent student){
        FSCstudent parent = parent(root, student);
        if(parent == null){
            root = minNode(root.getRight());
        }
        else if(isLeaf(student)){
            if(parent.getLeft() == student){
                parent.setLeft(null);
            }
            else{
                parent.setRight(null);
            }
        }
        else if(hasOnlyLeftChild(student)){
            if(parent.getLeft() == student){
                parent.setLeft(student.getLeft());
            }
            else{
                parent.setRight(student.getLeft());
            }
        }
        else if(hasOnlyRightChild(student)){
            if(parent.getLeft() == student){
                parent.setLeft(student.getRight());
            }
            else{
                parent.setRight(student.getRight());
            }
        }
        else{
            FSCstudent min = minNode(student.getRight());
            delete(min);
            if(parent.getRight() == student){
                parent.setRight(min);
            }
            else{
                parent.setLeft(min);
            }
        }
    }

     */






    public FSCstudent minNode(FSCstudent root){
        while(root.getLeft() != null){
            root = root.getLeft();
        }
        return root;
    }
    public FSCstudent parent(FSCstudent root, FSCstudent node){
        if(root == node || root == null){
            return null;
        }
        if(root.getRight() == node || root.getLeft() == node){
            return root;
        }
        if(node.getID() < root.getID()){
            return parent(root.getLeft(), node);
        }
        if(node.getID() > root.getID()){
            return parent(root.getRight(), node);
        }
        return null;
    }
    public boolean isLeaf(FSCstudent node){
        return node.getLeft() == null && node.getRight() == null;
    }
    public boolean hasOnlyLeftChild(FSCstudent node){
        return node.getRight() == null && node.getLeft() != null;
    }
    public boolean hasOnlyRightChild(FSCstudent node){
        return node.getRight() != null && node.getLeft() == null;
    }
}
