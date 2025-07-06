/**
 * Problem: Binary Tree Maximum Depth - Problem #104
 * Source: dailycodingproblem.com (Example)
 * Date: June 30, 2025
 * Difficulty: Medium
 * 
 * Problem Statement:
 * Given the root of a binary tree, return its maximum depth.
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 * 
 * Example:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * 
 * Constraints:
 * - The number of nodes in the tree is in the range [0, 10^4]
 * - -100 <= Node.val <= 100
 * 
 * Approach:
 * Use recursive depth-first search (DFS) to traverse the tree.
 * For each node, the maximum depth is 1 + max(left subtree depth, right subtree depth).
 * Base case: if the node is null, return 0.
 * 
 * 1. If root is null, return 0
 * 2. Recursively calculate left subtree depth
 * 3. Recursively calculate right subtree depth
 * 4. Return 1 + max(left depth, right depth)
 * 
 * Time Complexity: O(n) - visit each node once
 * Space Complexity: O(h) - recursion stack depth, where h is height of tree
 *                   O(log n) for balanced tree, O(n) for skewed tree
 * 
 * Edge Cases Considered:
 * - Empty tree (root is null)
 * - Single node tree
 * - Completely unbalanced tree (linked list structure)
 */

package com.dailyproblems.medium;

public class BinaryTreeMaxDepth {
    
    /**
     * Definition for a binary tree node
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * Calculates the maximum depth of a binary tree using recursion
     * @param root - root node of the binary tree
     * @return int - maximum depth of the tree
     */
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursively calculate depth of left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // Return 1 (current node) + maximum of left and right depths
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    /**
     * Iterative approach using level-order traversal (BFS)
     * @param root - root node of the binary tree
     * @return int - maximum depth of the tree
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(w) where w is the maximum width of the tree
     */
    public int maxDepthIterative(TreeNode root) {
        if (root == null) return 0;
        
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            depth++;
            int levelSize = queue.size();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add children to queue for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return depth;
    }
    
    /**
     * Helper method to create a tree for testing
     */
    private TreeNode createSampleTree() {
        /*
         * Creates tree:     3
         *                  / \\
         *                 9   20
         *                    /  \\
         *                   15   7
         */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        BinaryTreeMaxDepth solution = new BinaryTreeMaxDepth();
        
        // Test case 1: Sample tree with depth 3
        TreeNode root1 = solution.createSampleTree();
        int result1 = solution.maxDepth(root1);
        System.out.println("Test 1 (Recursive): " + result1); // Expected: 3
        
        int result1Iterative = solution.maxDepthIterative(root1);
        System.out.println("Test 1 (Iterative): " + result1Iterative); // Expected: 3
        
        // Test case 2: Empty tree
        TreeNode root2 = null;
        int result2 = solution.maxDepth(root2);
        System.out.println("Test 2 (Empty tree): " + result2); // Expected: 0
        
        // Test case 3: Single node
        TreeNode root3 = new TreeNode(1);
        int result3 = solution.maxDepth(root3);
        System.out.println("Test 3 (Single node): " + result3); // Expected: 1
        
        // Test case 4: Linear tree (like a linked list)
        TreeNode root4 = new TreeNode(1);
        root4.right = new TreeNode(2);
        root4.right.right = new TreeNode(3);
        root4.right.right.right = new TreeNode(4);
        int result4 = solution.maxDepth(root4);
        System.out.println("Test 4 (Linear tree): " + result4); // Expected: 4
    }
}
