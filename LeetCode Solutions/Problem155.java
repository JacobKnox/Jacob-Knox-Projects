class MinStack {
    private Stack<Integer> mins;
    private Stack<Integer> nums;

    public MinStack() {
        nums = new Stack<Integer>();
        mins = new Stack<Integer>();
    }
    
    public void push(int val) {
        if(mins.empty()){
            mins.push(val);
        }
        else{
            mins.push(Math.min(val, mins.peek()));
        }
        nums.push(val);
    }
    
    public void pop() {
        mins.pop();
        nums.pop();
    }
    
    public int top() {
        return nums.peek();
    }
    
    public int getMin() {
        return mins.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */