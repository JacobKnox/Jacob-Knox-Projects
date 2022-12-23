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
    public int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> nums = reverseOrder(new ArrayList<Integer>(), root);
        return nums.get(k - 1);
    }
    public ArrayList<Integer> reverseOrder(ArrayList<Integer> tree, TreeNode root){
        if(root == null){
            return null;
        }
        reverseOrder(tree, root.left);
        tree.add(root.val);
        reverseOrder(tree, root.right);
        return tree;
    }
}