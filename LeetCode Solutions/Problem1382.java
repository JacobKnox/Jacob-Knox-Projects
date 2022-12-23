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
    ArrayList<Integer> list = new ArrayList<>();
    public TreeNode balanceBST(TreeNode root) {
        toArray(root);
        root = sortedArrayToBST(list.toArray(new Integer[0]));
        return root;
    }
    public void toArray(TreeNode root){
        if(root != null){
           toArray(root.left);
            list.add(root.val);
            toArray(root.right); 
        }
    }
    public TreeNode sortedArrayToBST(Integer[] nums) {
        if(nums.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(nums[nums.length / 2]);
        if(nums.length == 1){
            return root;
        }
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, nums.length / 2));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, (nums.length / 2) + 1, nums.length));
        return root;
    }
}