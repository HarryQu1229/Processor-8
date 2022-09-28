# Meeting Minutes 22/09

1. Started Trello board and initialize several tasks, classified tasks into milestone 1 and milestone 2
2. Went over slide together and discussed several implementation plans
   1. How to generate topological sequence
      1. Option I: Use "level" to represent the ancestor and descendant relationship
      2. Option II: Store ancestor and descendant relationship in each node as an array
      3. Problem with option II: it is possible that a task of lower level is scheduled after a higher level → level option may not work
   2. How should nodes and edges be stored in the program
      1. `Node` class
      2.  Will not create `Edge` class. Instead, store the weight of each edge in the `Node` class
      3. Found `graph stream api` and `graph viz` which can handle reading the dot file for us, hence there will be no need to create our own data structure to store the file.
      4. Tried `graph stream api` but did not successfully read in from a dot file
   3. Discussed terminologies and their definitions, such as, critical path and bottom level.
   4. Discussed how to generate solution/state tree, but did not come up with a valid solution