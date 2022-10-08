//import io.InputLoader;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import algorithm.AStar;
//import algorithm.PartialSolution;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AStarUniTest {
//
//    @Nested
//    class Graph8 {
//
//        @BeforeEach
//        public void loadInputGraph() {
//            InputLoader.loadDotFile("g8");
//        }
//
//        @Test
//        public void TwoProcessors() {
//            InputLoader.setNumOfProcessors(2);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(28, solution.calculateEndScheduleTime());
//        }
//
//        @Test
//        public void FourProcessors() {
//            InputLoader.setNumOfProcessors(4);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(22, solution.calculateEndScheduleTime());
//        }
//
//    }
//
//    @Nested
//    class Graph3 {
//
//        @BeforeEach
//        public void loadInputGraph() {
//            InputLoader.loadDotFile("g3");
//        }
//
//        @Test
//        public void TwoProcessors() {
//            InputLoader.setNumOfProcessors(2);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(581, solution.calculateEndScheduleTime());
//        }
//
//        @Test
//        public void FourProcessors() {
//            InputLoader.setNumOfProcessors(4);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(581, solution.calculateEndScheduleTime());
//        }
//
//    }
//
//    @Nested
//    class Graph9 {
//
//        @BeforeEach
//        public void loadInputGraph() {
//            InputLoader.loadDotFile("g9");
//        }
//
//        @Test
//        public void TwoProcessors() {
//            InputLoader.setNumOfProcessors(2);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(55, solution.calculateEndScheduleTime());
//        }
//
//        @Test
//        public void FourProcessors() {
//            InputLoader.setNumOfProcessors(4);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(55, solution.calculateEndScheduleTime());
//        }
//
//    }
//
//    @Nested
//    class Graph10 {
//
//        @BeforeEach
//        public void loadInputGraph() {
//            InputLoader.loadDotFile("g10");
//        }
//
//        @Test
//        public void TwoProcessors() {
//            InputLoader.setNumOfProcessors(2);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(50, solution.calculateEndScheduleTime());
//        }
//
//        @Test
//        public void FourProcessors() {
//            InputLoader.setNumOfProcessors(4);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(50, solution.calculateEndScheduleTime());
//        }
//
//    }
//
//    @Nested
//    class Graph11 {
//
//        @BeforeEach
//        public void loadInputGraph() {
//            InputLoader.loadDotFile("g11");
//        }
//
//        @Test
//        public void TwoProcessors() {
//            InputLoader.setNumOfProcessors(2);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(350, solution.calculateEndScheduleTime());
//        }
//
//        @Test
//        public void FourProcessors() {
//            InputLoader.setNumOfProcessors(4);
//            AStar aStar = new AStar();
//            PartialSolution solution = aStar.buildTree();
//            assertEquals(227, solution.calculateEndScheduleTime());
//        }
//
//    }
//
//}
