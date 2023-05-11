import java.util.*;
import java.util.concurrent.*;

/**
 * Models a job scheduler.
 *
 * @author Jacob A. Knox <jacobknoxa@gmail.com>
 */

public class MyScheduler{
    private BlockingQueue<Job> incomingJobs;
    private BlockingQueue<Job> outgoingJobs;
    private String metric;
    private int numJobs;

    public MyScheduler(int numJobs, String property){
        this.incomingJobs = new LinkedBlockingDeque<>(numJobs);
        this.outgoingJobs = new LinkedBlockingDeque<>(numJobs);
        this.metric = property;
        this.numJobs = numJobs;
    }

    public BlockingQueue<Job> getIncomingQueue() {
        return this.incomingJobs;
    }

    public BlockingQueue<Job> getOutgoingQueue() {
        return this.outgoingJobs;
    }

    public void run(){
        int jobsMoved = 0;
        if(this.metric.equals("max wait")){
            while(jobsMoved < this.numJobs){
                try {
                    Job next = this.incomingJobs.take();
                    this.outgoingJobs.add(next);
                    jobsMoved++;
                } catch (InterruptedException e) {
                    System.err.println("Waiting to get the next job got interrupted!");
                }
            }
        }

        else if(this.metric.equals("combined")){
            ArrayList<Job> temp = new ArrayList<>();
            while(jobsMoved < this.numJobs){
                    this.incomingJobs.drainTo(temp);
                    if(temp.size() > 0){
                        Collections.sort(temp, Collections.reverseOrder(Comparator.comparing(Job::getLatency)));
                        Job next = temp.get(0);
                        temp.remove(0);
                        this.outgoingJobs.add(next);
                        jobsMoved++;
                    }
            }
        }

        else{
            ArrayList<Job> temp = new ArrayList<>();
            long time = 0;
            while(jobsMoved < this.numJobs){
                try {
                    Thread.sleep(time);
                    this.incomingJobs.drainTo(temp);
                    if(temp.size() > 0){
                        Collections.sort(temp, Comparator.comparing(Job::getLength));
                        Job next = temp.get(0);
                        temp.remove(0);
                        time = next.getLength();
                        this.outgoingJobs.add(next);
                        jobsMoved++;
                    }
                } catch (InterruptedException e) {
                    System.err.println("Waiting to get the next job got interrupted!");
                }
            }
        }
    }
}