/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode oddEvenList(ListNode head) {
        ListNode hp = head;
        if(hp == null){
            return head;
        }
        ListNode newHead = hp.next;
        ListNode newHP = newHead;
        
        while(hp.next != null){
            hp.next = newHP.next;
            if(hp.next == null){
                break;
            }
            hp = hp.next;
            newHP.next = hp.next;
            newHP = newHP.next;
        }
        
        hp.next = newHead;
        
        return head;
    }
}