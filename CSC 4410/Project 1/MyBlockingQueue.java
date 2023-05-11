import java.util.LinkedList;

/**
 * Models a thread-safe Blocking Queue.
 *
 * @author Jacob A. Knox <jacobknoxa@gmail.com>
 */

public class MyBlockingQueue<T> {
    private final int maxElements;
    private LinkedList<T> elements;
    private int numElements = 0;

    public MyBlockingQueue(int size){
        this.maxElements = size;
        this.elements = new LinkedList<T>();
    }

    public synchronized void add(T element) {
        while(this.getNumElements() == this.maxElements) {
            try {
                this.wait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.elements.add(element);
        this.numElements++;
        if(this.getNumElements() == 1){
            this.notifyAll();
        }
        else{
            this.notify();
        }
    }

    public synchronized T remove(){
        while(this.getNumElements() == 0) {
            try {
                this.wait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        T firstElement = this.elements.remove(0);
        this.numElements--;
        if(this.getNumElements() == this.maxElements - 1){
            this.notifyAll();
        }
        else{
            this.notify();
        }
        return firstElement;
    }

    public String toString(){
        return this.elements.toString();
    }

    public int getNumElements(){
        return numElements;
    }

    public int getFreeSpace(){
        return this.maxElements - numElements;
    }
}