class Solution {
    public static int dr[] = {1, 0, -1, 0};
    public static int dc[] = {0, 1, 0, -1};
    public static int R, C;
    
    public int maxAreaOfIsland(int[][] grid) {
        R = grid.length;
        C = grid[0].length;
        int maxIsland = 0;
        int area;
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                area = floodfill(i, j, 1, 0, grid);
                if(area > maxIsland){
                    maxIsland = area;
                }
            }
        }
        return maxIsland;
    }
    
    public static int floodfill(int r, int c, int c1, int c2, int[][] grid){
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
