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
    public ListNode middleNode(ListNode head) {
        ListNode fastHP = head;
        ListNode slowHP = head;
        
        while(fastHP.next != null){
            fastHP = fastHP.next.next;
            slowHP = slowHP.next;
            if(fastHP == null){
                break;
            }
        }
        
        return slowHP;
    }
}