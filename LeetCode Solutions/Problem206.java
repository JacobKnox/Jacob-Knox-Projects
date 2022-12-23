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
    public ListNode reverseList(ListNode head) {
        if(head == null){
            return head;
        }
        
        ListNode hp = head;
        ListNode newHead;
        ListNode newHp;
        
        while(hp.next != null){
            hp = hp.next;
        }
        newHead = hp;
        newHp = newHead;
        
        while(newHp != head){
            hp = head;
            while(hp.next != newHp){
                hp = hp.next;
            }
            newHp.next = hp;
            newHp = newHp.next;
        }
        
        newHp.next = null;
        
        return newHead;
        
        
    }
}