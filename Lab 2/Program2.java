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
     * @param problem  - contains the regions, the start region and the end region of the graph
     * @return the minimum distance possible to get from start region to end region in the given problem.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {
        // TODO

        // Initialize
        ArrayList<Region> regions = problem.getRegions();
        PriorityQueue<Region> pq = new PriorityQueue<>(Comparator.comparingInt(Region::getMinDist));
        problem.reset_minDist();
        problem.getStartRegion().setMinDist(0);
        pq.offer(problem.getStartRegion());

        // Iterate until the priority queue is empty
        while (!pq.isEmpty()) {
            // min distance region
            Region cRegi = pq.poll();

            // peek all nearby regions
            for (Edge edge : cRegi.getEdges()) {
                Region nearby = edge.getDestination();
                int distanceToNeighbor = cRegi.getMinDist() + edge.getWeight();

                // relax region node
                if (distanceToNeighbor < nearby.getMinDist()) {
                    nearby.setMinDist(distanceToNeighbor);
                    pq.offer(nearby);
                }
            }
        }

        // return min dist
        return problem.getEndRegion().getMinDist();
    }

}
