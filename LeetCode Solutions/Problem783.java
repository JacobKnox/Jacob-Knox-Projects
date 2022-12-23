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
    int[] list = new int[100];
    int index = 0;
    public int minDiffInBST(TreeNode root) {
        int minDiff = 100001;
        for(int j = 0; j < 100; j++){
            list[j] = -1;
        }
        list = traverse(root);
        int i = 0;
        while(i != 99 && list[i + 1] != -1){
            if(list[i + 1] - list[i] < minDiff){
                minDiff = list[i + 1] - list[i];
            }
            i++;
        }
        return minDiff;
    }
    public int[] traverse(TreeNode root){
        if(root == null){
            return null;
        }
        traverse(root.left);
        list[index] = root.val;
        index++;
        traverse(root.right);
        return list;
    }
}