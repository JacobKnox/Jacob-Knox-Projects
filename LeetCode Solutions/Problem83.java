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
    public ListNode deleteDuplicates(ListNode head) {
        ListNode hp = head;
        while(hp != null){
            if(hp.next == null){
                break;
            }
            if(hp.val == hp.next.val){
                hp.next = hp.next.next;
            }
            else{
                hp = hp.next;
            }
        }
        return head;
    }
}