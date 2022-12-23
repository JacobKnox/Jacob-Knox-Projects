/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode hp = head;
        while(hp != null){
            if(hp.val > 99999){
                return true;
            }
            hp.val = hp.val + 100000;
            hp = hp.next;
        }
        return false;
    }
}