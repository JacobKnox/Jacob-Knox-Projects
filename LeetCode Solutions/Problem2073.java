class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
        int time = 0;
        int index = 0;
        while(tickets[k] != 0){
            if(tickets[index] == 0){
                index++;
                if(index > (tickets.length - 1)){
                    index = 0;
                }
                continue;
            }
            tickets[index]--;
            time++;
            index++;
            if(index > (tickets.length - 1)){
                index = 0;
            }
        }
        return time;
    }
}