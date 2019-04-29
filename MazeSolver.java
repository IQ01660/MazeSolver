/*-----------------------------------------------------------------------------------------
ANSWER THE QUESTIONS FROM THE DOCUMENT HERE

(1) Which graph representation did you choose, and why?
    I chose Adjacency Matrix, because we do not remove or add any new vertices to our Graph
    (which would probably be more efficient with Adjacency list). 
    Instead we are updating edges which take less big-O time with 
    adjacency matrix implementation.

(2) Which search algorithm did you choose, and why?


  ----------------------------------------------------------------------------------------*/

  import java.io.*;
  import java.lang.Math;
  import java.util.Stack;
  import java.util.Vector;
  
  public class MazeSolver {
  
      public Node[] solve(Graph _mazeGraph)
      {
        //the nodes that will be in the path
        Node[] pathNodes = new Node[_mazeGraph.numNodes];
        int inSolutionCtr = 0;

        Stack<Node> foundNodes = new Stack<Node>();

        //find the top left node and add it to foundNodes
        foundNodes.push(_mazeGraph.nodes[0]);

        

        //while the foundNodes list is not empty
        while (!foundNodes.isEmpty())
        {
            //remove the last added Node from foundNodes list
            Node removedNode = foundNodes.pop();
            
            //if this removedNode has not been visited
            if(!removedNode.visited)
            {
                //mark it as visited
                removedNode.visited = true;

                //adding the node to patthNodes list (inSolution)
                //some will be removed later
                pathNodes[inSolutionCtr] = removedNode;
                inSolutionCtr++;

                //stops if reaches the exit
                if(removedNode.index == _mazeGraph.numNodes - 1)
                {
                    break;
                }
                //if node just removed from stack has no neighbors
                Node lastStep = removedNode;
                while(findUnvisitedNeighbors(lastStep, _mazeGraph).isEmpty())
                {
                    pathNodes[inSolutionCtr - 1] = null;
                    inSolutionCtr--;
                    lastStep = pathNodes[inSolutionCtr - 1];
                }

                //add its unvisited neighbors to the foundList
                for (Node _neighbor : findUnvisitedNeighbors(removedNode, _mazeGraph)) {
                    foundNodes.push(_neighbor);
                }
            }
        }
        return pathNodes;
      }

      //returns a vector of neighbors of a provided node
      private Vector<Node> findUnvisitedNeighbors(Node _removedNode, Graph _mazeGraph)
      {
          
          Vector<Node> edgedNeighbors = new Vector<Node>();
          int graphSize = (int) Math.sqrt(_mazeGraph.numNodes);

          //check whether the left neighbor has an edge with removedNode
          if ( _mazeGraph.edgeExists(_removedNode.index - 1, _removedNode.index) )
          {
            if (!_mazeGraph.nodes[_removedNode.index - 1].visited)
            {
                edgedNeighbors.add(_mazeGraph.nodes[_removedNode.index - 1]);
            }
          }
          //right neighbor
          if ( _mazeGraph.edgeExists(_removedNode.index + 1, _removedNode.index) )
          {
            if (!_mazeGraph.nodes[_removedNode.index + 1].visited)
            {
              edgedNeighbors.add(_mazeGraph.nodes[_removedNode.index + 1]);
            }
          }
          //top neighbor
          if ( _mazeGraph.edgeExists(_removedNode.index - graphSize, _removedNode.index) )
          {
            if (!_mazeGraph.nodes[_removedNode.index - graphSize].visited)
            {
              edgedNeighbors.add(_mazeGraph.nodes[_removedNode.index - graphSize]);
            }
          }
          //bottom neighbor
          if ( _mazeGraph.edgeExists(_removedNode.index + graphSize, _removedNode.index) )
          {
            if (!_mazeGraph.nodes[_removedNode.index + graphSize].visited)
            {
              edgedNeighbors.add(_mazeGraph.nodes[_removedNode.index + graphSize]);
            }
          }

          return edgedNeighbors;
      }


    
      public void run(String filename) throws IOException {
  
        // read the input file to extract relevant information about the maze
        String[] readFile = parse(filename);
        int mazeSize = Integer.parseInt(readFile[0]);
        int numNodes = mazeSize*mazeSize;
        String mazeData = readFile[1];
    
        // construct a maze based on the information read from the file
        Graph mazeGraph = buildGraph(mazeData, numNodes);
    
        // do something here to solve the maze
        Node[] inSolutionNodes = solve(mazeGraph);

        int i = 0;
        while ( !(inSolutionNodes[i] == null) ) 
        {
            inSolutionNodes[i].inSolution = true;
            i++;
        }
        
        // print out the final maze with the solution path
        printMaze(mazeGraph.nodes, mazeData, mazeSize);
      }
  
      // prints out the maze in the format used for HW8
      // includes the final path from entrance to exit, if one has been recorded,
      // and which cells have been visited, if this has been recorded
      public void printMaze(Node[] mazeCells, String mazeData, int mazeSize) {
    
        int ind = 0;
        int inputCtr = 0;
    
        System.out.print("+");
        for(int i = 0; i < mazeSize; i++) {
            System.out.print("--+");
        }
        System.out.println();
    
        for(int i = 0; i < mazeSize; i++) {
            if(i == 0) System.out.print(" ");
            else System.out.print("|");
    
            for(int j = 0; j < mazeSize; j++) {
            System.out.print(mazeCells[ind] + "" + mazeCells[ind] +  mazeData.charAt(inputCtr));
            inputCtr++;
            ind++;
            }
            System.out.println();
    
            System.out.print("+");
            for(int j = 0; j < mazeSize; j++) {
            System.out.print(mazeData.charAt(inputCtr) + "" +  mazeData.charAt(inputCtr) + "+");
            inputCtr++;
            }
            System.out.println();
        }
      
      }
  
      // reads in a maze from an appropriately formatted file (this matches the format of the 
      // mazes you generated in HW8)
      // returns an array of Strings, where position 0 stores the size of the maze grid (i.e., the
      // length/width of the grid) and position 1 minimal information about which walls exist
      public String[] parse(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
    
        // determine size of maze
        int size = 0;
        int nextChar = fr.read();
        while(nextChar >= 48 && nextChar <= 57) {
            size = 10*size + nextChar - 48;
            nextChar = fr.read();
        }
    
        String[] result = new String[2];
        result[0] = size + "";
        result[1] = "";
    
    
        // skip over up walls on first row
        for(int j = 0; j < size; j++) {
            fr.read();
            fr.read();
            fr.read();
        }
        fr.read();
        fr.read();
    
        for(int i = 0; i < size; i++) {
            // skip over left wall on each row
            fr.read();
            
            for(int j = 0; j < size; j++) {
            // skip over two spaces for the cell
            fr.read();
            fr.read();
    
            // read wall character
            nextChar = fr.read();
            result[1] = result[1] + (char)nextChar;
    
            }
            // clear newline character at the end of the row
            fr.read();
            
            // read down walls on next row of input file
            for(int j = 0; j < size; j++)  {
            // skip over corner
            fr.read();
            
            //skip over next space, then handle wall
            fr.read();
            nextChar = fr.read();
            result[1] = result[1] + (char)nextChar;
            }
    
            // clear last wall and newline character at the end of the row
            fr.read();
            fr.read();
            
        }
    
        return result;
      }
      
      public Graph buildGraph(String maze, int numNodes) {
  
        Graph mazeGraph = new Graph(numNodes);
        int size = (int)Math.sqrt(numNodes);
    
        int mazeInd = 0;
        for(int i = 0; i < size; i++) {
            // create edges for right walls in row i
            for(int j = 0; j < size; j++) {
                char nextChar = maze.charAt(mazeInd);
                mazeInd++;
                if(nextChar == ' ') {
                    // add an edge corresponding to a right wall, using the indexing convention 
                    // for nodes
                    mazeGraph.addEdge(size*i + j, size*i + j + 1);
                }
            }
    
            // create edges for down walls below row i
            for(int j = 0; j < size; j++)  {
                char nextChar = maze.charAt(mazeInd);
                mazeInd++;
                if(nextChar == ' ') {
                    // add an edge corresponding to a down wall, using the indexing convention
                    // for nodes
                    mazeGraph.addEdge(size*i + j, size*(i+1) + j);
                }
            }    
        }
    
        return mazeGraph;
      }
         
      public static void main(String [] args) {
        if(args.length < 1) {
            System.out.println("USAGE: java MazeSolver <filename>");
        }
        else{
            try{
                new MazeSolver().run(args[0]);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
      }
  
  }