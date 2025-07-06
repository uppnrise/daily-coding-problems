/**
 * Problem: Regular Expression Matching - Problem #10
 * Source: dailycodingproblem.com (Example)
 * Date: June 30, 2025
 * Difficulty: Hard
 * 
 * Problem Statement:
 * Given an input string s and a pattern p, implement regular expression matching
 * with support for '.' and '*' where:
 * - '.' Matches any single character
 * - '*' Matches zero or more of the preceding element
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * Example:
 * Input: s = "aa", p = "a*"
 * Output: true (a* means zero or more 'a's)
 * 
 * Input: s = "mississippi", p = "mis*is*p*."
 * Output: false
 * 
 * Constraints:
 * - 1 <= s.length <= 20
 * - 1 <= p.length <= 30
 * - s contains only lowercase English letters
 * - p contains only lowercase English letters, '.', and '*'
 * - It is guaranteed that for each '*' there is a previous valid character to match
 * 
 * Approach:
 * Use dynamic programming with memoization. Create a 2D DP table where dp[i][j] 
 * represents whether the first i characters of string s match the first j 
 * characters of pattern p.
 * 
 * 1. Handle base cases: empty string and pattern
 * 2. For each character in string and pattern:
 *    - If pattern[j] is a regular character or '.', check direct match
 *    - If pattern[j+1] is '*', handle two cases:
 *      a) Use '*' zero times (skip current char and '*')
 *      b) Use '*' one or more times (if current chars match)
 * 3. Use memoization to avoid redundant calculations
 * 
 * Time Complexity: O(m * n) - where m and n are lengths of string and pattern
 * Space Complexity: O(m * n) - for the memoization table
 * 
 * Edge Cases Considered:
 * - Empty string with empty pattern (should return true)
 * - Empty string with pattern containing '*' (may still match)
 * - Pattern with consecutive '*' characters
 * - Pattern ending with '*'
 */

package com.dailyproblems.hard;

import java.util.HashMap;
import java.util.Map;

public class RegularExpressionMatching {
    
    // Memoization cache to store computed results
    private Map<String, Boolean> memo;
    
    /**
     * Main method to check if string matches pattern
     * @param s - input string to match
     * @param p - pattern with '.' and '*' support
     * @return boolean - true if string matches pattern
     */
    public boolean isMatch(String s, String p) {
        memo = new HashMap<>();
        return isMatchHelper(s, 0, p, 0);
    }
    
    /**
     * Recursive helper method with memoization
     * @param s - input string
     * @param i - current index in string
     * @param p - pattern
     * @param j - current index in pattern
     * @return boolean - true if substring matches subpattern
     */
    private boolean isMatchHelper(String s, int i, String p, int j) {
        // Create key for memoization
        String key = i + "," + j;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        boolean result;
        
        // Base case: reached end of pattern
        if (j == p.length()) {
            result = (i == s.length());
        } else {
            // Check if current characters match
            boolean firstMatch = (i < s.length() && 
                                (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));
            
            // Check if next character is '*'
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                // Two options with '*':
                // 1. Use '*' zero times - skip current char and '*'
                // 2. Use '*' one or more times - if first chars match, advance in string
                result = isMatchHelper(s, i, p, j + 2) || 
                        (firstMatch && isMatchHelper(s, i + 1, p, j));
            } else {
                // No '*', must have exact match and continue
                result = firstMatch && isMatchHelper(s, i + 1, p, j + 1);
            }
        }
        
        // Store result in memoization cache
        memo.put(key, result);
        return result;
    }
    
    /**
     * Dynamic Programming approach (bottom-up)
     * @param s - input string to match
     * @param p - pattern with '.' and '*' support
     * @return boolean - true if string matches pattern
     */
    public boolean isMatchDP(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] represents if first i chars of s match first j chars of p
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base case: empty string matches empty pattern
        dp[0][0] = true;
        
        // Handle patterns that can match empty string (like "a*b*c*")
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);
                
                if (pChar == '*') {
                    // '*' matches zero or more of the preceding character
                    char prevChar = p.charAt(j - 2);
                    
                    // Zero occurrences: dp[i][j-2]
                    dp[i][j] = dp[i][j - 2];
                    
                    // One or more occurrences: check if chars match
                    if (prevChar == sChar || prevChar == '.') {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pChar == '.' || pChar == sChar) {
                    // Direct character match or wildcard
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // If no match and not '*', dp[i][j] remains false
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        RegularExpressionMatching solution = new RegularExpressionMatching();
        
        // Test case 1: Basic '*' usage
        String s1 = "aa", p1 = "a*";
        boolean result1 = solution.isMatch(s1, p1);
        boolean result1DP = solution.isMatchDP(s1, p1);
        System.out.println("Test 1: " + result1 + " (DP: " + result1DP + ")"); // Expected: true
        
        // Test case 2: Complex pattern
        String s2 = "mississippi", p2 = "mis*is*p*.";
        boolean result2 = solution.isMatch(s2, p2);
        boolean result2DP = solution.isMatchDP(s2, p2);
        System.out.println("Test 2: " + result2 + " (DP: " + result2DP + ")"); // Expected: false
        
        // Test case 3: Wildcard matching
        String s3 = "ab", p3 = ".*";
        boolean result3 = solution.isMatch(s3, p3);
        boolean result3DP = solution.isMatchDP(s3, p3);
        System.out.println("Test 3: " + result3 + " (DP: " + result3DP + ")"); // Expected: true
        
        // Test case 4: Empty string
        String s4 = "", p4 = "a*";
        boolean result4 = solution.isMatch(s4, p4);
        boolean result4DP = solution.isMatchDP(s4, p4);
        System.out.println("Test 4: " + result4 + " (DP: " + result4DP + ")"); // Expected: true
        
        // Test case 5: No match
        String s5 = "ab", p5 = ".*c";
        boolean result5 = solution.isMatch(s5, p5);
        boolean result5DP = solution.isMatchDP(s5, p5);
        System.out.println("Test 5: " + result5 + " (DP: " + result5DP + ")"); // Expected: false
    }
}
