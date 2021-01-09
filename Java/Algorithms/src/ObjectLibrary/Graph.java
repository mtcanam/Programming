package ObjectLibrary;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<Integer, Vertex> graphNodes;
    private int nodeCount;

    public Graph(){
        graphNodes = new HashMap<Integer, Vertex>();
        nodeCount = 0;
    }

    public Graph(Map<Integer, Vertex> map){
        graphNodes = new HashMap<Integer, Vertex>(map);
        nodeCount = graphNodes.size();
    }

    public Map<Integer, Vertex> getGraphNodes() {
        return graphNodes;
    }

    public void setGraphNodes(Map<Integer, Vertex> graphNodes) {
        this.graphNodes = graphNodes;
        nodeCount = this.graphNodes.size();
    }

    public int getNodeCount() {
        return nodeCount;
    }


}

