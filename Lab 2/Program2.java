/*
 * Name: Adithya Ramanathan
 * EID: ar74353
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission
// Do not add extra imports

import java.util.ArrayList;

public class Program2 {

    /**
     * findMinimumDistance
     *
     * @param problem - contains the regions, the start region and the end region of the graph
     * @return the minimum distance possible to get from start region to end region in the given problem.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {
        // TODO

        // Steps 1-2 - initializations
        resetMinDist(problem);
        problem.getStartRegion().setMinDist(0);

        // Steps 3-5 - find min route
        runDijkstra(problem);

        // Step 6 - return solution
        return problem.getEndRegion().getMinDist();
    }

    private void resetMinDist(Problem problem) {
        for (Region r : problem.getRegions()) {
            r.setMinDist(Integer.MAX_VALUE);
        }
    }

    private void runDijkstra(Problem problem) {
        Heap<Region> priorityQ = new Heap<>();
        ArrayList<Region> regions = problem.getRegions();
        priorityQ.buildHeap(regions);

        while (priorityQ.getSize() > 0) {
            Region r = priorityQ.extractMin();
            ArrayList<Region> nearbys = r.getNeighbors();
            ArrayList<Integer> weightages = r.getWeights();

            for (int x = 0; x < nearbys.size(); x++) {
                Region near = nearbys.get(x);
                int weightage = weightages.get(x);
                int dist = r.getMinDist() + weightage;

                relaxRegion(near, dist, priorityQ);
            }
        }
    }

    private void relaxRegion(Region nearby, int distance, Heap<Region> minHeap) {
        if (distance < nearby.getMinDist()) {
            nearby.setMinDist(distance);
            minHeap.changeKey(nearby, distance);
        }
    }

}
