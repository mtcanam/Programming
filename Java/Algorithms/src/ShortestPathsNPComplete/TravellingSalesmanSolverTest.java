package ShortestPathsNPComplete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class TravellingSalesmanSolverTest {

    TravellingSalesmanSolver tsp;
    TravellingSalesmanSolver tsp2;
    TravellingSalesmanSolver tsp3;
    TravellingSalesmanSolver tspReal;

    @BeforeEach
    void setUp() {
        tsp = new TravellingSalesmanSolver("TSPTest1");
        tsp2 = new TravellingSalesmanSolver("TSPTest2");
        tsp3 = new TravellingSalesmanSolver("TSPTest3");
        tspReal = new TravellingSalesmanSolver("TSPData");
    }

    @Test
    void TravellingSalesmanSolver(){
        DecimalFormat df = new DecimalFormat("0.00");
        tsp.populateSubProblems();
        tsp2.populateSubProblems();
        tsp3.populateSubProblems();
        tspReal.populateSubProblems();
        Assertions.assertEquals("10.24", df.format(tsp.getMinimumCostTourValue()));
        Assertions.assertEquals("12.36", df.format(tsp2.getMinimumCostTourValue()));
        Assertions.assertEquals("14.00", df.format(tsp3.getMinimumCostTourValue()));
        System.out.println(tspReal.getMinimumCostTourValue());
    }
}