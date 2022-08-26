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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int size = 0;
        ListNode hp = head;
        
        while(hp != null){
            size++;
            hp = hp.next;
        }
        
        if(n == size){
            return head.next;
        }
        
        hp = head;
        
        while(size - n != 1){
            hp = hp.next;
            size--;
        }
        
        hp.next = hp.next.next;
        
        return head;
        
    }
}
