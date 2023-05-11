
import java.util.*;

public class SemaphoreTester {
    public static void main(String[] args) {
        //System.out.println();
        
        int expectedPoints = 0;

        //this test checks the number of tokens right after a semaphore is created.
        for (int i = -1; i < 20; i ++) {
            assert (new MySemaphore(i).getNumTokens() == i) : "Constructor incorrect when given " + i + " tokens!";
        }
        System.out.println("The Constructor seems to work okay.");
        
        final MySemaphore s0 = new MySemaphore(3);
        s0.p();
        s0.p();
        s0.p();
        s0.v();
        s0.p();
        System.out.println("Looks like the semaphore doesn't block when there are a positive number of tokens!");
        expectedPoints += 20;
        
        
        //this test checks whether P blocks when there are no available tokens and that v adds a token.
        final MySemaphore s = new MySemaphore(0);
        final ArrayList<String> flags = new ArrayList<>();
        Thread tryP = new Thread(() -> {s.p(); flags.add("P finished");});
        tryP.start();
        //System.out.println(s.getNumTokens());

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Sleep finished");

        flags.add("Sleep finished");
        s.v();
        //System.out.println(s.getNumTokens());


        assert flags.get(0).equals("Sleep finished") : "p() didn't block on newly-created semaphore with 0 tokens!";
        System.out.println("Looks like p() blocks threads when a semaphore is created with no tokens, good!");
        
        
        try {
            tryP.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        assert flags.get(1).equals("P finished") : "The thread executing p() never finished correctly!";
        assert s.getNumTokens() == 0 : "There aren't zero tokens after a v() and a p().";
        expectedPoints += 20;

        
        //this test checks that lots of v() calls will increment the token appropriately
        System.out.println("Running Multi-v Test...");
        int numThreads = 100000;
        final MySemaphore s2 = new MySemaphore(-numThreads);
        s2.v(); // add one token back
        Thread tryP2 = new Thread(() -> {s2.p(); flags.add("P finished");});
        tryP2.start();
        
        //System.out.println("tryP2 launched!");

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        flags.add("Sleep finished");

        assert flags.get(2).equals("Sleep finished") : "P is not blocking on negative tokens!";


        final long startTime = System.currentTimeMillis();

    
        List<Thread> threads = new ArrayList<Thread>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(() -> {s2.v();});
            threads.add(t);
            t.start();
        }
        
        try {
            tryP2.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        assert s.getNumTokens() == 0 : "The semaphore ended up with the wrong number of tokens!";
        assert flags.get(3).equals("P finished") : "Threads got executed out of order!";


        
        
        try {
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        final long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        int targetMillis = 2500;

        System.out.println("That took: " + elapsed + " milliseconds.");
        System.out.println("Target time: " + targetMillis + " milliseconds.");
        System.out.println( elapsed <= targetMillis ? "You got it in the target time, great!" : "That took a long time!");
        expectedPoints += 10;
        
        
        System.out.println("Running the big stress test...");
        //this test just throws a bunch of p()->v() threads at a Semaphore and hopes they make it.
        final MySemaphore s3 = new MySemaphore(10);
        
        threads = new ArrayList<Thread>(numThreads);
        int startNumTokens = s3.getNumTokens();
        for (int i = 0; i < startNumTokens; i++) {
            Thread t = new Thread(() -> {s3.p();});
            threads.add(t);
            t.start();
        }
        
        try {
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Launched and completed the initial threads...");
        
        //s3 should have zero tokens now
        assert s3.getNumTokens() == 0 : "Multiple p()s don't remove the appropriate number of tokens!";
        int numThreads2 = 30000; // if you're getting an out of memory error, drop this a bit.
        System.out.println("Running stage 2...");
        MySemaphore s4 = new MySemaphore(1-numThreads2);
        for (int i = 0; i < numThreads2; i++) {
            Thread t = new Thread(() -> {
                s3.p();
                s4.v();
                //System.out.println(s4.getNumTokens()); //uncomment this if this part is hanging
                s3.v();
            });
            //threads.add(t);
            t.start();
        }
        System.out.println("Launched the p -> v threads...");
        
        assert s3.getNumTokens() == 0 : "s3 doesn't have the correct number of tokens at this point!";
        
        s3.v();
        System.out.println("Opened the floodgates..."); //uncomment the above print statement if it's hanging here!
        
        s4.p();
        System.out.println("All other threads should be complete!");
        
        s3.p();
        
        assert s4.getNumTokens() == 0 : "s4 didn't end up with the correct number of tokens.";
        
        assert s3.getNumTokens() == 0 : "s3 didn't end up with the correct number of tokens.";
        
        System.out.println("The main testing thread made it to the end, that's something!");
        expectedPoints += 10;
        
        System.out.println("Just based on these tests, you'll earn " + expectedPoints + " points available from tests.  (I will also have to look at your code to check the other parts that can't be tested!)");
        
        
    }
}
