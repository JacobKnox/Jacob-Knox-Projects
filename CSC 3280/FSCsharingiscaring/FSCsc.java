/*
Author: Jacob Knox
Student ID: 1244362
Section: 001
Email: epicdude203@gmail.com

Florida Southern College Honor Code:
“I will practice academic and personal integrity and excellence of character and expect the same from others.”
*/

/* Any relevant Java imports HERE */
import java.util.*;

public class FSCsc{

    /* MAIN class HERE */
    public static void main(String[] args){

        // Create Scanner to read input from System.in
        Scanner input = new Scanner(System.in);
        // Integer to store the random seed
        int seed = input.nextInt();
        // Integer to store x-size of plane
        int xSize = input.nextInt();
        // Integer to store y-size of plane
        int ySize = input.nextInt();
        // Integer to store the number of commands to be executed
        int numCommands = input.nextInt();
        // Integer to temporarily store the MAC ID
        int macID;
        // Integer to temporarily store second MAC ID
        int macIDTwo;
        // RANDOM object using the seed listed above
        Random rng = new Random(seed);
        // FSCscBST object to contain all the student nodes
        FSCscBST myTree = new FSCscBST();
        // String to store the command
        String command;

        /* FOR loop to loop through the number of commands */
        for(int i = 0; i < numCommands; i++) {
            // Read in the command
            command = input.next();
            /* SWITCH statement for the command */
            switch(command) {
                /* CASE for JOIN command */
                case "JOIN": {
                    // Read in line to appropriate temporary variables (listed above)
                    macID = input.nextInt();
                    // Check IF student is already participating
                    if(myTree.search(macID)) {
                        FSCstudent temp = myTree.findNode(macID);
                        // If they are, output an error (see output)
                        System.out.println("Cannot Perform JOIN Command:\n\tMAC " + macID + ", " + temp.getFirstName() + " " + temp.getLastName() + " - already a participant in the FSC Sharing is Caring system.");
                        input.nextLine();
                    }
                    else {
                        // Else, add them to the FSCscBST instance by creating a new FSCstudent object and calling the insert method
                        FSCstudent temp = new FSCstudent(macID, input.next(), input.next(), input.nextInt(), input.nextInt(), input.nextInt());
                        myTree.insert(temp);
                        //System.out.println(myTree.search(temp.getID()));
                        System.out.println(temp.getFirstName() + " " + temp.getLastName() + ", MAC " + temp.getID() + ", joined the FSC Sharing is Caring system.");
                    }
                    break;
                }

                /* CASE for FINDMAC command */
                case "FINDMAC": {
                    // Read in MAC ID to appropriate temporary variable
                    macID = input.nextInt();
                    // Check IF the MAC exists in the FSCscBST instance
                    if(myTree.search(macID)){
                        FSCstudent temp = myTree.findNode(macID);
                        // If it does, print the MAC ID, name, location, and number of connection
                        if(temp.getNumLinks() == 1){
                            System.out.println("Found:  MAC " + macID + ", " + temp.getFirstName() + " " + temp.getLastName() + ", currently at position (" + temp.getX() + ", " + temp.getY() + "), " + temp.getNumLinks() + " Link");
                        }
                        else {
                            System.out.println("Found:  MAC " + macID + ", " + temp.getFirstName() + " " + temp.getLastName() + ", currently at position (" + temp.getX() + ", " + temp.getY() + "), " + temp.getNumLinks() + " Links");
                        }
                    }
                    // Else, output an error (see output)
                    else{
                        System.out.println("MAC " + macID + " not found in the FSC Sharing is Caring system.");
                    }
                    break;
                }

                /* CASE for LINK command */
                case "LINK": {
                    // Read in the two MAC IDs to the appropriate temporary variables
                    macID = input.nextInt();
                    macIDTwo = input.nextInt();
                    // Check IF both IDs are in the FSCscBST instance
                    if(myTree.search(macID) && myTree.search(macIDTwo)) {
                        FSCstudent tempOne = myTree.findNode(macID);
                        FSCstudent tempTwo = myTree.findNode(macIDTwo);
                        // If they are, check IF they are linked already
                        if(tempOne.getAllowedMACs().search(macIDTwo)) {
                            // If they are, output an error (see output)
                            System.out.println("Cannot Perform LINK Command:\n\tMAC " + macID + " and MAC " + macIDTwo + " are already linked.");
                        }
                        else if(macID == macIDTwo){
                            System.out.println("Cannot Perform LINK Command:\n\tMAC " + macID + " cannot link to itself.");
                        }
                        // Else, link them by adding the MAC ID of each to the other's FSCscLinkedDevices instance by creating an instance of FSCscDevice
                        else{
                            FSCscDevice deviceOne = new FSCscDevice(macID);
                            FSCscDevice deviceTwo = new FSCscDevice(macIDTwo);
                            tempOne.getAllowedMACs().insert(deviceTwo);
                            tempTwo.getAllowedMACs().insert(deviceOne);
                            System.out.println("MAC " + macID + " and MAC " + macIDTwo + " are now linked.");
                        }
                    }
                    // Else, output an error (see output)
                    else{
                        System.out.println("Cannot Perform LINK Command:");
                        if(!myTree.search(macID)){
                            System.out.println("\tMAC " + macID + " - This MAC Address is not in the FSC Sharing is Caring system.");
                        }
                        if(!myTree.search(macIDTwo)){
                            System.out.println("\tMAC " + macIDTwo + " - This MAC Address is not in the FSC Sharing is Caring system.");
                        }
                    }
                    break;
                }

                /* CASE for UNLINK command */
                case "UNLINK": {
                    // Read in the two MAC IDs to the appropriate temporary variables
                    macID = input.nextInt();
                    macIDTwo = input.nextInt();
                    // Check IF both IDs are in the FSCscBST instance
                    if(myTree.search(macID) && myTree.search(macIDTwo)) {
                        FSCstudent tempOne = myTree.findNode(macID);
                        FSCstudent tempTwo = myTree.findNode(macIDTwo);
                        // If they are, check IF they are linked
                        if(tempOne.getAllowedMACs().search(macIDTwo)) {
                            // If they are, unlink them by removing each one's FSCscDevice instance from the other's FSCscLinkedDevices instance
                            tempOne.getAllowedMACs().delete(macIDTwo);
                            tempTwo.getAllowedMACs().delete(macID);
                        }
                        // Else, output an error (see output)
                        else{
                            System.out.println("Cannot Perform UNLINK Command:\n\tMAC " + macID + " and MAC " + macIDTwo + " are not currently linked.");
                        }
                    }
                    // Else, output an error (see output)
                    else{
                        System.out.println("Cannot Perform UNLINK Command:");
                        if(!myTree.search(macID)){
                            System.out.println("\tMAC " + macID + " - This MAC Address is not in the FSC Sharing is Caring system.");
                        }
                        if(!myTree.search(macIDTwo)){
                            System.out.println("\tMAC " + macIDTwo + " - This MAC Address is not in the FSC Sharing is Caring system.");
                        }
                    }
                    break;
                }

                /* CASE for QUIT command */
                case "QUIT": {
                    // Read in the MAC ID to the appropriate temporary variable
                    macID = input.nextInt();
                    // Check IF it is in the FSCscBST instance
                    if(myTree.search(macID)) {
                        FSCstudent temp = myTree.findNode(macID);
                        FSCscDevice hp = temp.getAllowedMACs().getHead();
                        // If it is, remove it from ALL FSCscDevice instances it is a member of, clear it, and remove it from the FSCscBST instance

                        /* WHILE loop to loop over the device's FSCscDevice instance */
                        while(hp != null){
                            // For each MAC ID in the instance, find it in the FSCscBST instance
                            // Call the getAllowedMACs method on the FSCstudent instance then call the delete method on it with the quitting MAC ID
                            if(myTree.search(hp.getID())) {
                                myTree.findNode(hp.getID()).getAllowedMACs().delete(macID);
                            }
                            hp = hp.getNext();
                        }
                        // Set the head of the device's FSCscDevices instance to null to empty it
                        temp.getAllowedMACs().removeAllLinks();
                        // Remove the device from the FSCscBST instance
                        myTree.delete(macID);
                        System.out.println("MAC " + macID + " has been removed from the FSC Sharing is Caring system.");
                    }
                    // Else, output an error (see output)
                    else{
                        System.out.println("Cannot Perform QUIT Command:\n\tMAC " + macID + " not found in the FSC Sharing is Caring system.");
                    }
                    break;
                }

                /* CASE for SHOWCONNECTIONS command */
                case "SHOWCONNECTIONS": {
                    // Read in the MAC ID to the appropriate temporary variable
                    macID = input.nextInt();
                    // Check IF it is in the FSCscBST instance
                    if(myTree.search(macID)) {
                        FSCstudent temp = myTree.findNode(macID);
                        // If it is, print ALL of the information specified
                        temp.showConnections(myTree);
                    }
                    // Else, output an error (see output)
                    else{
                        System.out.println("Cannot Perform SHOWCONNECTIONS Command:\n\tMAC " + macID + " - This MAC Address is not in the FSC Sharing is Caring system.");
                    }
                    break;
                }

                /* CASE for PRINTMEMBERS command */
                case "PRINTMEMBERS": {
                    if(!myTree.isEmpty()) {
                        // Call the printMembers method on the FSCscBST instance
                        System.out.println("Members of FSC Sharing is Caring System:");
                        myTree.printMembers(myTree.getRoot());
                    }
                    else{
                        System.out.println("Cannot Perform PRINTMEMBERS Command:\n\tThere are currently no participants in the FSC Sharing is Caring system.");
                    }
                    break;
                }

                /* CASE for MOVEDEVICES command */
                case "MOVEDEVICES": {
                    if(!myTree.isEmpty()) {
                        // Call the moveDevices method on the FSCscBST instance
                        myTree.moveDevices(xSize, ySize, rng, myTree.getRoot());
                        System.out.println("All devices successfully moved.");
                    }
                    else{
                        System.out.println("Cannot Perform MOVEDEVICES Command:\n\tThere are currently no participants in the FSC Sharing is Caring system.");
                    }
                    // For each, use .nextInt(respective coordinate size variable) on the Random object created earlier to get a new X-coordinate and new Y-coordinate
                    break;
                }
            }
        }
        System.out.println();
    }
}