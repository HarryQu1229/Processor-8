# Milestone 2

## Expected outcome

1. Program is able to apply multi-threading to the algorithm
2. Program is able to visualize the computation process and the outcome
3. Program is able to write to a user-specified filename

## Major decisions

###  Multi threading

#### Issue

How do we implement thread pool and enable multi threading 

#### Solution

We first investigated `Pyjama` which was suggested in the project specification. However, we found that this library has very little documentation, which means it will be relatively difficult to learn. Given the limited amount of time that we have before the final submission, we decided to go with `Concurrent`, which is a  Java built-in library.

With the help of `Concurrent` library, we were able to implement lots of tasks efficiently, such as establishing thread pool and getting the result of the first-finished thread.