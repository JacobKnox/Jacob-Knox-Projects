/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode hp1 = headA;
        ListNode hp2 = headB;
        ListNode node2Return = null;
        
        while(hp1 != null){
            hp1.val = -1 * hp1.val;
            hp1 = hp1.next;
        }
        while(hp2 != null){
            if(hp2.val < 0){
                node2Return = hp2;
                break;
            }
            hp2 = hp2.next;
        }
        hp1 = headA;
        while(hp1 != null){
            hp1.val = -1 * hp1.val;
            hp1 = hp1.next;
        }
        return node2Return;
    }
}