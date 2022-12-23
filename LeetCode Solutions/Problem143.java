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
    public void reorderList(ListNode head) {
        ListNode hp1 = head;
        ListNode hp2 = head;
        while(hp1 != null && hp1.next != null){
            while(hp2.next.next != null){
                hp2 = hp2.next;
            }
            if(hp1 == hp2){
                break;
            }
            hp2.next.next = hp1.next;
            hp1.next = hp2.next;
            hp2.next = null;
            if(hp1.next == null){
                break;
            }
            hp1 = hp1.next.next;
            hp2 = hp1;
        }
    }
}