package main;

import algorithm.AStarUtil;
import algorithm.ParallelAStar;
import algorithm.DFSParallel;
import io.InputLoader;
import io.OutputFormatter;
import org.graphstream.graph.Node;
import algorithm.AStar;
import models.Digraph;
import algorithm.PartialSolution;
import utils.GraphGenerator;

import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {


        long startTime = System.currentTimeMillis();

//        if (args.length < 2) {
//            System.err.println("Invalid arguments");
//            return;
//        }
//
//        String path = args[0];
//        int processAmount = Integer.parseInt(args[1]);
//
//        InputLoader.setNumOfProcessors(processAmount);
//        InputLoader.loadDotFileFromPath(path);
//
//        AStar aStar = new AStar();
//        PartialSolution solution = aStar.buildTree();
//
//        OutputFormatter outputFormatter = new OutputFormatter();
//        outputFormatter.aStar(solution, path.substring(0, path.length() - 4));

        Digraph inputGraph = InputLoader.loadDotFileFromPath("examples/g11/in.dot");
        InputLoader.setNumOfProcessors(4);
        ParallelAStar parallelAStar = new ParallelAStar(4);
        System.out.println(parallelAStar.build().getInfo());


//        GraphGenerator.generateRandomGraphs(10);

//       if (args.length < 2) {
//           System.err.println("Invalid arguments");
//            return;
//        }


//        DFSParallel dfsParallel = new DFSParallel(4);
//        dfsParallel.findBestPartial();
//        System.out.println(dfsParallel.getBestPartialSolution().getInfo());


//        AStarUtil aStarUtil = new AStarUtil();
//        System.out.println(aStarUtil.getBestPartialSolution().getInfo());
//        ParallelAStar parallelAStar = new ParallelAStar(2);

//        PartialSolution build = parallelAStar.build();

//        System.out.println(build);

//        System.out.println(build.getInfo());
//
//
////        InputLoader.print(digraph, false);
////        BruteForce solutionTree = BruteForce.getSolutionTree(digraph, 1);
////        System.out.println(solutionTree.getNodeCount());
//
////        System.out.println("\n=== Final print ===");
////        System.out.println(solutionTree.getAllNodes().size());
////        InputLoader.print(solutionTree, false);
//
////        System.out.println(digraph.getAllNodes());
//
////        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));
//
////        System.out.println(myGraph.getCriticalPath());
//
////
////        BruteForce solutionTree = new BruteForce(2);
////        solutionTree.build(solutionTree.getRoot());
////        solutionTree.print();
//
//
//
//        AStar aStar = new AStar();
//        PartialSolution p = aStar.buildTree(new PartialSolution());
//        System.out.println(p.getInfo());
//        new OutputFormatter().aStar(p, inputGraph);

//        System.out.println(p.calculateEndScheduleTime());
//        PartialSolution p = aStar.getLastPartialSolution();
//        System.out.println(p);

        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println("time used：" + (endTime - startTime)/1000 + "s");
    }


    private static int countLeaves(Digraph digraph) {
        int count = 0;
        for (Node node : digraph.getAllNodes()) {
            if (digraph.getOutDegreeOfNode(node) == 0) {
                count++;
            }
        }
        return count;
    }


}
