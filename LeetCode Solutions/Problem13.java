class Solution {
    public int romanToInt(String s) {
        int sum = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'M'){
                sum += 1000;
            }
            else if(s.charAt(i) == 'D'){
                sum += 500;
            }
            else if(s.charAt(i) == 'C'){
                if(i != s.length() - 1 && s.charAt(i + 1) == 'D'){
                    sum += 400;
                }
                else if(i != s.length() - 1 && s.charAt(i + 1) == 'M'){
                    sum += 900;
                }
                else{
                    sum += 100;
                    i--;
                }
                i++;
            }
            else if(s.charAt(i) == 'L'){
                sum += 50;
            }
            else if(s.charAt(i) == 'X'){
                if(i != s.length() - 1 && s.charAt(i + 1) == 'L'){
                    sum += 40;
                }
                else if(i != s.length() - 1 && s.charAt(i + 1) == 'C'){
                    sum += 90;
                }
                else{
                    sum += 10;
                    i--;
                }
                i++;
            }
            else if(s.charAt(i) == 'V'){
                sum += 5;
            }
            else{
                if(i != s.length() - 1 && s.charAt(i + 1) == 'V'){
                    sum += 4;
                }
                else if(i != s.length() - 1 && s.charAt(i + 1) == 'X'){
                    sum += 9;
                }
                else{
                    sum += 1;
                    i--;
                }
                i++;
            }
        }
        return sum;
    }
}