#!/bin/bash

# Daily Coding Problems - Solution Runner
# Usage: ./run_solution.sh <difficulty> <problem_name>
# Example: ./run_solution.sh easy TwoSum

if [ $# -ne 2 ]; then
    echo "Usage: $0 <difficulty> <problem_name>"
    echo "Example: $0 easy TwoSum"
    echo "Difficulties: easy, medium, hard"
    exit 1
fi

DIFFICULTY=$1
PROBLEM_NAME=$2

# Check if difficulty is valid
if [[ ! "$DIFFICULTY" =~ ^(easy|medium|hard)$ ]]; then
    echo "Error: Difficulty must be 'easy', 'medium', or 'hard'"
    exit 1
fi

# Compile and run the Java solution
echo "Compiling and running $PROBLEM_NAME from $DIFFICULTY difficulty..."
javac -cp src/main/java src/main/java/com/dailyproblems/$DIFFICULTY/$PROBLEM_NAME.java

if [ $? -eq 0 ]; then
    echo "Compilation successful. Running solution..."
    java -cp src/main/java com.dailyproblems.$DIFFICULTY.$PROBLEM_NAME
else
    echo "Compilation failed!"
    exit 1
fi
