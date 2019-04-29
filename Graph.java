public class Graph {

	int numNodes;
	Node[] nodes;

	//adjacency matrix
	int[][] adjMatrix;

    public void addEdge(int i, int j) {
		if(i >= 0 && j >= 0 && i < numNodes && j < numNodes)
		{
			//drawing an edge from both nodes
			adjMatrix[i][j] = 1;
			adjMatrix[j][i] = 1;
		}
		return;
    }

	//returns true if there is an edge between two vertices
	public boolean edgeExists(int i, int j)
	{
		if(i >= 0 && j >= 0 && i < numNodes && j < numNodes)
		{
			if (adjMatrix[i][j] == 1 && adjMatrix[j][i] == 1)
			{
				return true;
			}
		}

		return false;
	}

    public Graph(int num) {
		numNodes = num;

		nodes = new Node[numNodes];
		for(int i = 0; i < numNodes; i++) {
			nodes[i] = new Node(i);
		}

		// you might also want to do other things here

		//creating the adj. matrix
		adjMatrix = new int[numNodes][numNodes];

    }

}