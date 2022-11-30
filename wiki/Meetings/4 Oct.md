# Meeting Minutes 04/10

1. Planning for the next phase: task to do before milestone 2
   1. Use multiple core to calculate the scheduling
   2. Visualization
   3. Team decided to do these part together
2. Discussion on how to use multiple core
   1. Pyjama: recommended by lecturers but little documentation
   2. Parallel Task: good for multi-thread GUI application but also little documentation
   3. May also use java built-in java library `java.util.Concurrent`
3. Discussed what to display on the GUI
   1. Gantt chart
   2. RAM usage
   3. Current best time
   4. Percentage completed
   5. Elapsed Time
   6. Running status
   7. Basic information:
      1. Num of processor
      2. Num of core
      3. Num of tasks
      4. Input/Output file name
4. Tool to read command line argument: `Apache Commons CLI`
5. Whether to keep both A* and DFS or one of them: A* for now, do DFS if have time
6. Task before next meeting: read and investigate concurrency and java FX