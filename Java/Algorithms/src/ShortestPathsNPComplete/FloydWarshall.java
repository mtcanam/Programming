package ShortestPathsNPComplete;
/*
Solution:
An algorithm to solve the shortest path between all pairs of vertices in a graph
This is of time complexity O(n3), which is pretty decent compared to iterative versions of bellman ford or dikstra
Interesting method is to assign arbitrary numbers to the vertices
Idea here is to have 3 nested loops:
1. Usable vertex limiter (ie for k=2, we can use vertex 1 and 2)
2. Vertex 1 iterator (i)
3. Vertex 2 iterator (j)
Through the above, we'd essentially try to construct paths between all vertices using only vertex 1; then 1 and 2; 1, 2, 3; etc.
We need a 3d array of solutions (to match the 3 loops)
There are two cases for a new solution:
1. Take the path that was solved between vertex i and vertex j with the k-1 set of usable vertices
2. Take the path from i to k and add it to the path from k to j with the k-1 set of usable vertices
Obviously, there may not be a path between i and j using the vertex set. This would lead to a solution of +inf.

Reconstruction:
Need an extra array B[][] that stores the max label of the internal nodes of a path from i to j.
Reset this to k every time we use case 2 as teh solution.
Can then recursively access this matrix to solve full paths.
*/
public class FloydWarshall {
}
