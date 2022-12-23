class MyStack {
    Queue<Integer> myNums;
    
    public MyStack() {
        myNums = new LinkedList<Integer>();
    }
    
    public void push(int x) {
        myNums.add(x);
        for(int i = 0; i < myNums.size() - 1; i++){
            myNums.add(myNums.poll());
        }
    }
    
    public int pop() {
        return myNums.poll();
    }
    
    public int top() {
        return myNums.peek();
    }
    
    public boolean empty() {
        return(myNums.peek() == null);
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */