/*
 * Name: Vikram Venkatesh
 * PID:  A16811165
 * Source: Vikram Venkatesh PA6 DSC30 at UC San Diego HDSI
 */

import java.util.*;

/**
 * Binary search tree implementation.
 *
 * @author Vikram Venkatesh
 * @since  {05/04/2022}
 */
public class BSTree<T extends Comparable<? super T>> {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.left = left;
            this.right = right;
            this.dataList = dataList;
            this.key = key;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = new LinkedList<>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return this.key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            return this.left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            return this.right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            return this.dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) {
            this.left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) {
            this.right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            this.dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            this.dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            int prevSize = this.dataList.size();
            this.dataList.remove(data);
            if (this.dataList.size() >= prevSize) {
                return false;
            }
            return true;
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        this.root = null;
        this.nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        if (this.root == null) {
            return null;
        }
        return this.root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return this.nelems;
    }

    /**
     * Insert a key into BST
     *
     * @param key
     * @return true if insertion is successful and false otherwise
     * @throws NullPointerException if key is null
     */
    public boolean insert(T key) {
        boolean rtrBool;
        if (key == null) {
            throw new NullPointerException();
        } else if (root == null) {
            LinkedList emptyLinkedList = new LinkedList();
            // inserting an empty linked list as the value for the key to be inserted
            root = new BSTNode(null, null, emptyLinkedList, key);
            rtrBool = true;
        } else {
            // calling helper method
            rtrBool = insertHelper(root, key);
        }
        if (rtrBool) {
            // incrementing size if insertion is done
            this.nelems++;
        }
        return rtrBool;
    }

    /**
     * Helper method for insert
     *
     * @param node node to be inserted
     * @param key
     * @return true if insertion is possible else false
     */
    private boolean insertHelper(BSTNode node, T key) {
        // if key already exists, return false
        if (key.compareTo(node.getKey()) == 0) {
            return false;
            // if key is less than node's key
        } else if (key.compareTo(node.getKey()) < 0) {
            if (node.getLeft() == null) {
                BSTNode leftNode = new BSTNode(null, null, key);
                // setting node's left pointer to leftNode
                node.setleft(leftNode);
                return true;
            } else {
                // recursive call
                return insertHelper(node.getLeft(), key);
            }
            // if key is greater than node's key
        } else {
            if (node.getRight() == null) {
                BSTNode rightNode = new BSTNode(null, null, key);
                // setting node's right pointer to rightNode
                node.setright(rightNode);
                return true;
            } else {
                // recursive call
                return insertHelper(node.getRight(), key);
            }
        }
    }

    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            return false;
        }
        BSTNode findNode = findKeyHelper(root, key);
        // return true if key is found (not null) else false (null)
        if (findNode != null) {
            return true;
        }
        return false;
    }

    /**
     * Helper method for findKey
     *
     * @param node
     * @param key
     * @return node if found
     */
    private BSTNode findKeyHelper(BSTNode node, T key) {
        if (node == null) {
            return null;
        }
        // base case to return node if node is found
        if (key.compareTo(node.getKey()) == 0) {
            return node;
        }
        // recursive call for left side
        if (key.compareTo(node.getKey()) < 0) {
            return findKeyHelper(node.getLeft(), key);
            // recursive call for right side
        } else {
            return findKeyHelper(node.getRight(), key);
        }
    }

    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If either key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        if (key == null || data == null) {
            throw new NullPointerException();
        }
        if (root != null) {
            BSTNode findNode = findKeyHelper(root, key);
            if (findNode != null) {
                // adding data to key's value (linked list)
                findNode.getDataList().add(data);
            } else {
                throw new IllegalArgumentException();
            }
            // throwing exception if root is null
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!findKey(key)) {
            throw new IllegalArgumentException();
        }
        // getting node using findKeyHelper
        BSTNode findNode = findKeyHelper(root, key);
        return findNode.getDataList();
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        if (root == null) {
            return -1;
        }
        // calling helper function
        return findHeightHelper(root);
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {
        //base case
        if (root == null) {
            return -1;
        }
        // recursive call for left side height
        int left = findHeightHelper(root.getLeft()) + 1;
        // recursive call for right side height
        int right = findHeightHelper(root.getRight()) + 1;
        // returning max of left and right side heights
        if (left >= right) {
            return left;
        } else {
            return right;
        }
    }
}