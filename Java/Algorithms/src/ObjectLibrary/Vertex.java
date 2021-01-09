package ObjectLibrary;

import java.util.HashMap;

public class Vertex {
    private int NodeID;
    private HashMap<Integer, Integer> Connections;

    public Vertex(int id) {
        NodeID = id;
        Connections = new HashMap<Integer, Integer>();
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
}
