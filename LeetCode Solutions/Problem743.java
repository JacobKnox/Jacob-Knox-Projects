import java.util.Arrays;

class Solution {
    public static int V;
    public int networkDelayTime(int[][] times, int n, int k) {
        int[][] graph = new int[n][n];
        V = n;
        for(int i = 0; i < n; i++){
            Arrays.fill(graph[i], Integer.MAX_VALUE);
        }
        for(int i = 0; i < times.length; i++){
            graph[times[i][0] - 1][times[i][1] - 1] = times[i][2];
        }
        int[] dist = dijkstra(graph, k - 1);
        int max = 0;
        for(int i = 0; i < dist.length; i++){
            if(dist[i] == Integer.MAX_VALUE){
                return -1;
            }
            if(dist[i] > max){
                max = dist[i];
            }
        }
        return max;
    }
    
    int minDistance(int[] dist, boolean[] sptSet)
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    int[] dijkstra(int[][] graph, int src)
    {
        int[] dist = new int[V]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        boolean[] sptSet = new boolean[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v] && graph[u][v] != Integer.MAX_VALUE)
                    dist[v] = dist[u] + graph[u][v];
        }

        // print the constructed distance array
        return dist;
    }
}