/**
 * Problem: Single Number II - Google Interview Question
 * Source: dailycodingproblem.com
 * Date: July 7, 2025
 * Difficulty: Hard
 * 
 * Problem Statement:
 * Given an array of integers where every integer occurs three times except for 
 * one integer, which only occurs once, find and return the non-duplicated integer.
 * 
 * Example:
 * Input: [6, 1, 3, 3, 3, 6, 6]
 * Output: 1
 * 
 * Input: [13, 19, 13, 13]
 * Output: 19
 * 
 * Constraints:
 * - Do this in O(N) time and O(1) space
 * - Every number appears exactly 3 times except one number which appears once
 * - The array contains at least 1 element
 * 
 * Approach:
 * This problem requires bit manipulation. Since we can't use extra space for counting,
 * we need to use the properties of bits. The key insight is that for each bit position,
 * if we count how many numbers have that bit set, the count will be either:
 * - 3k (if the unique number doesn't have this bit set)
 * - 3k + 1 (if the unique number has this bit set)
 * 
 * We can use two variables (ones, twos) to track bit counts:
 * - ones: tracks bits that appeared 1 time
 * - twos: tracks bits that appeared 2 times
 * - When a bit appears 3 times, we reset both ones and twos for that bit
 * 
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only using two integer variables
 * 
 * Edge Cases Considered:
 * - Array with minimum size (4 elements: 3 same + 1 unique)
 * - Negative numbers
 * - Zero as the unique number
 * - Large numbers within integer range
 */

package com.dailyproblems.hard;

import java.util.HashMap;
import java.util.Map;

public class SingleNumberII {
    
    /**
     * Finds the single number using bit manipulation (Optimal Solution)
     * @param nums - array where every number appears 3 times except one
     * @return int - the number that appears only once
     * 
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(1) - constant space
     */
    public int singleNumber(int[] nums) {
        int ones = 0;  // Tracks bits that appeared 1 time
        int twos = 0;  // Tracks bits that appeared 2 times
        
        for (int num : nums) {
            // Update ones: add current number, but remove bits already in twos
            ones = (ones ^ num) & ~twos;
            
            // Update twos: add bits that were in ones before this iteration
            twos = (twos ^ num) & ~ones;
        }
        
        // After processing all numbers, ones contains the unique number
        return ones;
    }
    
    /**
     * Alternative bit manipulation approach using bit counting
     * @param nums - array where every number appears 3 times except one
     * @return int - the number that appears only once
     * 
     * Time Complexity: O(n) - iterate through array for each of 32 bits
     * Space Complexity: O(1) - constant space
     */
    public int singleNumberBitCounting(int[] nums) {
        int result = 0;
        
        // Check each bit position (32 bits for integer)
        for (int i = 0; i < 32; i++) {
            int bitCount = 0;
            
            // Count how many numbers have bit i set
            for (int num : nums) {
                if ((num >> i & 1) == 1) {
                    bitCount++;
                }
            }
            
            // If count is not divisible by 3, the unique number has this bit set
            if (bitCount % 3 != 0) {
                result |= (1 << i);
            }
        }
        
        return result;
    }
    
    /**
     * Brute force approach using HashMap (violates space constraint)
     * @param nums - array where every number appears 3 times except one
     * @return int - the number that appears only once
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) - HashMap storage
     */
    public int singleNumberHashMap(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        
        // Count occurrences of each number
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        
        // Find the number that appears only once
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        
        // Should never reach here given problem constraints
        throw new IllegalArgumentException("No single number found");
    }
    
    /**
     * Mathematical approach using sum formula (violates space constraint)
     * @param nums - array where every number appears 3 times except one
     * @return int - the number that appears only once
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) - HashSet for unique numbers
     */
    public int singleNumberMath(int[] nums) {
        java.util.Set<Integer> uniqueNums = new java.util.HashSet<>();
        long sumTotal = 0;
        long sumUnique = 0;
        
        for (int num : nums) {
            sumTotal += num;
            if (uniqueNums.add(num)) {
                sumUnique += num;
            }
        }
        
        // Formula: 3 * (sum of unique numbers) - (total sum) = 2 * (single number)
        return (int) ((3 * sumUnique - sumTotal) / 2);
    }
    
    /**
     * Sorting-based approach (violates time constraint but easier to understand)
     * @param arr - array where every number appears 3 times except one
     * @return Integer - the number that appears only once, or null if not found
     * 
     * Algorithm:
     * 1. Sort the array so identical numbers are grouped together
     * 2. Iterate through the array, checking if current element has duplicates
     * 3. If current element equals next element, skip 3 positions (assuming triplet)
     * 4. If no duplicate found, current element is the unique one
     * 
     * Time Complexity: O(n log n) - due to sorting
     * Space Complexity: O(1) - sorting in place, constant extra variables
     * 
     * Note: This approach violates the O(n) time constraint but is much easier
     * to understand compared to bit manipulation techniques.
     * 
     * DETAILED EXAMPLE WALKTHROUGH:
     * 
     * Example 1: [2, 2, 3, 2]
     * Step 1: Sort → [2, 2, 2, 3]
     * Step 2: Walk through:
     *   index=0: arr[0]=2, check arr[0]==arr[1]? → 2==2? YES
     *            Skip triplet: index += 3 → index=3
     *   index=3: arr[3]=3, check if index < length-1? → 3 < 3? NO
     *            No next element, so return arr[3] = 3 ✓
     * 
     * Example 2: [0, 1, 0, 1, 0, 1, 99]
     * Step 1: Sort → [0, 0, 0, 1, 1, 1, 99]
     * Step 2: Walk through:
     *   index=0: arr[0]=0, check arr[0]==arr[1]? → 0==0? YES
     *            Skip triplet: index += 3 → index=3
     *   index=3: arr[3]=1, check arr[3]==arr[4]? → 1==1? YES
     *            Skip triplet: index += 3 → index=6
     *   index=6: arr[6]=99, check if index < length-1? → 6 < 6? NO
     *            No next element, so return arr[6] = 99 ✓
     * 
     * Example 3: [43, 16, 45, 89, 45, 16, 43, 45, 16, 43]
     * Step 1: Sort → [16, 16, 16, 43, 43, 43, 45, 45, 45, 89]
     * Step 2: Walk through:
     *   index=0: arr[0]=16, check arr[0]==arr[1]? → 16==16? YES
     *            Skip triplet: index += 3 → index=3
     *   index=3: arr[3]=43, check arr[3]==arr[4]? → 43==43? YES
     *            Skip triplet: index += 3 → index=6
     *   index=6: arr[6]=45, check arr[6]==arr[7]? → 45==45? YES
     *            Skip triplet: index += 3 → index=9
     *   index=9: arr[9]=89, check if index < length-1? → 9 < 9? NO
     *            No next element, so return arr[9] = 89 ✓
     * 
     * Why this works:
     * - After sorting, identical numbers are grouped together
     * - If a number appears 3 times, all 3 instances are consecutive
     * - We can skip all 3 at once when we detect the first duplicate
     * - The unique number will be alone (no duplicate next to it)
     * - When we encounter a number with no duplicate next to it, that's our answer
     * 
     * VISUAL REPRESENTATION:
     * Original: [2, 2, 3, 2]
     * Sorted:   [2, 2, 2, 3]
     *            ^     ^  ^
     *            |     |  |
     *            |     |  └─ Unique! No duplicate next to it
     *            |     └─ End of triplet
     *            └─ Start of triplet (detected by arr[0]==arr[1])
     * 
     *           index=0  →  Skip 3  →  index=3  →  Return 3
     *              ↓           ↓          ↓
     *           [2, 2, 2,     3]
     *            -------      ^
     *             triplet   unique
     */
    public Integer singleNumberSorting(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        java.util.Arrays.sort(arr);

        int index = 0;
        while (index < arr.length) {
            // Check if current element has a duplicate next to it
            if (index < arr.length - 1 && arr[index] == arr[index + 1]) {
                index += 3; // Skip the triplet (assuming all 3 are consecutive)
            } else {
                return arr[index]; // Found the unique element
            }
        }

        return null; // If no unique element found, return null
    }

    /**
     * Helper method to explain the bit manipulation logic
     */
    private void explainBitLogic(int[] nums) {
        System.out.println("Bit Manipulation Walkthrough:");
        System.out.println("ones tracks bits appearing 1 time, twos tracks bits appearing 2 times");
        System.out.println("When a bit appears 3 times, both ones and twos are reset for that bit\n");
        
        int ones = 0, twos = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
            
            System.out.printf("Step %d: num=%d, ones=%d, twos=%d\n", i + 1, num, ones, twos);
        }
        System.out.println("Final result (ones): " + ones + "\n");
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        SingleNumberII solution = new SingleNumberII();
        
        // Test case 1: Given example
        int[] nums1 = {6, 1, 3, 3, 3, 6, 6};
        int result1 = solution.singleNumber(nums1);
        System.out.println("Test 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + result1); // Expected: 1
        System.out.println();
        
        // Test case 2: Second given example
        int[] nums2 = {13, 19, 13, 13};
        int result2 = solution.singleNumber(nums2);
        System.out.println("Test 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + result2); // Expected: 19
        System.out.println();
        
        // Test case 3: Single element (minimum case)
        int[] nums3 = {5, 5, 5, 7};
        int result3 = solution.singleNumber(nums3);
        System.out.println("Test 3: " + java.util.Arrays.toString(nums3));
        System.out.println("Result: " + result3); // Expected: 7
        System.out.println();
        
        // Test case 4: Negative numbers
        int[] nums4 = {-1, -1, -1, -2, -2, -2, -3};
        int result4 = solution.singleNumber(nums4);
        System.out.println("Test 4: " + java.util.Arrays.toString(nums4));
        System.out.println("Result: " + result4); // Expected: -3
        System.out.println();
        
        // Test case 5: Zero as unique number
        int[] nums5 = {1, 1, 1, 2, 2, 2, 0};
        int result5 = solution.singleNumber(nums5);
        System.out.println("Test 5: " + java.util.Arrays.toString(nums5));
        System.out.println("Result: " + result5); // Expected: 0
        System.out.println();
        
        // Test case 6: Larger numbers
        int[] nums6 = {100, 200, 100, 100, 200, 200, 300};
        int result6 = solution.singleNumber(nums6);
        System.out.println("Test 6: " + java.util.Arrays.toString(nums6));
        System.out.println("Result: " + result6); // Expected: 300
        System.out.println();
        
        System.out.println("--- Comparing with alternative approaches ---");
        
        // Compare with bit counting approach
        int bitCountResult = solution.singleNumberBitCounting(nums1);
        System.out.println("Bit Counting Result: " + bitCountResult);
        
        // Compare with HashMap approach (violates space constraint)
        int hashMapResult = solution.singleNumberHashMap(nums1);
        System.out.println("HashMap Result: " + hashMapResult);
        
        // Compare with mathematical approach (violates space constraint)
        int mathResult = solution.singleNumberMath(nums1);
        System.out.println("Mathematical Result: " + mathResult);
        
        // Compare with sorting approach (violates time constraint)
        Integer sortingResult = solution.singleNumberSorting(nums1);
        System.out.println("Sorting Result: " + sortingResult);
        
        System.out.println("\n--- Bit Manipulation Explanation ---");
        solution.explainBitLogic(new int[]{3, 3, 3, 5});
        
        System.out.println("--- Algorithm Comparison ---");
        System.out.println("✓ Bit Manipulation (ones/twos): O(n) time, O(1) space - OPTIMAL");
        System.out.println("✓ Bit Counting: O(32n) time, O(1) space - Good");
        System.out.println("✓ Sorting: O(n log n) time, O(1) space - Violates time constraint");
        System.out.println("✓ HashMap: O(n) time, O(n) space - Violates space constraint");
        System.out.println("✓ Mathematical: O(n) time, O(n) space - Violates space constraint");
        
        System.out.println("\n--- Why This is Hard ---");
        System.out.println("• Requires advanced bit manipulation techniques");
        System.out.println("• Must achieve O(1) space complexity");
        System.out.println("• Non-obvious algorithm (ones/twos state machine)");
        System.out.println("• Handles edge cases like negative numbers and zero");
        System.out.println("• Understanding of XOR properties and bit operations");
    }
}
