class Solution {
    HashSet<Integer> detonated = new HashSet<>();
    public int maximumDetonation(int[][] bombs) {
        int maxBombs = 1;
        int currBombs;
        for(int i = 0; i < bombs.length; i++){
            detonated.clear();
            currBombs = detonate(i, bombs[i], bombs);
            if(currBombs > maxBombs){
                maxBombs = currBombs;
            }
        }
        return maxBombs;
    }
    
    public int detonate(int curr, int[] bomb, int[][] bombs){
        detonated.add(curr);
        int ans = 1;
        for(int i = 0; i < bombs.length; i++){
            if(inRange(bomb, bombs[i]) && !detonated.contains(i)){
                ans += detonate(i, bombs[i], bombs);
            }
        }
        return ans;
    }
    
    public boolean inRange(int[] bombOne, int[] bombTwo){
        long xDist = (bombTwo[0] - bombOne[0]);
        long yDist = (bombTwo[1] - bombOne[1]);
        long xSq = xDist * xDist;
        long ySq = yDist * yDist;
        long distance = xSq + ySq;
        return distance <= ((long) bombOne[2] * bombOne[2]);
    }
}


/*
COMMENTS FOR THOUGHTS

inRange function to take in two rows
- Calculates Cartesian distance
- Uses range of FIRST bomb to determine in range or not
- Returns true or false

FOR each row (bomb), denotonate it
- Temp mark as detonated (?)
- Check every other row (bomb)
-- IF it is inRange, detonate it too
-- Temp mark as detonated (?)

For every bomb that detonates, increment a tracker variable
Ultimately return tracker variable and check against a max variable



*/
