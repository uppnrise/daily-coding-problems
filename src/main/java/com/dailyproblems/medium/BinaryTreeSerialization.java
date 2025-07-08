/**
 * Problem: Binary Tree Serialization and Deserialization - Google Interview Question
 * Source: dailycodingproblem.com
 * Date: July 8, 2025
 * Difficulty: Medium
 * 
 * Problem Statement:
 * Given the root to a binary tree, implement serialize(root), which serializes the tree 
 * into a string, and deserialize(s), which deserializes the string back into the tree.
 * 
 * For example, given the following Node class:
 * 
 * class Node:
 *     def __init__(self, val, left=None, right=None):
 *         self.val = val
 *         self.left = left
 *         self.right = right
 * 
 * The following test should pass:
 * node = Node('root', Node('left', Node('left.left')), Node('right'))
 * assert deserialize(serialize(node)).left.left.val == 'left.left'
 * 
 * Example:
 * Input Tree:
 *       root
 *      /    \
 *    left   right
 *   /
 * left.left
 * 
 * Serialized: "root,left,left.left,null,null,null,right,null,null"
 * Deserialize back to original tree structure
 * 
 * Approach:
 * 1. Serialize: Use preorder traversal (root, left, right) and include null markers
 * 2. Deserialize: Reconstruct tree using the same preorder pattern
 * 3. Use a delimiter (comma) to separate values and "null" for empty nodes
 * 
 * Alternative approaches:
 * - Level-order (BFS) serialization
 * - Postorder traversal
 * - JSON-like format with nested structures
 * 
 * Time Complexity: O(n) for both serialize and deserialize
 * Space Complexity: O(n) for the serialized string and recursion stack
 * 
 * Edge Cases Considered:
 * - Empty tree (null root)
 * - Single node tree
 * - Only left children (skewed tree)
 * - Only right children (skewed tree)
 * - Values containing special characters
 * - Numeric vs string values
 */

package com.dailyproblems.medium;

import java.util.*;

public class BinaryTreeSerialization {
    
    /**
     * Definition for a binary tree node
     */
    public static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(String val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
        
        TreeNode(String val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public String toString() {
            return val;
        }
    }
    
    private static final String NULL_MARKER = "null";
    private static final String DELIMITER = ",";
    
    /**
     * Serializes a binary tree to a string using preorder traversal
     * @param root - the root of the binary tree
     * @return String - serialized representation of the tree
     * 
     * Algorithm:
     * 1. Use preorder traversal (root -> left -> right)
     * 2. For each node, add its value to the result
     * 3. For null nodes, add a special marker ("null")
     * 4. Join all values with a delimiter
     * 
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(n) - for the result string and recursion stack
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NULL_MARKER).append(DELIMITER);
            return;
        }
        
        // Preorder: root, left, right
        sb.append(node.val).append(DELIMITER);
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    /**
     * Deserializes a string back to a binary tree
     * @param data - serialized string representation
     * @return TreeNode - root of the reconstructed tree
     * 
     * Algorithm:
     * 1. Split the string by delimiter to get array of values
     * 2. Use an index to track current position in array
     * 3. Recursively build tree using preorder pattern
     * 4. When encountering "null", return null
     * 5. Otherwise, create node and recursively build left and right subtrees
     * 
     * Time Complexity: O(n) - process each value once
     * Space Complexity: O(n) - for recursion stack and input array
     */
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        
        String[] values = data.split(DELIMITER);
        int[] index = {0}; // Use array to pass index by reference
        return deserializeHelper(values, index);
    }
    
    private TreeNode deserializeHelper(String[] values, int[] index) {
        if (index[0] >= values.length) {
            return null;
        }
        
        String val = values[index[0]++];
        if (NULL_MARKER.equals(val)) {
            return null;
        }
        
        // Create node and recursively build left and right subtrees
        TreeNode node = new TreeNode(val);
        node.left = deserializeHelper(values, index);
        node.right = deserializeHelper(values, index);
        
        return node;
    }
    
    /**
     * Alternative implementation using level-order traversal (BFS)
     * This approach might be more intuitive for some
     */
    public String serializeLevelOrder(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                sb.append(NULL_MARKER).append(DELIMITER);
            } else {
                sb.append(node.val).append(DELIMITER);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return sb.toString();
    }
    
    public TreeNode deserializeLevelOrder(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        
        String[] values = data.split(DELIMITER);
        if (values.length == 0 || NULL_MARKER.equals(values[0])) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int index = 1;
        while (!queue.isEmpty() && index < values.length) {
            TreeNode node = queue.poll();
            
            // Process left child
            if (index < values.length) {
                String leftVal = values[index++];
                if (!NULL_MARKER.equals(leftVal)) {
                    node.left = new TreeNode(leftVal);
                    queue.offer(node.left);
                }
            }
            
            // Process right child
            if (index < values.length) {
                String rightVal = values[index++];
                if (!NULL_MARKER.equals(rightVal)) {
                    node.right = new TreeNode(rightVal);
                    queue.offer(node.right);
                }
            }
        }
        
        return root;
    }
    
    /**
     * Helper method to print tree structure for visualization
     */
    public void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        System.out.println("Tree structure (level-order):");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    System.out.print("null ");
                } else {
                    System.out.print(node.val + " ");
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to verify tree structure matches expected pattern
     */
    public boolean verifyTreeStructure(TreeNode root, String expectedPath, String expectedValue) {
        TreeNode current = root;
        
        for (char direction : expectedPath.toCharArray()) {
            if (current == null) {
                return false;
            }
            
            if (direction == 'L' || direction == 'l') {
                current = current.left;
            } else if (direction == 'R' || direction == 'r') {
                current = current.right;
            }
        }
        
        return current != null && expectedValue.equals(current.val);
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        BinaryTreeSerialization solution = new BinaryTreeSerialization();
        
        System.out.println("=== Binary Tree Serialization & Deserialization ===\n");
        
        // Test case 1: Example from problem statement
        System.out.println("Test 1: Original example");
        TreeNode node = new TreeNode("root", 
                           new TreeNode("left", 
                               new TreeNode("left.left"), null), 
                           new TreeNode("right"));
        
        System.out.println("Original tree:");
        solution.printTree(node);
        
        String serialized = solution.serialize(node);
        System.out.println("Serialized: " + serialized);
        
        TreeNode deserialized = solution.deserialize(serialized);
        System.out.println("Deserialized tree:");
        solution.printTree(deserialized);
        
        // Verify the assertion from problem statement
        boolean testPassed = solution.verifyTreeStructure(deserialized, "LL", "left.left");
        System.out.println("Test assertion passed: " + testPassed);
        System.out.println("deserialized.left.left.val = " + 
                          (deserialized.left != null && deserialized.left.left != null ? 
                           deserialized.left.left.val : "null"));
        System.out.println();
        
        // Test case 2: Empty tree
        System.out.println("Test 2: Empty tree");
        TreeNode emptyTree = null;
        String emptySeralized = solution.serialize(emptyTree);
        TreeNode emptyDeserialized = solution.deserialize(emptySeralized);
        System.out.println("Empty tree serialized: '" + emptySeralized + "'");
        System.out.println("Empty tree deserialized: " + emptyDeserialized);
        System.out.println();
        
        // Test case 3: Single node
        System.out.println("Test 3: Single node");
        TreeNode singleNode = new TreeNode("single");
        String singleSerialized = solution.serialize(singleNode);
        TreeNode singleDeserialized = solution.deserialize(singleSerialized);
        System.out.println("Single node serialized: " + singleSerialized);
        System.out.println("Single node deserialized: " + singleDeserialized.val);
        System.out.println();
        
        // Test case 4: Left-skewed tree
        System.out.println("Test 4: Left-skewed tree");
        TreeNode leftSkewed = new TreeNode("1", 
                                  new TreeNode("2", 
                                      new TreeNode("3"), null), null);
        
        String leftSkewedSerialized = solution.serialize(leftSkewed);
        TreeNode leftSkewedDeserialized = solution.deserialize(leftSkewedSerialized);
        System.out.println("Left-skewed serialized: " + leftSkewedSerialized);
        System.out.println("Left-skewed deserialized:");
        solution.printTree(leftSkewedDeserialized);
        System.out.println();
        
        // Test case 5: Right-skewed tree
        System.out.println("Test 5: Right-skewed tree");
        TreeNode rightSkewed = new TreeNode("1", null,
                                   new TreeNode("2", null,
                                       new TreeNode("3")));
        
        String rightSkewedSerialized = solution.serialize(rightSkewed);
        TreeNode rightSkewedDeserialized = solution.deserialize(rightSkewedSerialized);
        System.out.println("Right-skewed serialized: " + rightSkewedSerialized);
        System.out.println("Right-skewed deserialized:");
        solution.printTree(rightSkewedDeserialized);
        System.out.println();
        
        // Test case 6: Complete binary tree
        System.out.println("Test 6: Complete binary tree");
        TreeNode completeTree = new TreeNode("1",
                                    new TreeNode("2",
                                        new TreeNode("4"), new TreeNode("5")),
                                    new TreeNode("3",
                                        new TreeNode("6"), new TreeNode("7")));
        
        String completeSerialized = solution.serialize(completeTree);
        TreeNode completeDeserialized = solution.deserialize(completeSerialized);
        System.out.println("Complete tree serialized: " + completeSerialized);
        System.out.println("Complete tree deserialized:");
        solution.printTree(completeDeserialized);
        System.out.println();
        
        // Test case 7: Compare preorder vs level-order serialization
        System.out.println("Test 7: Comparing serialization methods");
        String preorderSerialized = solution.serialize(completeTree);
        String levelOrderSerialized = solution.serializeLevelOrder(completeTree);
        
        TreeNode preorderDeserialized = solution.deserialize(preorderSerialized);
        TreeNode levelOrderDeserialized = solution.deserializeLevelOrder(levelOrderSerialized);
        
        System.out.println("Preorder serialized: " + preorderSerialized);
        System.out.println("Level-order serialized: " + levelOrderSerialized);
        
        System.out.println("Both methods produce equivalent trees: " + 
                          treesEqual(preorderDeserialized, levelOrderDeserialized));
        System.out.println();
        
        // Performance analysis
        System.out.println("=== Algorithm Analysis ===");
        System.out.println("Preorder Serialization:");
        System.out.println("  ✓ Time: O(n) - visit each node once");
        System.out.println("  ✓ Space: O(n) - recursion stack + output string");
        System.out.println("  ✓ Simple recursive implementation");
        System.out.println("  ✓ Compact representation");
        System.out.println();
        
        System.out.println("Level-order Serialization:");
        System.out.println("  ✓ Time: O(n) - process each node once");
        System.out.println("  ✓ Space: O(w) where w is max width of tree");
        System.out.println("  ✓ More intuitive for some developers");
        System.out.println("  ✓ Better for wide trees (less memory for queue)");
        System.out.println();
        
        System.out.println("=== Key Insights ===");
        System.out.println("• Preorder traversal naturally reconstructs tree structure");
        System.out.println("• Null markers are essential for unambiguous deserialization");
        System.out.println("• String-based approach handles any data type in node values");
        System.out.println("• Both serialization methods preserve complete tree structure");
        System.out.println("• Consider delimiter conflicts when values contain special chars");
    }
    
    /**
     * Helper method to check if two trees are structurally and value-wise equal
     */
    private static boolean treesEqual(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        
        return t1.val.equals(t2.val) && 
               treesEqual(t1.left, t2.left) && 
               treesEqual(t1.right, t2.right);
    }
}
