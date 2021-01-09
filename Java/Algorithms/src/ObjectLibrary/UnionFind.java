package ObjectLibrary;
import java.util.*;

// class to represent a disjoint set
public class UnionFind
{
    private Map<Integer, Integer> parent = new HashMap();

    // stores the depth of trees
    private Map<Integer, Integer> rank = new HashMap();

    private long numSets = 0;

    // perform MakeSet operation
    public void makeSet(Integer[] input)
    {
        // create n disjoint sets (one for each item)
        for (int i : input)
        {
            parent.put(i, i);
            rank.put(i, 0);
            numSets++;
        }
    }

    // Find the root of the set in which element k belongs
    public int Find(int k)
    {
        // if k is not root
        if (parent.get(k) != k)
            // path compression
            parent.put(k, Find(parent.get(k)));

        return parent.get(k);
    }

    // Perform Union of two subsets
    public void Union(int a, int b)
    {
        // find root of the sets in which elements
        // x and y belongs
        int x = Find(a);
        int y = Find(b);

        // if x and y are present in same set
        if (x == y)
            return;

        // Always attach a smaller depth tree under the
        // root of the deeper tree.
        if (rank.get(x) > rank.get(y)) {
            parent.put(y, x);
        }
        else if (rank.get(x) < rank.get(y)) {
            parent.put(x, y);
        }
        else
        {
            parent.put(x, y);
            rank.put(y, rank.get(y) + 1);
        }
        numSets--;

    }

    public void structureCompression() {
        for (int key : parent.keySet()){
            // if key is not root
            if (parent.get(key) != key) {
                // path compression
                parent.put(key, Find(parent.get(key)));
            }
        }
    }

    public long getNumSets(){
        return numSets;
    }

    public static void printSets(int[] universe, UnionFind ds)
    {
        for (int i : universe)
            System.out.print(ds.Find(i) + " ");

        System.out.println();
    }
}