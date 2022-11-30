# Milestone 1

## Expected outcome

1. Program is able to be executed from command line.
2. Program is able to read command line arguments.
3. Program is able to read in the dot file, where the path of the file is provided as one of the command line arguments
4. Program is able to output a valid solution to a dot file
5. **Finish all above before 3rd October**

## Major decisions

### I/O

#### Issue

Given a dot file, how to read it into the program and represent it using appropriate data structure

#### Solution

We considered using Java `Scanner` or `BufferReader` to read from file. However, given the complexity of the input file and the special format (the input file is a dot file rather than text file), we started looking for Java libraries that can help us with reading data. As we used Maven in our project, installing dependencies will not be a difficult task to do. 

We found `graph stream api` and `graph viz` that can read and parse a dot file. By investigating both of these, we found `graph stream api` has more features than `graph viz` and it is also more well-documented. Therefore, we ended up using `graph stream api` to help us with I/O

### Solution Tree Generation

#### Issue

Initially, we wanted to generate the entire solution tree and traverse the tree using DFS and/or A*. However, the solution tree can be huge for some of the graphs (like g2 in our repository) which resulting in `OutOfMemoryError`

#### Solution

Considering the size of solution tree, we decided not to generate the entire solution tree but to calculate schedule as we generate the tree. We used A Star algorithm to probe which node to continue at each position in the solution tree

