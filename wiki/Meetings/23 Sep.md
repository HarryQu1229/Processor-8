# Meeting Minutes 22/09

0. Member has been able to read a dot file using `graph stream api` before meeting 

1. Demo how to use `graph stream api` to the team to make sure everyone is at the same stage. Also investigated other useful methods provided by `graph stream api`. `graph stream api` has all the features of `graph viz` so decided not to use `graph viz`
2. Investigate how to generate solution tree
   1. Generate all the task orders regardless of the graph relationship - memory cost too high
   2. Use a queue to store all nodes whose in-degree is 0, and minus the in-degree of a node by 1 when its neighbour node is added to the solution tree.
   3. Generate all topological sequences and calculate the cost one by one
      1. Cannot generate a solution tree using these topological sequences?