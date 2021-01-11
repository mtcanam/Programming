package ObjectLibrary;


public class HuffmanTree {
    //The tree itself should have a root and methods to determine the overall structure of the tree
    //The input to this tree will be strings of binary, like "0010101"

    private TreeNode root;
    private long totalWeight;
    private int maxDepth;
    private int minDepth;

    public HuffmanTree(){
        root = new TreeNode();
        totalWeight = 0;
        maxDepth = 0;
        minDepth = 0;
    }
    public HuffmanTree(int val){
        root = new TreeNode();
        root.setRightChild(new TreeNode(val));
        totalWeight = val;
        maxDepth = 1;
        minDepth = 0;
    }

    public HuffmanTree(HuffmanTree t1, HuffmanTree t2){
        root = new TreeNode();
        totalWeight = t1.getTotalWeight() + t2.getTotalWeight();
        int depthAdd = 0;
        //If the subtrees are complete (neither child is null), we point to that root as the child
        //As we default all the initial leaves to the right subtree, we know that will never be null
        //If we only have one child, point directly to that child
        if (t1.getRoot().getLeftChild() == null){
            root.setRightChild(t1.getRoot().getRightChild());
        }else{
            root.setRightChild(t1.getRoot());
            depthAdd = 1;
        }
        if (t2.getRoot().getLeftChild() == null){
            root.setLeftChild(t2.getRoot().getRightChild());
        }else{
            root.setLeftChild(t2.getRoot());
            depthAdd = 1;
        }
        maxDepth = Math.max(t1.getMaxDepth(), t2.getMaxDepth()) + depthAdd;
        minDepth = Math.min(t1.getMinDepth(), t2.getMinDepth()) + 1;
    }

    public TreeNode getRoot() {
        return root;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public int getMinDepth() {
        return minDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}

