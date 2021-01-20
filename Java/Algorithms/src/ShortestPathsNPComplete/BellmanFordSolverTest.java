package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BellmanFordSolverTest {

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
    void generateSolutionMatrix() {
        BellmanFordSolver bs = new BellmanFordSolver(testGraph, 1);
        Assertions.assertEquals(bs.generateSolutionMatrix(), "Negative Cycle Detected");

        BellmanFordSolver bs2 = new BellmanFordSolver(testGraph2, 4);
        Assertions.assertEquals(bs2.generateSolutionMatrix(), "Success");
        Assertions.assertArrayEquals(bs2.getShortestPaths(), new long[]{-2,-1,2,0});

        BellmanFordSolver bs3 = new BellmanFordSolver(testGraph3, 1);
        Assertions.assertEquals(bs3.generateSolutionMatrix(), "Success");
        Assertions.assertArrayEquals(bs3.getShortestPaths(), new long[]{0,2,1,0,2});

        BellmanFordSolver bs4 = new BellmanFordSolver(testGraph4, 3);
        Assertions.assertEquals(bs4.generateSolutionMatrix(), "Negative Cycle Detected");
    }
}