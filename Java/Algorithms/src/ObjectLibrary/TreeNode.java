package ObjectLibrary;

public class TreeNode {

    //Nodes of a binary tree should know their parent, both left and right child, and value
    private TreeNode leftChild;
    private TreeNode rightChild;
    private int key;

    public TreeNode() {

    }

    public TreeNode(int key) {
        this.key = key;
        leftChild = null;
        rightChild = null;
    }

    public int getKey() {
        return key;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
