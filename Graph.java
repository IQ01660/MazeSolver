public class Graph {

    int numNodes;
    Node[] nodes;

    public void addEdge(int i, int j) {
	return;
    }

    public Graph(int num) {
	numNodes = num;
	nodes = new Node[numNodes];
	for(int i = 0; i < numNodes; i++) {
	    nodes[i] = new Node(i);
	}

	// you might also want to do other things here
    }

}