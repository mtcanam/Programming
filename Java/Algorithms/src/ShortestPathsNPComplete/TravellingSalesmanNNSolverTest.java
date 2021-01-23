package ShortestPathsNPComplete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class TravellingSalesmanNNSolverTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void testTravellingSalesmanNNSolver(){
        DecimalFormat df = new DecimalFormat("0");
        TravellingSalesmanNNSolver tsn = new TravellingSalesmanNNSolver("TSPNNDataTest");
        Assertions.assertEquals("15", df.format(tsn.calculateTotalDistance()));
        TravellingSalesmanNNSolver tsn2 = new TravellingSalesmanNNSolver("TSPNNDataTest2");
        Assertions.assertEquals("2470", df.format(tsn2.calculateTotalDistance()));
        TravellingSalesmanNNSolver tsnReal = new TravellingSalesmanNNSolver("TSPNNData");
        System.out.println((tsnReal.calculateTotalDistance()));
    }
}