/**
 * Problem: Two Sum - Problem #1
 * Source: dailycodingproblem.com (Example)
 * Date: June 30, 2025
 * Difficulty: Easy
 * 
 * Problem Statement:
 * Given an array of integers and a target sum, return the indices of two numbers
 * in the array that add up to the target sum. You can assume that there is
 * exactly one solution, and you cannot use the same element twice.
 * 
 * Example:
 * Input: nums = [2, 7, 11, 15], target = 9
 * Output: [0, 1] (because nums[0] + nums[1] = 2 + 7 = 9)
 * 
 * Constraints:
 * - 2 <= nums.length <= 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * - Only one valid answer exists
 * 
 * Approach:
 * Use a HashMap to store the complement of each number as we iterate through the array.
 * For each number, check if its complement (target - current number) exists in the map.
 * If it exists, we found our pair. If not, add the current number and its index to the map.
 * 
 * 1. Create a HashMap to store number -> index mapping
 * 2. Iterate through the array
 * 3. For each number, calculate complement = target - current number
 * 4. Check if complement exists in the map
 * 5. If yes, return [complement_index, current_index]
 * 6. If no, add current number and index to the map
 * 
 * Time Complexity: O(n) - single pass through the array
 * Space Complexity: O(n) - HashMap can store up to n elements
 * 
 * Edge Cases Considered:
 * - Array with exactly 2 elements
 * - Negative numbers in the array
 * - Target sum is 0
 */

package com.dailyproblems.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class TwoSum {
    
    /**
     * Finds two indices in the array that sum to the target value
     * @param nums - array of integers
     * @param target - target sum value
     * @return int[] - array containing the two indices
     */
    public int[] twoSum(int[] nums, int target) {
        // HashMap to store number -> index mapping
        Map<Integer, Integer> numToIndex = new HashMap<>();
        
        // Iterate through the array
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // Check if complement exists in the map
            if (numToIndex.containsKey(complement)) {
                // Found the pair, return indices
                return new int[]{numToIndex.get(complement), i};
            }
            
            // Add current number and its index to the map
            numToIndex.put(nums[i], i);
        }
        
        // This should never happen given the problem constraints
        throw new IllegalArgumentException("No two sum solution exists");
    }
    
    /**
     * Brute force approach (less efficient but easier to understand)
     * @param nums - array of integers
     * @param target - target sum value
     * @return int[] - array containing the two indices
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        // Check every pair of numbers
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        
        // This should never happen given the problem constraints
        throw new IllegalArgumentException("No two sum solution exists");
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        
        // Test case 1: Basic example
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = solution.twoSum(nums1, target1);
        System.out.println("Test 1: " + Arrays.toString(result1)); // Expected: [0, 1]
        
        // Test case 2: Numbers at the end
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.println("Test 2: " + Arrays.toString(result2)); // Expected: [1, 2]
        
        // Test case 3: Same number twice
        int[] nums3 = {3, 3};
        int target3 = 6;
        int[] result3 = solution.twoSum(nums3, target3);
        System.out.println("Test 3: " + Arrays.toString(result3)); // Expected: [0, 1]
        
        // Test case 4: With negative numbers
        int[] nums4 = {-1, -2, -3, -4, -5};
        int target4 = -8;
        int[] result4 = solution.twoSum(nums4, target4);
        System.out.println("Test 4: " + Arrays.toString(result4)); // Expected: [2, 4] (-3 + -5 = -8)
        
        // Verify with brute force approach
        System.out.println("\\nVerifying with brute force:");
        int[] bruteForcResult = solution.twoSumBruteForce(nums1, target1);
        System.out.println("Brute Force Test 1: " + Arrays.toString(bruteForcResult));
    }
}
