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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        ListNode temp;
        while(list2 != null && list1.val >= list2.val){
            temp = list2;
            list2 = list2.next;
            temp.next = list1;
            list1 = temp;
        }
        ListNode hp = list1;
        while(hp.next != null && list2 != null){
            if(hp.next.val >= list2.val){
                temp = list2;
                list2 = list2.next;
                temp.next = hp.next;
                hp.next = temp;
                continue;
            }
            hp = hp.next;
        }
        if(list2 != null){
            hp.next = list2;
        }
        return list1;
    }
}