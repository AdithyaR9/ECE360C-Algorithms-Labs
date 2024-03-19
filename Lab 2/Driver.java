// This Driver file will be replaced by ours during grading
// Do not include this file in your final submission

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Driver {

    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    public static  Program2 program = new Program2(); // instance of your graph
    public static  boolean testDijkstra; // set to true by -d flag

    private static void usage() { // error message
        System.err.println("usage: java Driver [-d] [-m] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-d\tTest Dijkstra implementation");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        parseArgs(args);
        if(testDijkstra) {
            Problem problem = new Problem();
            problem.setRegions(parseDijkstraInputFile(filename));;
            testDijkstraRun(problem);
        }else if(testHeap) {
            Heap<Region> heap = new Heap<>();
            heap.buildHeap(parseHeapInputFile(filename));
            testHeapRun(heap);
        }else{
            usage();
        }
    }

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }
        filename = "";
        testHeap = false;
        testDijkstra = false;
        for (String s : args) {
            if (s.equals("-h")) {
                testHeap = true;
            } else if(s.equals("-d")){
                testDijkstra = true;
            }
            else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

    }

    public static ArrayList<Region> parseDijkstraInputFile(String filename) throws FileNotFoundException {
        ArrayList<Region> regions = new ArrayList<>();

        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] pricePairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            Region currentStudent = new Region(currNode);
            regions.add(currNode, currentStudent);
            ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer priceVal = Integer.parseInt(pricePairs[k]);
                currNeighbors.add(new NeighborWeightTuple(neighborVal, priceVal));
            }
        }
        for (int i = 0; i < regions.size(); ++i) {
            Region currStudent = regions.get(i);
            ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
            for (NeighborWeightTuple neighbor : neighbors) {
                currStudent.setNeighborAndWeight(regions.get(neighbor.neighborID), neighbor.weight);
            }
        }
        sc.close();
        return regions;
    }

    public static ArrayList<Region> parseHeapInputFile(String filename) throws FileNotFoundException {
        ArrayList<Region> regions = new ArrayList<>();

        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        for (int i = 0; i < inputSize.length; ++i) {
            Region region = new Region(i);
            region.setMinDist(Integer.valueOf(inputSize[i]));
            regions.add(region);
        }
        sc.close();
        return regions;
    }

    // feel free to alter this method however you wish, we will replace it with our own version during grading
    public static void testDijkstraRun(Problem problem) throws FileNotFoundException {
        ArrayList<Region> regions = problem.getRegions();
        problem.setStartRegion(0);
        problem.setEndRegion(regions.size()-1);
        System.out.println("Length of shortest route from start region to end region: \n" +
                program.findMinimumRouteDistance(problem));
    }
    
    // *** This function allows you to debug your heap, this is not how we are going to test your heap. ***
    // feel free to alter this method however you wish, we will replace it with our own version during grading
    public static void testHeapRun(Heap<Region> heap) throws FileNotFoundException {
        System.out.println("Original Heap:");
        System.out.println(heap.toString());

        Region r0 = new Region(7);
        r0.setMinDist(54);
        heap.insertNode(r0);
        System.out.println("Heap after inserting a member r0(id: 7, value: 54): ");
        System.out.println(heap.toString());

        System.out.println("The minimal member of the heap: ");
        Region minRegion = heap.findMin();
        System.out.println("id: " + minRegion.getId() + " value: " + minRegion.getValue() + "\n");

        System.out.println("The heap after extracting the minimal member: ");
        heap.extractMin();
        System.out.println(heap.toString());

        System.out.println("The heap after changing the value of r0 from 54 to 1");
        heap.changeKey(r0, 1);
        System.out.println(heap.toString());

        System.out.println("The heap after deleting r0: ");
        heap.delete(r0);
        System.out.println(heap);
    }

    private static boolean assertEquals(int a, int b) {
        // print out the two values and if they are equal or not
        System.out.println(a + " == " + b + " : " + (a == b));
        return a == b;
    }

    private static class NeighborWeightTuple {
        public Integer neighborID;
        public Integer weight;

        NeighborWeightTuple(Integer neighborID, Integer weight) {
            this.neighborID = neighborID;
            this.weight = weight;
        }
    }

}
