import java.util.*;
import java.util.concurrent.*;

/**
 * Models a basic CPU that cannot be interrupted.
 * @author Kyle Burke
 */
public class CPU {
    
    //fields
    
    //whether to print everything that's going on
    private boolean verbosePrinting;
    
    //the completed jobs
    private List<Job> completed;
    
    //name of this CPU
    private String name;
    
    //incoming job list
    private BlockingQueue<Job> incoming;
    
    //whether this is ready to stop
    private boolean readyToStop;
    
    //only opens up when it's completed
    private Semaphore isDone;
    
    //start time of the whole cpu
    private long startTime;
    
    //time this spent running
    private long runningTime;
    
    //the number of jobs this will process
    private int numJobs;
    
    //constructor
    public CPU(String name, BlockingQueue<Job> incoming, boolean verbosePrinting) {
        this.name = name;
        this.incoming = incoming;
        this.verbosePrinting = verbosePrinting;
        this.completed = new ArrayList<Job>();
        this.readyToStop = false;
        this.isDone = new Semaphore(0);
        this.numJobs = -1;
    }
    
    public void blockUntilDone() {
        try {
            this.isDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.isDone.release();
    }
    
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }
    
    
    //ends this when the incoming queue is empty
    public void shutDown() {
        this.readyToStop = true;
    }
    
    //starts this CPU running
    public void start() {
        this.startTime = System.currentTimeMillis();
        Thread t = new Thread(() -> run());
        t.start();
        System.out.println("CPU " + name + " is chugging along!");
    }
    
    private void run() {
        //while (!this.readyToStop || this.incoming.getNumElements() > 0) {
        while (this.completed.size() < this.numJobs) {
            try {
                Job next = this.incoming.take();
                if (this.verbosePrinting) {
                    System.out.println("Next job on " + this.name + ": " + next.toString());
                }
                /*
                long runningTime = next.getLength();
                next.run();
                try {
                    Thread.sleep(runningTime);
                } catch (Exception e) {
                
                }
                next.pause();
                if (!next.isDone()) {
                    System.err.println("Job didn't finish!!!!");
                }*/
                next.run(this);
                if (next.getLength() > 0) {
                    completed.add(next);
                    if (this.verbosePrinting) {
                        System.out.println("Completed on " + this.name + ": " + next.toString());
                        System.out.println("We've run " + this.completed.size() + " jobs.");
                    } else if (this.completed.size() % (this.numJobs / 10) == 0) {
                        System.out.println("CPU " + name + " has completed " + this.completed.size() + " jobs.");
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("Waiting to get the next job got interrupted!");
            }
        }
        this.isDone.release();
        this.runningTime = System.currentTimeMillis() - this.startTime;
        long totalLength = this.getTotalJobLengths();
        long totalLatency = this.getTotalLatency();
        System.out.println("CPU " + this.name + " finished!\n  - Total lengths: " + totalLength + "\n  - Total latency: " + totalLatency + "\n  - Utility: " + this.getUtilityPercentage() + "%\n  - Total wait time: " + this.getTotalWaitTime() + "\n  - Average wait time: " + this.getAverageWaitTime() + "\n  - Max wait time: " + this.getMaxWaitTime());
    }
    
    public long getTotalJobLengths() {
        long total = 0;
        //this shouldn't be executed until it's done...
        try {
            this.isDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Job job : this.completed) {
            total += job.getLength();
        }
        this.isDone.release();
        return total;
    }
    
    public long getTotalLatency() {
        long total = 0;
        //this shouldn't be executed until it's done...
        try {
            this.isDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Job job : this.completed) {
            total += job.getLatency();
        }
        this.isDone.release();
        return total;
    }
    
    public long getTotalWaitTime() {
        return this.getTotalLatency() - this.getTotalJobLengths();
    }
    
    public long getMaxWaitTime() {
        long max = 0;
        try {
            this.isDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Job job : this.completed) {
            long wait = job.getWaitTime();
            if (wait > max) {
                max = wait;
            }
        }
        this.isDone.release();
        return max;
    }
    
    public List<Job> getCompleted() {
        List<Job> completedCopy = new ArrayList<>();
        for (Job job : this.completed) {
            completedCopy.add(job.clone());
        }
        return completedCopy;
    }
    
    public int getNumJobsCompleted() {
        return this.completed.size();
    }
    
    public long getAverageWaitTime() {
        return this.getTotalWaitTime() / this.getNumJobsCompleted();
    }
    
    public long getEfficiency() {
        return (100 * this.getTotalJobLengths() / this.getTotalLatency());
    }
    
    public int getUtilityPercentage() {
        return (int) (100 * this.getTotalJobLengths() / this.runningTime);
    }
    



} //end of CPU.java