public class Solution {
    public boolean isValid(String s) {
        Stack<Character> parenthesis = new Stack<Character>();
        Character current;
        Character check;
        for(int i = 0; i < s.length(); i++){
            current = s.charAt(i);
            if(current == '(' || current == '[' || current == '{'){
                parenthesis.push(current);
            }
            else{
                if(parenthesis.isEmpty()){
                    return false;
                }
                check = parenthesis.pop();
                if(current == ')'){
                    if(check != '('){
                        return false;
                    }
                }
                if(current == ']'){
                    if(check != '['){
                        return false;
                    }
                }
                if(current == '}'){
                    if(check != '{'){
                        return false;
                    }
                }
            }
        }
        if(!parenthesis.isEmpty()){
            return false;
        }
        return true;
    }
}