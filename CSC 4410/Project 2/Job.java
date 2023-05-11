/**
 * Models a process to run.  These are interruptible processes.
 *
 * @author Kyle Burke
 */
public class Job {

    //fields
    
    //expected running time
    private long length;
    
    //id of this job
    private String id;
    
    //time created
    private long timeCreated;
    
    //time waited
    private long timeWaited;
    

    //constructor
    public Job(long length, String id) {
        this.length = length;
        this.id = id;
        this.timeCreated = System.currentTimeMillis();
        this.timeWaited = -1;
    }
    
    //gets the expected length
    public long getLength() {
        return this.length;
    }
    
    //gets the time this job was created
    public long getTimeCreated() {
        return this.timeCreated;
    }
    
    //returns the id
    public String getId() {
        return this.id;
    }
    
    //starts the job running
    public void run(CPU cpu) {
        this.timeWaited = System.currentTimeMillis() - this.timeCreated;
        try {
            Thread.sleep(this.length);
        } catch (Exception e) {
            System.out.println("Couldn't sleep!");
        }
        //cpu.finishedJob(this);
    }
    
    public long getWaitTime() {
        if (this.timeWaited > -1) {
            return this.timeWaited;
        } else {
            return System.currentTimeMillis() - this.timeCreated;
        }
    }
    
    public long getLatency() {
        return this.timeWaited + this.length;
    }
    
    public String toString() {
        return "Job " + this.id + " w/length " + this.length + (this.timeWaited > -1 ? (" waited: " + this.timeWaited + " % efficiency: " + (100.0 * this.length / this.getLatency())) : "");
    }
    
    public Job clone() {
        Job clone = new Job(this.length, this.id);
        clone.timeCreated = timeCreated;
        clone.timeWaited = timeWaited;
        return clone;
    }

    
    /**
     * A job that shuts the CPU down.
     */
    public static class KillJob extends Job {
        
        public KillJob() {
            super(0, "Kill");
        }
        
        /**
         * Shuts down the CPU.
         */
        public void run(CPU cpu) {
            cpu.shutDown();
        }
        
    } //end of KillJob
        

} //end of Job.java