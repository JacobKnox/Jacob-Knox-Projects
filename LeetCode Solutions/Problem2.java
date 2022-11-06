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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carryOver = 0, sum = 0;
        ListNode newHead = new ListNode();
        ListNode hp = newHead;
        while(l1 != null || l2 != null){
            if(l1 == null){
                l1 = new ListNode(0);
            }
            else if(l2 == null){
                l2 = new ListNode(0);
            }
            sum = carryOver + l1.val + l2.val;
            l1 = l1.next;
            l2 = l2.next;
            carryOver = sum / 10;
            hp.val = sum % 10;
            if(l1 != null || l2 != null){
                hp.next = new ListNode();
                hp = hp.next;
            }
        }
        if(carryOver != 0){
            hp.next = new ListNode(carryOver);
        }
        return newHead;
    }
}