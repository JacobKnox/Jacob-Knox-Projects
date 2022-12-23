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
    public ListNode removeElements(ListNode head, int val) {
        ListNode hp = head;
        while(hp != null && hp.next != null){
            if(hp.next.val == val){
                hp.next = hp.next.next;
                continue;
            }
            hp = hp.next;
        }
        if(head != null && head.val == val){
            head = head.next;
        }
        return head;
    }
}