import java.util.*;
import java.util.concurrent.*;
import java.lang.*;

/**
 * Runs MyScheduler.java to test running time.  Usage: $ java 
 *
 * @author Kyle Burke <kburke@flsouthern.edu>
 */
public class SchedulerTester {

    //properties to measure
    private static final String[] GOALS = {"max wait", "avg wait", /* "efficiency",*/ "combined"};
    
    
    //default number of jobs
    private static final int DEFAULT_NUM_JOBS = 1000;
    
    //default min job length
    private static final int DEFAULT_MIN_LENGTH = 1;
    
    //default max job length
    private static final int DEFAULT_MAX_LENGTH = 100;
    
    //default fake speedup TODO: In future years, use this!
    private static final int DEFAULT_SPEEDUP = 1;
    
    public static void main(String[] args) {
        boolean verbose = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("verbose")) {
                //found verbose!
                verbose = true;
                //reset args by removing 'verbose'
                String[] newArgs = new String[args.length-1];
                for (int j = 0; j < i; j++) {
                    newArgs[j] = args[j];
                }
                for (int j = i; j < newArgs.length; j++) {
                    newArgs[j] = args[j+1];
                }
                args = newArgs;
                break;
            }
        }
    
        // You may rename this method to better suit the purpose of your test case
        // Your test case logic here
        int numJobsInput;
        try {
            numJobsInput = Integer.parseInt(args[0]);
        } catch (Exception e) {
            numJobsInput = DEFAULT_NUM_JOBS;
        }
        final int numJobs = numJobsInput;
        int minJobInput;
        try {
            minJobInput = Integer.parseInt(args[1]);
        } catch (Exception e) {
            minJobInput = DEFAULT_MIN_LENGTH;
        }
        
        final int minJobLength = minJobInput;
        int maxJobInput;
        try {
            maxJobInput = Integer.parseInt(args[2]);
        } catch (Exception e) {
            maxJobInput = DEFAULT_MAX_LENGTH;
        }
        final int maxJobLength = maxJobInput;
        
        
        int averageLength = (minJobLength + maxJobLength) / 2;
        int defaultAvgLength = (DEFAULT_MIN_LENGTH + DEFAULT_MAX_LENGTH) / 2;
        
        
        
        //run the tests and get the results
        long[] testResults = new long[GOALS.length];
        
        
        
        System.out.println("\n*********************************************************************");
        System.out.println("About to run the simulations with:\n * Num. Jobs: " + numJobs + "\n * Min job length: " + minJobLength + "\n * Max job length: " + maxJobLength);
        
        for (int i = 0; i < GOALS.length; i++) {
            testResults[i] = runTest(GOALS[i], numJobs, minJobLength, maxJobLength, verbose);
        }
        
        //set up the goals of the tests
        Map<String, List<Integer>> goals = new TreeMap<>();
        List<Integer> maxWaitTimeGoals = new ArrayList<>();
        List<Integer> avgWaitTimeGoals = new ArrayList<>();
        //List<Integer> efficiencyGoals = new ArrayList<>();
        List<Integer> combinedGoals = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int maxWaitGoal = (11000 + 1000 * i) * (averageLength / defaultAvgLength);
            maxWaitTimeGoals.add(maxWaitGoal);
            int avgWaitGoal = (50 + 100 * i) * (averageLength / defaultAvgLength);
            avgWaitTimeGoals.add(avgWaitGoal);
            //efficiencyGoals.add(34 - (2 * i));
            int combinedGoal = (37000 + 1000 * i) * (averageLength / defaultAvgLength);
            combinedGoals.add(combinedGoal);
        }
        maxWaitTimeGoals = Arrays.asList(new Integer[] {4000, 6000, 9000, 14000, 21000, 32000, 48000});
        avgWaitTimeGoals = Arrays.asList(new Integer[] {1500, 1600, 1700, 1800, 1900, 2100, 2500});
        combinedGoals = Arrays.asList(new Integer[] {6700, 7000, 7500, 8100, 8800, 9500, 10500});
        goals.put("max wait", maxWaitTimeGoals);
        goals.put("avg wait", avgWaitTimeGoals);
        //goals.put("efficiency", efficiencyGoals);
        goals.put("combined", combinedGoals);
        
    
        
        
        
        //calculate the points based on the goals
        
        
        int totalScore = 0;
        int totalMax = 0;
        
        for (String goal : goals.keySet()) {
            List<Integer> statGoals = goals.get(goal);
            boolean reversed = false;
            if (statGoals.get(0) > statGoals.get(1)) {
                reversed = true;
            }
            int goalIndex = goalToIndex(goal);
            int time = (int) testResults[goalIndex];
            System.out.println("Your code had a " + goal + " of: " + time);
            System.out.println("Brackets: " + statGoals);
            Integer[] array = statGoals.toArray(new Integer[0]);
            if (reversed) {
                Arrays.sort(array);
            }
            int index = Arrays.binarySearch(array, time);
            if (index < 0 ) {
                index = -1 * (index + 1);
            }
            int rank = statGoals.size() - index;
            if (reversed) {
                rank = index;
            }
            System.out.println("Rank: " + rank);
            int score = 10 * rank;
            int maxScore = 10 * statGoals.size();
            System.out.println("That earns: " + score + "/" + maxScore + " points!\n");
            totalScore += score;
            totalMax += maxScore;
        }
        
        totalMax += 15;
        totalScore += 15;
        System.out.println("All tests completed: 15 points");
        
        System.out.println("Total score: " + totalScore + "/" + totalMax);
        
    }


    // Runs a test looking to optimize a specified property, returning the stats from the run.  Stats are returned as:
    // [maxWaitingTime, totalWaitingTime]
    private static long runTest(String property, int numJobs, int minJobLength, int maxJobLength, boolean verbose) {
    
        int propertyIndex = goalToIndex(property);
        
        List<String> ids = new ArrayList<>();
        Random rng = new Random(System.currentTimeMillis());
        for (int i = 0; i < numJobs; i++) {
            ids.add("" +  rng.nextInt(10000));
        }
        
        System.out.println("\n*********************************************************************");
        System.out.println("Testing for: " + property + "    **************************************");

        MyScheduler scheduler = new MyScheduler(numJobs, property);
        BlockingQueue<Job> schedulerToCpu = scheduler.getOutgoingQueue();
        BlockingQueue<Job> generatorToScheduler = scheduler.getIncomingQueue();
        CPU cpu = new CPU("1", schedulerToCpu, verbose);
        cpu.setNumJobs(numJobs);
        //scheduler.setCPU(cpu);
        
        List<Integer> lengths = new ArrayList<>();
        for (int i = 0; i < numJobs; i++) {
            int jobLength = (int) i * (maxJobLength - minJobLength) / numJobs + minJobLength;
            lengths.add(jobLength);
            //jobs.add(new Job(jobLength, ids.get(i)));
            //System.out.print(jobLength + ", ");
        }
        //System.out.println("");
        
        Collections.shuffle(lengths, rng);
        //for (Job job : jobs) {
            //System.out.print(job.getLength() + ", ");
        //}
        

        Thread jobGenerator = new Thread(() -> {
            int i = 0;
            int numToPrime = Math.min(50, numJobs / 2);
            //prime it with some jobs without waiting between creation
            for (; i < numToPrime; ) {
                /*
                int jobLength = ThreadLocalRandom.current().nextInt(minJobLength, maxJobLength + 1);
                Job job = new Job(jobLength, ids.get(i));
                */
                Job job = new Job(lengths.get(i), ids.get(i));
                try {
                    generatorToScheduler.put(job);
                    if (verbose) {
                        System.out.println("Job #" + i + " created!");
                    }
                    i++;
                } catch (InterruptedException e) {
                    System.err.println("Putting a job in the queue to reach the scheduler (while priming) got interrupted!");
                }
            }
            System.out.println("!!!!!!! Done priming the simulation!!!!!!!!");
            //now generate jobs for the scheduler, waiting between each one you create.
            for (; i < numJobs; ) {
                /*
                int jobLength = ThreadLocalRandom.current().nextInt(minJobLength, maxJobLength + 1);
                Job job = new Job(jobLength, ids.get(i));
                */
                Job job = new Job(lengths.get(i), ids.get(i));
                int j = i;
                //add the job to the scheduler in a separate thread so that our processing can't be blocked by an unnaturally small BlockingQueue headed into the scheduler.
                Thread giveToScheduler = new Thread(() -> {
                    try {
                        generatorToScheduler.put(job);
                        if (verbose) {
                            System.out.println("Job #" + j + " created!");
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Adding a job in the queue to reach the scheduler got interrupted!");
                    }
                });
                giveToScheduler.start();
                /* 
                int minWait = (int) Math.floor(.5 * jobLength);
                int maxWait = (int) Math.ceil(1.5 * jobLength);
                int waitTime = ThreadLocalRandom.current().nextInt(minWait, maxWait);
                /* */
                int waitTime = lengths.get((i + numJobs/2) % numJobs);
                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                    //do nothing
                }
                i++;
            }
            //now tell the cpu it's done by sending it a Kill job
            
        });

        cpu.start();
        Thread schedulerThread = new Thread(() -> {
            scheduler.run();
        });
        schedulerThread.start();
        System.out.println("Launched MyScheduler!");
        //scheduler.start();
        jobGenerator.start();

        //System.out.println("Total Wait Time: " + cpu.getTotalWaitTime());
        
        //scheduler.stop();
        
        cpu.blockUntilDone();
        
        List<Job> completed = cpu.getCompleted();
        assert completed.size() == numJobs : "The number of completed jobs doesn't match the number created!";
        for (Job job : completed) {
            ids.remove(job.getId());
        }
        assert ids.size() == 0 : "Not all job ids were completed!";
        
        return (new long[] {cpu.getMaxWaitTime(), cpu.getAverageWaitTime(), /*cpu.getEfficiency(), */ cpu.getMaxWaitTime() + 2* cpu.getAverageWaitTime()})[propertyIndex];
        
    
    }
    
    //gets the index of a goal
    private static int goalToIndex(String goal) {
        for (int i = 0; i < GOALS.length; i++) {
            String goalX = GOALS[i];
            if (goal.equals(goalX)) {
                return i;
            }
        }
        throw new IllegalArgumentException("\"" + goal + "\" is not an appopriate goal!");
    }
}