import java.util.*;
import java.util.concurrent.*;

public class BlockingQueueTester {

    private static final int NUM_THREADS_DEFAULT = 20000;

    public static void assertTest(boolean test, String errorMessage, int pointsSoFar) {
        assert test : errorMessage + "  Total Points: " + pointsSoFar; 
        if (!test) {
            System.out.println("ERROR: " + errorMessage);
        }
    }

    public static void main(String[] args) {
        //System.out.println();
        
        int numThreads = NUM_THREADS_DEFAULT;
        if (args.length > 0) {
            try {
                int inputThreads = Integer.parseInt(args[0]);
                if (inputThreads > 0) {
                    numThreads = inputThreads;
                    System.out.println("Setting numThreads to " + numThreads + "...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Using the default number of threads: " + numThreads + ".");
        }
        
        int expectedPoints = 0;
        
        MyBlockingQueue<String> strings = new MyBlockingQueue<>(10);
        
        MyBlockingQueue<Integer> ints = new MyBlockingQueue<>(13);
        
        MyBlockingQueue<String> tiny = new MyBlockingQueue<>(1);
        
        
        tiny.add("Hi");
        ints.add(1337);
        
        //System.out.println(tiny);
        //System.out.println(ints);
        
        System.out.println("Testing toString...");
        
        assertTest(tiny.toString().contains("Hi"), "I don't see the added element in the toString!", expectedPoints);
        assertTest(!strings.toString().contains("Hi"), "Why does the toString include the string 'Hi'?  It shouldn't.", expectedPoints);
        assertTest(ints.toString().contains("1337"), "I don't see the added element in the toString!", expectedPoints);
        
        //finished toString
        expectedPoints += 10;
        
        
        System.out.println("Testing remove...");
        //test remove basics
        int lastInt = ints.remove();
        assertTest(lastInt == 1337, "Remove doesn't return the correct element on a queue of integers.", expectedPoints);
        assertTest(!ints.toString().contains("1337"), "It doesn't look like remove removes the element from the queue!", expectedPoints);
        String lastString = tiny.remove();
        assertTest(lastString.equals("Hi"), "Remove doesn't return the correct element on a queue of Strings.", expectedPoints);
        strings.add("1");
        strings.add("2");
        lastString = strings.remove();
        assertTest(lastString.equals("1"), "Remove doesn't return the correct element on a queue of Strings.", expectedPoints);
        
        //finished testing remove
        expectedPoints += 50;
        
        
        
        System.out.println("Testing getNumElements...");
        //testing getNumElements
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        assertTest(ints.getNumElements() == 0, "getNumElements doesn't work on an empty list.", expectedPoints);
        tiny.add("Hello");
        assertTest(tiny.getNumElements() == 1, "getNumElements doesn't work on a list with one  element.", expectedPoints);
        strings.add("mankey");
        strings.add("primeape");
        assertTest(strings.getNumElements() == 2, "getNumElements doesn't seem to work on a list with two elements.", expectedPoints);
        for (int i = 0; i < 13; i++) {
            ints.add(i);
            assertTest(ints.getNumElements() == (i+1), "getNumElements isn't working when there are " + i + " elements.", expectedPoints);
        }
        
        //finished testing getNumElements
        expectedPoints += 7;
        
        
        
        System.out.println("Testing getFreeSpace...");
        //testing getFreeSpace
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        assertTest(ints.getFreeSpace() == 13, "getFreeSpace doesn't work on an empty list.", expectedPoints);
        tiny.add("Hello");
        assertTest(tiny.getFreeSpace() == 0, "getFreeSpace doesn't work on a list with one  element.", expectedPoints);
        strings.add("mankey");
        strings.add("primeape");
        assertTest(strings.getFreeSpace() == 8, "getFreeSpace doesn't seem to work on a list with two elements.", expectedPoints);
        for (int i = 0; i < 13; i++) {
            ints.add(i);
            assertTest(ints.getFreeSpace() == (13 - i - 1), "getFreeSpace isn't working when there are " + i + " elements.", expectedPoints);
        }
        
        //finished testing getFreeSpace
        expectedPoints += 8;
        
        
        
        System.out.println("Testing FIFO-ness...");
        //testing FIFO-ness
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        for (int i = 0; i < 13; i++) {
            ints.add(i);
        }
        for (int i = 0; i < 13; i++) {
            assertTest(ints.remove() == i, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        }
        ints.add(5);
        ints.add(7);
        ints.add(9);
        assertTest(ints.remove() == 5, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        ints.add(11);
        ints.add(13);
        assertTest(ints.remove() == 7, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        assertTest(ints.remove() == 9, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        assertTest(ints.remove() == 11, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        assertTest(ints.remove() == 13, "The queue doesn't maintain the FIFO-policy.", expectedPoints);
        
        //finished testing fifo-ness
        expectedPoints += 10;
        
        
        
        System.out.println("Testing that remove() blocks correctly...");
        //testing that remove blocks appropriately.
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        Queue<String> flags = new ConcurrentLinkedQueue<>();
        flags.add("Monkey");
        Thread t = new Thread(() -> {ints.remove(); flags.add("Gibbon");});
        t.start();
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            //do nothing
        }
        assertTest(flags.size() == 1, "Remove doesn't block correctly.", expectedPoints);
        ints.add(1);
        
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //do nothing
        }
        flags.remove(); //remove Gibbon
        System.out.println("Testing multiple threads...");
        for (int i = 0; i < 10; i++) {
            t = new Thread(() -> {int k = ints.remove(); flags.add("" +  k); });
            t.start();
        }
        
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                //do nothing
            }
            assertTest(flags.size() == 1, "Remove doesn't block correctly! i=" + i + " ints: " + ints + " flags: " + flags, expectedPoints);
            ints.add(i);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //do nothing
            }
            //System.out.println(flags.get(1));
            flags.remove();
        }
        //finished testing remove blocking
        expectedPoints += 30;
        
        
        
        
        System.out.println("Testing that add blocks correctly...");
        //testing that add blocks appropriately.
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        
        tiny.add("Monkey");
        t = new Thread(() -> {tiny.add("Gibbon"); flags.add("Gibbon"); });
        t.start();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //do nothing
        }
        assertTest(flags.size() == 1, "add() doesn't block correctly!", expectedPoints);
        tiny.remove();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            //do nothing
        }
        flags.remove();
        
        System.out.println("Testing multiple threads...");
        //now test a bunch...
        for (int i = 0; i < 13; i++) {
            t = new Thread(() -> {tiny.add("Another Gibbon"); flags.add("Another Gibbon"); });
            t.start();
        }
        
        for (int i = 0; i < 13; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //do nothing
            }
            assertTest(flags.size() == 1, "add() isn't blocking correctly!", expectedPoints);
            tiny.remove();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //do nothing
            }
            flags.remove();
        }
        //done testing add
        expectedPoints += 30;
        
        
        
        
        System.out.println("Running the stress test...");
        //now the stress test
        //reset the queues
        while (ints.getNumElements() > 0) {
            ints.remove();
        }
        while (tiny.getNumElements() > 0) {
            tiny.remove();
        }
        while (strings.getNumElements() > 0) {
            strings.remove();
        }
        
        
        final long startTime = System.currentTimeMillis();
        
        Queue<Thread> addingThreads = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < numThreads; i++) {
            int j = i;
            
            t = new Thread(() -> {
                ints.add(j); 
                //System.out.println("j: " + j); 
            });
            t.start();
            addingThreads.add(t);
        }
        System.out.println("Adding threads launched!");
        Queue<Integer> completedThreads = new ConcurrentLinkedQueue<>();
        Queue<Thread> threads = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < numThreads - 13; i++) {
            t = new Thread(() -> {
                //System.out.println(ints);
                int id = ints.remove();
                completedThreads.add(id);
                //System.out.println("Adding id: " + id); 
            });
            threads.add(t);
        }
        System.out.println("Removing threads created!");
        for (Thread thread : threads) {
            thread.start();
        }
        System.out.println("Removing threads launched!");
        
        for (Thread x : addingThreads) {
            try {
                x.join();
            } catch (Exception e) {
                //do nothing
            }
            //addingThreads.remove(0);
        }
        System.out.println("Adding threads finished!");
        
        for (Thread x : threads) {
            try {
                x.join();
            } catch (Exception e) {
                //do nothing
            }
            //threads.remove(0);
        }
        System.out.println("Removing threads finished!");
        
        
        Queue<Integer> alreadySeenIds = new ConcurrentLinkedQueue<>();
        Queue<Integer> doublySeenIds = new ConcurrentLinkedQueue<>();
        int k = 0;
        for (int id : completedThreads) {
            //completedThreads.toString();
            //int id = completedThreads.get(k);
            if (k % 10 == 0) {
                //System.out.println(k + "-th completed id: " + id);
            }
            if (alreadySeenIds.contains(id)) {
                doublySeenIds.add(id);
            }
            k++;
        }
        
        assertTest(doublySeenIds.size() == 0, "Two threads pulled the same integers (" + doublySeenIds + ") out of the BlockingQueue!  That shouldn't happen!", expectedPoints);
        
        
        
        //done with the stress test
        expectedPoints += 10;
        
        System.out.println("Stress test complete!");

        final long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        int targetMillis = 16000 * numThreads / NUM_THREADS_DEFAULT;
        
        

        System.out.println("That took: " + elapsed + " milliseconds.");
        System.out.println("Target time: " + targetMillis + " milliseconds.  (Based on your number of threads.)");
        System.out.println( elapsed <= targetMillis ? "You got it in the target time, great!" : "That took a long time!");
        
        
        int[][] targets = new int[][] {{10000, 20}, {13000, 15}, {16000, 10}, {20000, 5}};
        
        int bonusPoints = 0;
        
        for (int i = 0; i < targets.length; i++) {
            int targetTime = targets[i][0] * numThreads / NUM_THREADS_DEFAULT;
            int bonus = targets[i][1];
            if (elapsed <= targetTime) {
                bonusPoints = bonus;
                System.out.println("Your code seems to be fast enough for a bonus!  (" + bonus + ")  I would check with Kyle to make sure it runs fast enough on his machine.");
                break;
            }
        }
        
        expectedPoints += bonusPoints;
        
        
        
        
        try {
            assert false;
        } catch (Error e) {
            System.out.println("Just based on these tests, it looks like you'll earn " + expectedPoints + " points available.  (I will also have to look at your code to check the other parts that can't be tested!)");
        }
        
        
    }
} //end of BlockingQueueTester.java