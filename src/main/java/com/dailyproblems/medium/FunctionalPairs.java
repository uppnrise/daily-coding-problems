/**
 * Problem: Functional Pairs Implementation - Jane Street Interview Question
 * Source: dailycodingproblem.com
 * Date: July 22, 2025
 * Difficulty: Medium
 * 
 * Problem Statement:
 * cons(a, b) constructs a pair, and car(pair) and cdr(pair) returns the first and 
 * last element of that pair. For example, car(cons(3, 4)) returns 3, and cdr(cons(3, 4)) returns 4.
 * 
 * Given this implementation of cons:
 * 
 * def cons(a, b):
 *     def pair(f):
 *         return f(a, b)
 *     return pair
 * 
 * Implement car and cdr.
 * 
 * Example:
 * Input: cons(3, 4)
 * Output: car(cons(3, 4)) = 3, cdr(cons(3, 4)) = 4
 * 
 * Input: cons("hello", "world")
 * Output: car(cons("hello", "world")) = "hello", cdr(cons("hello", "world")) = "world"
 * 
 * Approach:
 * This problem demonstrates functional programming concepts using higher-order functions
 * and closures. The cons function returns a function that captures the values a and b
 * in its closure. The car and cdr functions need to pass appropriate selector functions
 * to extract the first and second elements respectively.
 * 
 * Key Insights:
 * 1. cons returns a function that takes another function as parameter
 * 2. car needs to pass a function that selects the first element
 * 3. cdr needs to pass a function that selects the second element
 * 4. This pattern is common in functional programming (Church encoding)
 * 
 * Time Complexity: O(1) - all operations are constant time
 * Space Complexity: O(1) - only storing function references and primitive values
 * 
 * Edge Cases Considered:
 * - Different data types (integers, strings, objects)
 * - Null values
 * - Nested pairs (pairs within pairs)
 * - Empty strings or zero values
 */

package com.dailyproblems.medium;

import java.util.function.Function;
import java.util.function.BiFunction;

public class FunctionalPairs {
    
    /**
     * Functional interface representing a pair constructor
     * This mimics the behavior of the Python cons function
     */
    @FunctionalInterface
    public interface Pair<T> {
        T apply(BiFunction<T, T, T> selector);
    }
    
    /**
     * Constructs a pair using functional programming approach
     * This is the Java equivalent of the given Python cons function
     * 
     * @param a - first element of the pair
     * @param b - second element of the pair
     * @return Pair<T> - a function that takes a selector and returns the selected element
     * 
     * Algorithm:
     * 1. Return a lambda function that captures a and b in its closure
     * 2. The returned function takes a BiFunction selector
     * 3. The selector function is applied to both a and b
     * 4. This allows car and cdr to extract elements by passing appropriate selectors
     */
    public static <T> Pair<T> cons(T a, T b) {
        return selector -> selector.apply(a, b);
    }
    
    /**
     * Extracts the first element of a pair (car operation)
     * 
     * @param pair - the pair created by cons
     * @return T - the first element of the pair
     * 
     * Algorithm:
     * 1. Pass a selector function to the pair that returns the first argument
     * 2. The selector (a, b) -> a extracts the first element
     * 3. The pair function applies this selector to its captured values
     */
    public static <T> T car(Pair<T> pair) {
        return pair.apply((a, b) -> a);
    }
    
    /**
     * Extracts the second element of a pair (cdr operation)
     * 
     * @param pair - the pair created by cons
     * @return T - the second element of the pair
     * 
     * Algorithm:
     * 1. Pass a selector function to the pair that returns the second argument
     * 2. The selector (a, b) -> b extracts the second element
     * 3. The pair function applies this selector to its captured values
     */
    public static <T> T cdr(Pair<T> pair) {
        return pair.apply((a, b) -> b);
    }
    
    /**
     * Alternative implementation using raw Function types
     * This shows the direct translation from Python without custom interfaces
     */
    public static class RawImplementation {
        
        /**
         * Raw cons implementation using Function<BiFunction, Object>
         */
        public static Function<BiFunction<Object, Object, Object>, Object> cons(Object a, Object b) {
            return selector -> selector.apply(a, b);
        }
        
        /**
         * Raw car implementation
         */
        public static Object car(Function<BiFunction<Object, Object, Object>, Object> pair) {
            return pair.apply((a, b) -> a);
        }
        
        /**
         * Raw cdr implementation
         */
        public static Object cdr(Function<BiFunction<Object, Object, Object>, Object> pair) {
            return pair.apply((a, b) -> b);
        }
    }
    
    /**
     * Python-style implementation for exact problem recreation
     * This mimics the original Python code as closely as possible in Java
     */
    public static class PythonStyle {
        
        @FunctionalInterface
        public interface PythonPair {
            Object call(BiFunction<Object, Object, Object> f);
        }
        
        /**
         * Python-style cons implementation
         */
        public static PythonPair cons(Object a, Object b) {
            return f -> f.apply(a, b);
        }
        
        /**
         * Python-style car implementation
         */
        public static Object car(PythonPair pair) {
            return pair.call((a, b) -> a);
        }
        
        /**
         * Python-style cdr implementation
         */
        public static Object cdr(PythonPair pair) {
            return pair.call((a, b) -> b);
        }
    }
    
    /**
     * Test cases and example usage
     */
    public static void main(String[] args) {
        System.out.println("=== Functional Pairs Implementation ===\n");
        
        // Test case 1: Basic integer pair (from problem statement)
        System.out.println("Test 1: Basic integer pair");
        Pair<Integer> intPair = cons(3, 4);
        int first = car(intPair);
        int second = cdr(intPair);
        System.out.println("cons(3, 4):");
        System.out.println("car(pair) = " + first); // Expected: 3
        System.out.println("cdr(pair) = " + second); // Expected: 4
        System.out.println("Assertion: car(cons(3, 4)) == 3: " + (first == 3));
        System.out.println("Assertion: cdr(cons(3, 4)) == 4: " + (second == 4));
        System.out.println();
        
        // Test case 2: String pair
        System.out.println("Test 2: String pair");
        Pair<String> stringPair = cons("hello", "world");
        String str1 = car(stringPair);
        String str2 = cdr(stringPair);
        System.out.println("cons(\"hello\", \"world\"):");
        System.out.println("car(pair) = \"" + str1 + "\""); // Expected: "hello"
        System.out.println("cdr(pair) = \"" + str2 + "\""); // Expected: "world"
        System.out.println();
        
        // Test case 3: Double pair
        System.out.println("Test 3: Double pair");
        Pair<Double> doublePair = cons(3.14, 2.71);
        double pi = car(doublePair);
        double e = cdr(doublePair);
        System.out.println("cons(3.14, 2.71):");
        System.out.println("car(pair) = " + pi); // Expected: 3.14
        System.out.println("cdr(pair) = " + e); // Expected: 2.71
        System.out.println();
        
        // Test case 4: Null values
        System.out.println("Test 4: Null values");
        Pair<String> nullPair = cons("value", null);
        String value = car(nullPair);
        String nullValue = cdr(nullPair);
        System.out.println("cons(\"value\", null):");
        System.out.println("car(pair) = \"" + value + "\"");
        System.out.println("cdr(pair) = " + nullValue);
        System.out.println();
        
        // Test case 5: Python-style implementation
        System.out.println("Test 5: Python-style implementation");
        PythonStyle.PythonPair pythonPair = PythonStyle.cons("python", "java");
        Object pythonFirst = PythonStyle.car(pythonPair);
        Object pythonSecond = PythonStyle.cdr(pythonPair);
        System.out.println("PythonStyle.cons(\"python\", \"java\"):");
        System.out.println("PythonStyle.car(pair) = \"" + pythonFirst + "\"");
        System.out.println("PythonStyle.cdr(pair) = \"" + pythonSecond + "\"");
        System.out.println();
        
        // Test case 6: Raw implementation
        System.out.println("Test 6: Raw implementation");
        var rawPair = RawImplementation.cons("raw", "implementation");
        Object rawFirst = RawImplementation.car(rawPair);
        Object rawSecond = RawImplementation.cdr(rawPair);
        System.out.println("RawImplementation.cons(\"raw\", \"implementation\"):");
        System.out.println("RawImplementation.car(pair) = \"" + rawFirst + "\"");
        System.out.println("RawImplementation.cdr(pair) = \"" + rawSecond + "\"");
        System.out.println();
        
        // Test case 7: Boolean pair
        System.out.println("Test 7: Boolean pair");
        Pair<Boolean> boolPair = cons(true, false);
        boolean bool1 = car(boolPair);
        boolean bool2 = cdr(boolPair);
        System.out.println("cons(true, false):");
        System.out.println("car(pair) = " + bool1); // Expected: true
        System.out.println("cdr(pair) = " + bool2); // Expected: false
        System.out.println();
        
        // Test case 8: Character pair
        System.out.println("Test 8: Character pair");
        Pair<Character> charPair = cons('A', 'Z');
        char char1 = car(charPair);
        char char2 = cdr(charPair);
        System.out.println("cons('A', 'Z'):");
        System.out.println("car(pair) = '" + char1 + "'"); // Expected: 'A'
        System.out.println("cdr(pair) = '" + char2 + "'"); // Expected: 'Z'
        System.out.println();
        
        // Test case 9: Performance test
        System.out.println("Test 9: Performance test");
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Pair<Integer> perfPair = cons(i, i + 1);
            int perfFirst = car(perfPair);
            int perfSecond = cdr(perfPair);
            // Verify correctness
            if (perfFirst != i || perfSecond != i + 1) {
                throw new AssertionError("Performance test failed at iteration " + i);
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Created and accessed 100,000 pairs in " + 
                          (endTime - startTime) / 1_000_000 + " ms");
        System.out.println();
        
        // Explanation of the functional programming concept
        System.out.println("=== Functional Programming Explanation ===");
        System.out.println("This problem demonstrates several key concepts:");
        System.out.println("1. Higher-order functions: cons returns a function");
        System.out.println("2. Closures: the returned function captures variables a and b");
        System.out.println("3. Function composition: car and cdr compose with cons");
        System.out.println("4. Church encoding: representing data using only functions");
        System.out.println("5. Lambda calculus: pure functional computation");
        System.out.println();
        
        System.out.println("=== How it works ===");
        System.out.println("cons(a, b) → returns function f(selector) → selector(a, b)");
        System.out.println("car(pair) → calls pair with selector (x, y) → x");
        System.out.println("cdr(pair) → calls pair with selector (x, y) → y");
        System.out.println();
        
        System.out.println("=== Step-by-step execution ===");
        System.out.println("1. cons(3, 4) creates: pair = (selector) → selector(3, 4)");
        System.out.println("2. car(pair) calls: pair((a, b) → a)");
        System.out.println("3. This becomes: ((a, b) → a)(3, 4) = 3");
        System.out.println("4. cdr(pair) calls: pair((a, b) → b)");
        System.out.println("5. This becomes: ((a, b) → b)(3, 4) = 4");
        System.out.println();
        
        System.out.println("=== Why this is elegant ===");
        System.out.println("• No explicit data structures needed");
        System.out.println("• Pure functional approach");
        System.out.println("• Demonstrates power of closures");
        System.out.println("• Foundation for more complex data structures");
        System.out.println("• Used in functional languages like Scheme/LISP");
        System.out.println("• Shows that functions can represent data");
        System.out.println();
        
        System.out.println("=== Jane Street Connection ===");
        System.out.println("• Jane Street uses functional programming (OCaml)");
        System.out.println("• Tests understanding of higher-order functions");
        System.out.println("• Evaluates ability to think abstractly");
        System.out.println("• Shows knowledge of fundamental CS concepts");
        System.out.println("• Demonstrates comfort with lambda calculus");
    }
}
