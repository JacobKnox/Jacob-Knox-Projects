class MyQueue {
    
    Stack<Integer> pop;
    Stack<Integer> add;

    public MyQueue() {
        pop = new Stack<Integer>();
        add = new Stack<Integer>();
    }
    
    public void push(int x) {
        if(add.empty()){
            while(!pop.empty()){
                add.push(pop.pop());
            }
        }
        add.push(x);
    }
    
    public int pop() {
        if(pop.empty()){
            while(!add.empty()){
               pop.push(add.pop()); 
            }
        }
        return pop.pop();
    }
    
    public int peek() {
        if(pop.empty()){
            while(!add.empty()){
                pop.push(add.pop());
            }
        }
        return pop.peek();
    }
    
    public boolean empty() {
        return(add.empty() && pop.empty());
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */