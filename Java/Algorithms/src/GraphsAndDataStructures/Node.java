package GraphsAndDataStructures;

import java.util.ArrayList;

public class Node {
    private int nodeID;
    private boolean visited;

    public Node(){

    }
    public Node(int nodeID){
        this.nodeID = nodeID;
        this.visited = false;
    }

    public int getNodeID() {
        return this.nodeID;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
