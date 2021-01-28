package ObjectLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Vertex getNode(int id){
        return this.graphNodes.get(id);
    }

    //This method assumes we want to use the connections that already exist when setting vertex
    public void setAndMergeNode(int id, Vertex vert){
        //Need to check whether this vertex exists already
        if (graphNodes.containsKey(id)){
            //This vertex already exists, we can add the connections from the original vertex
            Vertex oldVert = graphNodes.get(id);
            HashMap<Integer, Integer> oldConn = oldVert.getConnections();
            HashMap<Integer, Integer> newConn = vert.getConnections();
            for (int key:oldConn.keySet()){
                newConn.put(key, oldConn.get(key));
            }
            vert.setConnections(newConn);
        }

        //When setting a node, need to do three things:
        //1. Calculate the inbound vertices for the added vertex
        for (Vertex mapVert : graphNodes.values()){
            HashMap<Integer, Integer> conn = mapVert.getConnections();
            if (conn.containsKey(id)){
                vert.addInboundVertex(mapVert.getNodeID());
            }
        }
        //2. Add this vertex to all of the other inbound vertices sets
        HashMap<Integer, Integer> conn = vert.getConnections();
        for (int vertID: conn.keySet()){
            if (graphNodes.containsKey(vertID)){
                Vertex mapVert = graphNodes.get(vertID);
                mapVert.addInboundVertex(id);
                graphNodes.put(vertID, mapVert);
            }
        }

        //3. Put the node in the map, incrementing the node count if new
        if (graphNodes.containsKey(id)) {
            graphNodes.put(id, vert);
        }else{
            graphNodes.put(id, vert);
            nodeCount++;
        }
    }

    //This method assumes that we want to overwrite vertices when adding
    public void setNode(int id, Vertex vert){
        //When setting a node, need to do three things:
        //1. Calculate the inbound vertices for the added vertex
        for (Vertex mapVert : graphNodes.values()){
            HashMap<Integer, Integer> conn = mapVert.getConnections();
            if (conn.containsKey(id)){
                vert.addInboundVertex(mapVert.getNodeID());
            }
        }
        //2. Add this vertex to all of the other inbound vertices sets
        HashMap<Integer, Integer> conn = vert.getConnections();
        for (int vertID: conn.keySet()){
            if (graphNodes.containsKey(vertID)){
                Vertex mapVert = graphNodes.get(vertID);
                mapVert.addInboundVertex(id);
                graphNodes.put(vertID, mapVert);
            }
        }
        //3. Put the node in the map, incrementing the node count if new
        if (graphNodes.containsKey(id)) {
            graphNodes.put(id, vert);
        }else{
            graphNodes.put(id, vert);
            nodeCount++;
        }
    }

    //This method assumes we want to use the connections that already exist when setting vertex
    public void setAndMergeNodeNoInboundVertices(int id, Vertex vert){
        //Need to check whether this vertex exists already
        if (graphNodes.containsKey(id)){
            //This vertex already exists, we can add the connections from the original vertex
            Vertex oldVert = graphNodes.get(id);
            HashMap<Integer, Integer> oldConn = oldVert.getConnections();
            HashMap<Integer, Integer> newConn = vert.getConnections();
            for (int key:oldConn.keySet()){
                newConn.put(key, oldConn.get(key));
            }
            vert.setConnections(newConn);
        }
        //Put the node in the map, incrementing the node count if new
        if (graphNodes.containsKey(id)) {
            graphNodes.put(id, vert);
        }else{
            graphNodes.put(id, vert);
            nodeCount++;
        }
    }

    public void setGraphNodes(Map<Integer, Vertex> graphNodes) {
        this.graphNodes = graphNodes;
        nodeCount = this.graphNodes.size();
    }

    public int getNodeCount() {
        return nodeCount;
    }


}

