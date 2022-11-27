# Processor 8

## How to run - from the command line

Before running the application, please make sure you have [Java 11](https://www.oracle.com/java/technologies/downloads/) installed on your device

There are 2 .jar files, the first one is the default `scheduler.jar` which is designed to be run on Linux based operating systems, there is also another .jar file `scheduler-win.jar` which is designed to be run on Windows based operating systems. However, both jar files can be interoperable if no visualization option is required, i.e. `-v` command line option is not added.

1. Clone or download this repository and cd to the root directory (where "schedule.jar" is in the current directory)
2. Execute `java -jar [jar file based on OS] input.dot P [OPTIONS]`

```
input.dot     A digraph in DOT format, that represents the tasks need to be scheduled
P             The number of processors that these tasks have avaiable

OPTIONS
-p N          Use N cores to execute the software, if not provided, default value of N is 1
-o FILENAME   Output file name is FILENAME, if not provided, default filename is input-output.dot
-v            Visualise the process of computation, if not provided, computation is not visualised
```

## How to run - from IntelliJ

1. Build Project through Maven and download all dependencies through Maven.
2. Open `pom.xml` file and navigate to line `28`.
3. Change the command line arguments `input.dot P [OPTIONS]` to desired arguments. (Refer to Options Table above)
4. Open `Run Anything` window from the IDE
5. Enter the follow command `mvn clean javafx:run` to run the project in IntelliJ.

## How to Build Maven project

1. Run `mvn package`. This will generate a `target` folder on top directory.
2. Find `project-2-project-2-team-8-1.0-SNAPSHOT.jar` file.
3. You can run it from the command line following the instruction from `How to Run - From the command line`

## Major technical choices

### Graph Stream API

A library that provides us a series method to read in and write to dot files, and also provided us with data structures representing graph, node, and edge.

### Apache CLI

A library that helps us with reading command line arguments. Apache CLI provided us with several useful features such as checking if an argument is given (useful for -v`) and getting the value of a certain argument

### JavaFX and TileFX

Two UI libraries that helped us build the UI, where `JavaFX` is the base UI library and `TileFX` provided us with some other useful JavaFX component, for example, the diagram that displays RAM usage

### Java.util.Concurrent

A Java built-in library that allows us to apply multi threading in our program, and also provide us with multiple useful features, for example, obtaining the result of the thread that finishes the first



| Member name |
| ----------- |
| Alan Zhang  |
| Harry Qu    |
| Qingyang Li |
| Mike Ma     |
| Frank Ji    |

