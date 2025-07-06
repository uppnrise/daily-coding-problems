/**
 * Problem: Two Numbers Sum to K - Google Interview Question
 * Source: dailycodingproblem.com
 * Date: June 30, 2025
 * Difficulty: Easy
 * 
 * Problem Statement:
 * Given a list of numbers and a number k, return whether any two numbers 
 * from the list add up to k.
 * 
 * Example:
 * Input: nums = [10, 15, 3, 7], k = 17
 * Output: true (because 10 + 7 = 17)
 * 
 * Constraints:
 * - The list can contain positive and negative integers
 * - The list can contain duplicates
 * - You cannot use the same element twice (same index)
 * - Return true if any pair sums to k, false otherwise
 * 
 * Approach:
 * Use a HashSet to store numbers we've seen so far. For each number, 
 * check if its complement (k - current number) exists in the set.
 * If it exists, we found a pair. If not, add current number to the set.
 * This achieves the "one pass" bonus requirement.
 * 
 * 1. Create a HashSet to store visited numbers
 * 2. Iterate through the array once
 * 3. For each number, calculate complement = k - current number
 * 4. Check if complement exists in the set
 * 5. If yes, return true (found pair)
 * 6. If no, add current number to the set
 * 7. If loop completes without finding pair, return false
 * 
 * Time Complexity: O(n) - single pass through the array
 * Space Complexity: O(n) - HashSet can store up to n elements
 * 
 * Edge Cases Considered:
 * - Empty array (should return false)
 * - Array with single element (should return false)
 * - Array with duplicate numbers
 * - Negative numbers in the array
 * - Target sum k is 0
 * - Same number used twice (e.g., [3, 3] with k=6)
 */

package com.dailyproblems.easy;

import java.util.HashSet;
import java.util.Set;

public class TwoNumbersSumToK {
    
    /**
     * Checks if any two numbers in the array sum to k (One Pass Solution)
     * @param nums - array of integers
     * @param k - target sum value
     * @return boolean - true if any two numbers sum to k
     */
    public boolean hasSum(int[] nums, int k) {
        // Handle edge cases
        if (nums == null || nums.length < 2) {
            return false;
        }
        
        // HashSet to store numbers we've seen
        Set<Integer> seen = new HashSet<>();
        
        // Single pass through the array
        for (int num : nums) {
            int complement = k - num;
            
            // Check if complement exists in seen numbers
            if (seen.contains(complement)) {
                return true; // Found a pair that sums to k
            }
            
            // Add current number to seen set
            seen.add(num);
        }
        
        // No pair found
        return false;
    }
    
    /**
     * Brute force approach for comparison (less efficient)
     * @param nums - array of integers
     * @param k - target sum value
     * @return boolean - true if any two numbers sum to k
     * 
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public boolean hasSumBruteForce(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        
        // Check every pair
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == k) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Alternative approach using sorted array and two pointers
     * @param nums - array of integers
     * @param k - target sum value
     * @return boolean - true if any two numbers sum to k
     * 
     * Time Complexity: O(n log n) - due to sorting
     * Space Complexity: O(1) - if sorting in place, O(n) if creating new array
     */
    public boolean hasSumTwoPointers(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        
        // Create a copy and sort it
        int[] sortedNums = nums.clone();
        java.util.Arrays.sort(sortedNums);
        
        // Two pointers approach
        int left = 0;
        int right = sortedNums.length - 1;
        
        while (left < right) {
            int sum = sortedNums[left] + sortedNums[right];
            
            if (sum == k) {
                return true;
            } else if (sum < k) {
                left++; // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
        
        return false;
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        TwoNumbersSumToK solution = new TwoNumbersSumToK();
        
        // Test case 1: Given example
        int[] nums1 = {10, 15, 3, 7};
        int k1 = 17;
        boolean result1 = solution.hasSum(nums1, k1);
        System.out.println("Test 1: " + result1); // Expected: true (10 + 7 = 17)
        
        // Test case 2: No valid pair
        int[] nums2 = {1, 2, 3, 4};
        int k2 = 10;
        boolean result2 = solution.hasSum(nums2, k2);
        System.out.println("Test 2: " + result2); // Expected: false
        
        // Test case 3: With negative numbers
        int[] nums3 = {-1, -2, -3, 4, 5};
        int k3 = 2;
        boolean result3 = solution.hasSum(nums3, k3);
        System.out.println("Test 3: " + result3); // Expected: true (-3 + 5 = 2)
        
        // Test case 4: Same number twice (duplicates)
        int[] nums4 = {3, 3, 4, 5};
        int k4 = 6;
        boolean result4 = solution.hasSum(nums4, k4);
        System.out.println("Test 4: " + result4); // Expected: true (3 + 3 = 6)
        
        // Test case 5: Single element array
        int[] nums5 = {5};
        int k5 = 10;
        boolean result5 = solution.hasSum(nums5, k5);
        System.out.println("Test 5: " + result5); // Expected: false
        
        // Test case 6: Empty array
        int[] nums6 = {};
        int k6 = 5;
        boolean result6 = solution.hasSum(nums6, k6);
        System.out.println("Test 6: " + result6); // Expected: false
        
        // Test case 7: Target sum is 0
        int[] nums7 = {-1, 0, 1, 2};
        int k7 = 0;
        boolean result7 = solution.hasSum(nums7, k7);
        System.out.println("Test 7: " + result7); // Expected: true (-1 + 1 = 0)
        
        System.out.println("\\n--- Comparing with other approaches ---");
        
        // Verify with brute force
        boolean bruteForce1 = solution.hasSumBruteForce(nums1, k1);
        System.out.println("Test 1 Brute Force: " + bruteForce1);
        
        // Verify with two pointers
        boolean twoPointers1 = solution.hasSumTwoPointers(nums1, k1);
        System.out.println("Test 1 Two Pointers: " + twoPointers1);
        
        // Performance note
        System.out.println("\\n--- Performance Note ---");
        System.out.println("✓ One Pass Solution: O(n) time, O(n) space");
        System.out.println("✓ Brute Force: O(n²) time, O(1) space");
        System.out.println("✓ Two Pointers: O(n log n) time, O(1) space");
    }
}
