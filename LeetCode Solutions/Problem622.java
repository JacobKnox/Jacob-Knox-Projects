class MyCircularQueue {
    private int front;
    private int rear;
    private int rearVal;
    private int[] queue;

    public MyCircularQueue(int k) {
        queue = new int[k];
        for(int i = 0; i < k; i++){
            queue[i] = -1;
        }
        front = 0;
        rear = 0;
    }
    
    public boolean enQueue(int value) {
        if(this.isFull()){
            return false;
        }
        queue[rear] = value;
        rearVal = value;
        rear++;
        if(rear > queue.length - 1){
            rear = 0;
        }
        return true;
    }
    
    public boolean deQueue() {
        if(this.isEmpty()){
            return false;
        }
        queue[front] = -1;
        front++;
        if(front > queue.length - 1){
            front = 0;
        }
        return true;
    }
    
    public int Front() {
        return queue[front];
    }
    
    public int Rear() {
        if(this.isEmpty()){
            rearVal = -1;
        }
        return rearVal;
    }
    
    public boolean isEmpty() {
        return (front == rear && queue[rear] == -1);
    }
    
    public boolean isFull() {
        return (rear == front && queue[rear] != -1);
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */