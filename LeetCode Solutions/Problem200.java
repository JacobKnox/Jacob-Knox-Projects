class Solution {
    public static int dr[] = {1, 0, -1, 0};
    public static int dc[] = {0, 1, 0, -1};
    public static int R, C;
    
    public int numIslands(char[][] grid) {
        R = grid.length;
        C = grid[0].length;
        int numCalls = 0;
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(floodfill(i, j, '1', '0', grid) > 0){
                    numCalls++;
                }
            }
        }
        return numCalls;
    }
    
    public static int floodfill(int r, int c, char c1, char c2, char[][] grid){
        if(r < 0 || r >= R || c < 0 || c >= C){
            return 0;
        }
        if(grid[r][c] != c1){
            return 0;
        }
        int ans = 1;
        grid[r][c] = c2;
        for(int d = 0; d < 4; d++){
            ans += floodfill(r + dr[d], c + dc[d], c1, c2, grid);
        }
        return ans;
    }
}