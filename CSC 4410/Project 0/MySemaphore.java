/**
 * Implements a multi-token semaphore.
 * 
 * Authors: Jacob Knox
 */


public class MySemaphore {
    //the number of tokens available
    private int numTokens;

    public MySemaphore(int tokens){
        this.numTokens = tokens;
    }

    public int getNumTokens(){
        return this.numTokens;
    }

    public synchronized void p() {
        if(this.numTokens > 0) {
            this.numTokens--; //removes one token
        }
        else{
            try {
                this.wait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.numTokens--; //removes one token
        }
    }

    public synchronized void v() {
        this.numTokens++;
        
        if(this.getNumTokens() > 0){
            this.notify();
        }
    }
}
