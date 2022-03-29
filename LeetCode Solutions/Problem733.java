class Solution {
    public static int dr[] = {1, 0, -1, 0};
    public static int dc[] = {0, 1, 0, -1};
    public static int R, C;
    
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        R = image.length;
        C = image[0].length;
        
        if(newColor == image[sr][sc]){
            return image;
        }
        return floodfill(sr, sc, image[sr][sc], newColor, image);
    }
    
    public static int[][] floodfill(int r, int c, int c1, int c2, int[][] grid){
        if(r < 0 || r >= R || c < 0 || c >= C){
            return grid;
        }
        if(grid[r][c] != c1){
            return grid;
        }
        grid[r][c] = c2;
        for(int d = 0; d < 4; d++){
            floodfill(r + dr[d], c + dc[d], c1, c2, grid);
        }
        return grid;
    }
}
