class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] answer = {-1, -1};
        int test = 0;
        for(int i = 0; i < nums.length; i++){
            answer[0] = i;
            test = nums[i];
            for(int j = i + 1; j < nums.length; j++){
                if((test + nums[j]) == target){
                    answer[1] = j;
                    break;
                }
            }
            if(answer[1] != -1){
                break;
            }
        }
        return answer;
    }
}