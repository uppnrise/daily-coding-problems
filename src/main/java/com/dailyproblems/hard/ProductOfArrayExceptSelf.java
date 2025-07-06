/**
 * Problem: Product of Array Except Self - Uber Interview Question
 * Source: dailycodingproblem.com
 * Date: July 6, 2025
 * Difficulty: Hard
 * 
 * Problem Statement:
 * Given an array of integers, return a new array such that each element at index i 
 * of the new array is the product of all the numbers in the original array except 
 * the one at i.
 * 
 * Example:
 * Input: [1, 2, 3, 4, 5]
 * Output: [120, 60, 40, 30, 24]
 * 
 * Input: [3, 2, 1]
 * Output: [2, 3, 6]
 * 
 * Constraints:
 * - The length of the input array is at least 2
 * - You must solve it without using division
 * - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer
 * 
 * Approach:
 * Since we can't use division, we need to find the product of all elements except 
 * the current one without dividing by the current element. We can do this using 
 * two passes:
 * 
 * 1. Left Pass: For each element, calculate the product of all elements to its left
 * 2. Right Pass: For each element, calculate the product of all elements to its right
 * 3. The result at index i = left_product[i] * right_product[i]
 * 
 * We can optimize space by using the result array to store left products first,
 * then multiply by right products in a second pass.
 * 
 * Time Complexity: O(n) - two passes through the array
 * Space Complexity: O(1) - excluding the output array (which is required)
 * 
 * Edge Cases Considered:
 * - Array with zeros (affects product calculation)
 * - Array with negative numbers
 * - Minimum length array (2 elements)
 * - Array with all ones
 */

package com.dailyproblems.hard;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {
    
    /**
     * Calculates product of array except self without division (Space Optimized)
     * @param nums - input array of integers
     * @return int[] - array where each element is product of all others except itself
     * 
     * Time Complexity: O(n) - two passes through the array
     * Space Complexity: O(1) - constant extra space (excluding the output array)
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // First pass: calculate left products
        // result[i] = product of all elements to the left of i
        result[0] = 1; // No elements to the left of index 0
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        // Second pass: multiply by right products
        // Use a variable to track the running product from right
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct *= nums[i];
        }
        
        return result;
    }
    
    /**
     * Alternative approach using separate left and right arrays (for clarity)
     * @param nums - input array of integers
     * @return int[] - array where each element is product of all others except itself
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n) - for left and right arrays
     */
    public int[] productExceptSelfVerbose(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];
        
        // Calculate left products
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        
        // Calculate right products
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        
        // Combine left and right products
        for (int i = 0; i < n; i++) {
            result[i] = left[i] * right[i];
        }
        
        return result;
    }
    
    /**
     * Approach using division (for comparison - violates follow-up constraint)
     * This approach handles zeros specially
     * @param nums - input array of integers
     * @return int[] - array where each element is product of all others except itself
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1) - excluding output array
     */
    public int[] productExceptSelfWithDivision(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Count zeros and calculate total product of non-zero elements
        int zeroCount = 0;
        long totalProduct = 1;
        
        for (int num : nums) {
            if (num == 0) {
                zeroCount++;
            } else {
                totalProduct *= num;
            }
        }
        
        // Handle different cases based on zero count
        for (int i = 0; i < n; i++) {
            if (zeroCount > 1) {
                // More than one zero means all products are zero
                result[i] = 0;
            } else if (zeroCount == 1) {
                // Exactly one zero
                if (nums[i] == 0) {
                    result[i] = (int) totalProduct;
                } else {
                    result[i] = 0;
                }
            } else {
                // No zeros, safe to divide
                result[i] = (int) (totalProduct / nums[i]);
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to print array for testing
     */
    private void printArray(String label, int[] arr) {
        System.out.println(label + ": " + Arrays.toString(arr));
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();
        
        // Test case 1: Given example
        int[] nums1 = {1, 2, 3, 4, 5};
        int[] result1 = solution.productExceptSelf(nums1);
        solution.printArray("Test 1 Input", nums1);
        solution.printArray("Test 1 Output", result1);
        // Expected: [120, 60, 40, 30, 24]
        System.out.println();
        
        // Test case 2: Second given example
        int[] nums2 = {3, 2, 1};
        int[] result2 = solution.productExceptSelf(nums2);
        solution.printArray("Test 2 Input", nums2);
        solution.printArray("Test 2 Output", result2);
        // Expected: [2, 3, 6]
        System.out.println();
        
        // Test case 3: Array with zero
        int[] nums3 = {1, 0, 3, 4};
        int[] result3 = solution.productExceptSelf(nums3);
        solution.printArray("Test 3 Input", nums3);
        solution.printArray("Test 3 Output", result3);
        // Expected: [0, 12, 0, 0]
        System.out.println();
        
        // Test case 4: Array with multiple zeros
        int[] nums4 = {0, 0, 2, 3};
        int[] result4 = solution.productExceptSelf(nums4);
        solution.printArray("Test 4 Input", nums4);
        solution.printArray("Test 4 Output", result4);
        // Expected: [0, 0, 0, 0]
        System.out.println();
        
        // Test case 5: Array with negative numbers
        int[] nums5 = {-1, 2, -3, 4};
        int[] result5 = solution.productExceptSelf(nums5);
        solution.printArray("Test 5 Input", nums5);
        solution.printArray("Test 5 Output", result5);
        // Expected: [-24, 12, 8, -6]
        System.out.println();
        
        // Test case 6: Minimum length array
        int[] nums6 = {2, 3};
        int[] result6 = solution.productExceptSelf(nums6);
        solution.printArray("Test 6 Input", nums6);
        solution.printArray("Test 6 Output", result6);
        // Expected: [3, 2]
        System.out.println();
        
        System.out.println("--- Comparing with verbose approach ---");
        int[] verboseResult1 = solution.productExceptSelfVerbose(nums1);
        solution.printArray("Verbose Test 1", verboseResult1);
        
        System.out.println("\\n--- Comparing with division approach (violates constraint) ---");
        int[] divisionResult1 = solution.productExceptSelfWithDivision(nums1);
        solution.printArray("Division Test 1", divisionResult1);
        
        System.out.println("\\n--- Performance Analysis ---");
        System.out.println("✓ Optimized Solution: O(n) time, O(1) extra space");
        System.out.println("✓ Verbose Solution: O(n) time, O(n) extra space");
        System.out.println("✓ Division Solution: O(n) time, O(1) extra space (but violates constraint)");
        System.out.println("\\n--- Why This is Hard ---");
        System.out.println("• Cannot use division (main constraint)");
        System.out.println("• Must handle zeros correctly");
        System.out.println("• Space optimization requires clever use of output array");
        System.out.println("• Need to think about left and right products conceptually");
    }
}
