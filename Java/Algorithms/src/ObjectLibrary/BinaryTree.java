package ObjectLibrary;

import com.sun.source.tree.Tree;

public class BinaryTree {
    //The tree itself should have a root

    private TreeNode root;

    public BinaryTree(){
        this.root = new TreeNode();
    }
}

class TreeNode {

    //Nodes of a binary tree should know their parent, both left and right child, and value
    private TreeNode parent;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private int key;

    public TreeNode(int key){
        this.key = key;
    }
    public TreeNode(int key, TreeNode parent){
        this.key = key;
        this.parent = parent;
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
    public TreeNode getParent() {
        return parent;
    }
    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}