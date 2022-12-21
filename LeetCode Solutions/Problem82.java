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
        HashSet<Integer> dupes = new HashSet<>();
        while(hp != null && hp.next != null){
            if(hp.val == hp.next.val){
                dupes.add(hp.val);
            }
            hp = hp.next;
        }
        while(head != null && dupes.contains(head.val)){
            head = head.next;
        }
        hp = head;
        while(hp != null && hp.next != null){
            if(dupes.contains(hp.next.val)){
                hp.next = hp.next.next;
                continue;
            }
            hp = hp.next;
        }
        return head;
    }
}