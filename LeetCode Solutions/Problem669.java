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
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if(root == null){
            return null;
        }
        if(root.val == high){
            root.right = null;
        }
        if(root.val == low){
            root.left = null;
        }
        if(root.val < low){
            root = root.right;
            return trimBST(root, low, high);
        }
        if(root.val > high){
            root = root.left;
            return trimBST(root, low, high);
        }
        if(root == null){
            return null;
        }
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        
        
        
        /*if(root == null){
            return null;
        }
        if(root.left != null && root.left.val < low){
            delete(root, root.left);
            trimBST(root, low, high);
        }
        trimBST(root.left, low, high);
        if(root.right != null && root.right.val > high){
            delete(root, root.right);
            trimBST(root, low, high);
        }
        trimBST(root.right, low, high);*/
        
        
        
        return root;
    }
    public void delete(TreeNode parent, TreeNode toDelete){
        if(parent.left == toDelete){
            if(toDelete.left == null && toDelete.right == null){
                parent.left = null;
            }
            else if(toDelete.left == null){
                parent.left = toDelete.right;
            }
            else if(toDelete.right == null){
                parent.left = toDelete.left;
            }
            else{
                parent.left = max(toDelete.right);
            }
        }
        if(parent.right == toDelete){
            if(toDelete.left == null && toDelete.right == null){
                parent.right = null;
            }
            else if(toDelete.left == null){
                parent.right = toDelete.right;
            }
            else if(toDelete.right == null){
                parent.right = toDelete.left;
            }
            else{
                parent.right = min(toDelete.left);
            }
        }
    }
    public TreeNode max(TreeNode root){
        if(root.right == null){
            return root;
        }
        return max(root.right);
    }
    public TreeNode min(TreeNode root){
        if(root.left == null){
            return root;
        }
        return min(root.left);
    }
}