class Solution {
    int N; // number of vertices in the graph
	int[][] G; // the graph as an adjacency matrix
    public int findCircleNum(int[][] isConnected) {
        G = isConnected;
        N = isConnected.length;
        return BFS();
    }
    
    
    // perform a BFS on the graph G 
	int BFS() {
		// a visited array to mark which vertices have been visited in BFS
		boolean[] V = new boolean[N];

		int numComponets = 0; // the number of components in the graph

		// do the BFS from each node not already visited
		for (int i = 0; i < N; ++i) {
			if (!V[i]) {
				++numComponets;
				BFS(i, V);
			}
		}
		return numComponets;
	}

	// perform a BFS starting at node start
	void BFS(int start, boolean[] V) {
		Queue<Integer> Q = new LinkedList<Integer>(); // A queue to process nodes

		// start with only the start node in the queue and mark it as visited
		Q.offer(start);
		V[start] = true;

		// continue searching the graph while still nodes in the queue
		while (!Q.isEmpty()) {
			int at = Q.poll(); // get the head of the queue

			// add any unseen nodes to the queue to process, then mark them as  
			// visited so they don't get re-added
			for (int i = 0; i < N; ++i) {
				if (G[at][i] == 1 && !V[i]) {
					Q.offer(i);
					V[i] = true;
				}
			}
		}
	}
}