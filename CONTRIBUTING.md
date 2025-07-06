# How to Add New Problems

This guide explains how to add new coding problems to the repository following the established format and conventions.

## Step 1: Choose the Right Directory

Based on the problem difficulty, place your solution in the appropriate directory:
- `src/main/java/com/dailyproblems/easy/` - For easy problems
- `src/main/java/com/dailyproblems/medium/` - For medium problems  
- `src/main/java/com/dailyproblems/hard/` - For hard problems

## Step 2: File Naming Convention

Use descriptive PascalCase names that clearly indicate what the problem is about:
- `TwoSum.java` - Good ✅
- `BinaryTreeMaxDepth.java` - Good ✅
- `Problem1.java` - Avoid ❌
- `solution.java` - Avoid ❌

## Step 3: Use the Template

Copy the content from `TEMPLATE.java` and replace the placeholders with your specific problem details:

```java
/**
 * Problem: [Clear, descriptive title]
 * Source: dailycodingproblem.com
 * Date: [Current date]
 * Difficulty: [Easy/Medium/Hard]
 * 
 * Problem Statement:
 * [Complete problem description]
 * 
 * Example:
 * Input: [example input]
 * Output: [expected output]
 * 
 * Constraints:
 * [Any constraints from the problem]
 * 
 * Approach:
 * [Detailed explanation of your solution approach]
 * 
 * Time Complexity: O([complexity])
 * Space Complexity: O([complexity])
 * 
 * Edge Cases Considered:
 * [List important edge cases you handled]
 */
```

## Step 4: Package Declaration

Make sure your package declaration matches the directory structure:
- Easy problems: `package com.dailyproblems.easy;`
- Medium problems: `package com.dailyproblems.medium;`
- Hard problems: `package com.dailyproblems.hard;`

## Step 5: Implementation Guidelines

### Method Structure
- Create clear, well-named public methods for your solution
- Include detailed parameter and return value documentation
- Consider providing multiple approaches when applicable

### Main Method
- Always include a `main` method with comprehensive test cases
- Test edge cases and boundary conditions
- Print clear output showing test results
- Compare multiple approaches if you implemented them

### Comments
- Explain complex logic inline
- Describe the algorithm at a high level
- Clarify any non-obvious optimizations

## Step 6: Testing Your Solution

### Using VS Code
1. Open the Java file
2. Click the "Run" button above the main method
3. Or use `Ctrl+F5` (Windows/Linux) or `Cmd+F5` (Mac)

### Using Command Line
```bash
# Compile
javac -cp src/main/java src/main/java/com/dailyproblems/[difficulty]/[ProblemName].java

# Run
java -cp src/main/java com.dailyproblems.[difficulty].[ProblemName]
```

### Using the Helper Script
```bash
./run_solution.sh [difficulty] [ProblemName]
# Example: ./run_solution.sh easy TwoSum
```

## Step 7: Update Progress

After adding a new problem, update the README.md file:
1. Update the count in the "Problems Solved by Difficulty" section
2. Add the problem to the "Recent Problems" section if desired

## Example Checklist

Before committing your solution, ensure:
- [ ] File is in the correct difficulty directory
- [ ] Package declaration matches directory structure
- [ ] Problem statement is complete and clear
- [ ] Solution includes time/space complexity analysis
- [ ] Code is well-commented and readable
- [ ] Main method includes comprehensive test cases
- [ ] Solution compiles and runs without errors
- [ ] Edge cases are handled appropriately
- [ ] README.md is updated with new problem count

## Common Patterns

### For Array Problems
```java
// Handle null/empty arrays
if (arr == null || arr.length == 0) {
    return [appropriate default value];
}
```

### For Tree Problems
```java
// Define TreeNode class if needed
public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
}
```

### For String Problems
```java
// Handle null/empty strings
if (s == null || s.isEmpty()) {
    return [appropriate default value];
}
```

## Tips for Quality Solutions

1. **Clarity over Cleverness**: Write code that's easy to understand
2. **Multiple Approaches**: Show different ways to solve the problem when applicable
3. **Comprehensive Testing**: Include edge cases in your test cases
4. **Performance Analysis**: Always include time/space complexity
5. **Real-world Application**: Mention where this type of problem might be useful

Remember: The goal is to create a learning resource, so prioritize clarity and educational value over just getting the right answer.
