/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int[] findMode(TreeNode root) {
        HashMap<Integer, Integer> modes = modes(new HashMap<Integer, Integer>(), root);
        int max = 0;
        for(int key : modes.keySet()){
            if(modes.get(key) > max){
                max = modes.get(key);
            }
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int key : modes.keySet()){
            if(modes.get(key) == max){
                list.add(key);
            }
        }
        int[] newList = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            newList[i] = list.get(i);
        }
        return newList;
    }
    public HashMap<Integer, Integer> modes(HashMap<Integer, Integer> modeTracker, TreeNode root){
        if(root == null){
            return null;
        }
        if(modeTracker.containsKey(root.val)){
            modeTracker.replace(root.val, modeTracker.get(root.val) + 1);
        }
        else{
            modeTracker.put(root.val, 1);
        }
        modes(modeTracker, root.left);
        modes(modeTracker, root.right);
        return modeTracker;
    }
}