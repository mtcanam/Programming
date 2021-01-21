package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GraphReaderTest {

    private static Graph testGraph;
    private static Graph testGraph2;
    private static Graph testGraph3;
    private static Graph testGraph4;


    @BeforeEach
    void setUp() {
        testGraph = new Graph();
        Vertex vert1 = new Vertex(1);
        HashMap<Integer, Integer> conn1 = new HashMap<>();
        conn1.put(2,1);
        conn1.put(3,4);
        vert1.setConnections(conn1);
        Vertex vert2 = new Vertex(2);
        HashMap<Integer, Integer> conn2 = new HashMap<>();
        conn2.put(4,2);
        vert2.setConnections(conn2);
        Vertex vert3 = new Vertex(3);
        HashMap<Integer, Integer> conn3 = new HashMap<>();
        conn3.put(4,3);
        vert3.setConnections(conn3);
        Vertex vert4 = new Vertex(4);
        HashMap<Integer, Integer> conn4 = new HashMap<>();
        conn4.put(1,-4);
        vert4.setConnections(conn4);
        testGraph.setAndMergeNode(1,vert1);
        testGraph.setAndMergeNode(2,vert2);
        testGraph.setAndMergeNode(3,vert3);
        testGraph.setAndMergeNode(4,vert4);

        testGraph2 = new Graph();
        vert1 = new Vertex(1);
        conn1 = new HashMap<>();
        conn1.put(2,1);
        conn1.put(3,4);
        vert1.setConnections(conn1);
        vert2 = new Vertex(2);
        conn2 = new HashMap<>();
        conn2.put(4,2);
        vert2.setConnections(conn2);
        vert3 = new Vertex(3);
        conn3 = new HashMap<>();
        conn3.put(4,3);
        vert3.setConnections(conn3);
        vert4 = new Vertex(4);
        conn4 = new HashMap<>();
        conn4.put(1,-2);
        vert4.setConnections(conn4);
        testGraph2.setAndMergeNode(1,vert1);
        testGraph2.setAndMergeNode(2,vert2);
        testGraph2.setAndMergeNode(3,vert3);
        testGraph2.setAndMergeNode(4,vert4);

        testGraph3 = new Graph();
        vert1 = new Vertex(1);
        conn1 = new HashMap<>();
        conn1.put(2,2);
        conn1.put(5,3);
        vert1.setConnections(conn1);
        vert2 = new Vertex(2);
        conn2 = new HashMap<>();
        conn2.put(4,-2);
        vert2.setConnections(conn2);
        vert3 = new Vertex(3);
        conn3 = new HashMap<>();
        conn3.put(1,1);
        vert3.setConnections(conn3);
        vert4 = new Vertex(4);
        conn4 = new HashMap<>();
        conn4.put(1,4);
        conn4.put(3,1);
        conn4.put(5,2);
        vert4.setConnections(conn4);
        Vertex vert5 = new Vertex(5);
        HashMap<Integer, Integer> conn5 = new HashMap<>();
        conn5.put(3,-1);
        vert5.setConnections(conn4);
        testGraph3.setAndMergeNode(1,vert1);
        testGraph3.setAndMergeNode(2,vert2);
        testGraph3.setAndMergeNode(3,vert3);
        testGraph3.setAndMergeNode(4,vert4);
        testGraph3.setAndMergeNode(5,vert5);

        testGraph4 = new Graph();
        vert1 = new Vertex(1);
        conn1 = new HashMap<>();
        conn1.put(2,2);
        conn1.put(5,3);
        vert1.setConnections(conn1);
        vert2 = new Vertex(2);
        conn2 = new HashMap<>();
        conn2.put(4,-2);
        vert2.setConnections(conn2);
        vert3 = new Vertex(3);
        conn3 = new HashMap<>();
        conn3.put(1,1);
        vert3.setConnections(conn3);
        vert4 = new Vertex(4);
        conn4 = new HashMap<>();
        conn4.put(1,4);
        conn4.put(3,1);
        conn4.put(5,-1);
        vert4.setConnections(conn4);
        vert5 = new Vertex(5);
        conn5 = new HashMap<>();
        conn5.put(3,-1);
        vert5.setConnections(conn4);
        testGraph4.setAndMergeNode(1,vert1);
        testGraph4.setAndMergeNode(2,vert2);
        testGraph4.setAndMergeNode(3,vert3);
        testGraph4.setAndMergeNode(4,vert4);
        testGraph4.setAndMergeNode(5,vert5);

    }

    @Test
    void readDataAndFormGraph(){
        Graph g1 = new Graph();
        Graph g2 = new Graph();
        Graph g3 = new Graph();
        Graph g4 = new Graph();

        GraphReader gr1 = new GraphReader("APSPTest1");
        try {
            g1 = gr1.readDataAndFormGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphReader gr2 = new GraphReader("APSPTest2");
        try {
            g2 = gr2.readDataAndFormGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphReader gr3 = new GraphReader("APSPTest3");
        try {
            g3 = gr3.readDataAndFormGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphReader gr4 = new GraphReader("APSPTest4");
        try {
            g4 = gr4.readDataAndFormGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}