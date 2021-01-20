package ObjectLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Vertex {
    private int NodeID;
    private HashMap<Integer, Integer> Connections;
    private HashSet<Integer> inboundVertices;

    public Vertex(int id) {
        NodeID = id;
        Connections = new HashMap<Integer, Integer>();
        inboundVertices = new HashSet<>();
    }

    public int getNodeID() {
        return NodeID;
    }

    public HashMap<Integer, Integer> getConnections() {
        return Connections;
    }

    public void setConnections(HashMap<Integer, Integer> connections) {
        Connections = connections;
    }

    public HashSet<Integer> getInboundVertices() {
        return inboundVertices;
    }

    public void addInboundVertex(int id){
        inboundVertices.add(id);
    }
}
