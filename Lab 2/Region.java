/*
 * Name: Adithya Ramanathan
 * EID: ar74353
 */

// DO NOT MODIFY THIS FILE IN ANY WAY !!! 😂
// DO NOT INCLUDE THIS FILE IN YOUR SUBMISSION

import java.util.*;

public class Region extends HeapMember {

    private ArrayList<Region> neighbors;
    private ArrayList<Integer> weights;

    public Region(int id) {
        super(id);

        neighbors = new ArrayList<Region>();
        weights = new ArrayList<Integer>();
    }

    public void setNeighborAndWeight(Region n, Integer w) {
        neighbors.add(n);
        weights.add(w);
    }

    public ArrayList<Region> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Integer> getWeights() {
        return weights;
    }

    public void updateWeight(Integer index, Integer new_weight){
        weights.set(index, new_weight);
    }

    public int getMinDist() { return this.getValue(); }


    // Does not call heapify
    public void setMinDist(int x) {
        this.setValue(x);
    }

    public void resetMinDist() {
        this.setValue(Integer.MAX_VALUE);
    }

    public int get_Id() {
        return this.getId();
    }

    // clear neighbors array
    public void clearNeighbors(){
        neighbors = new ArrayList<Region>();
        weights = new ArrayList<Integer>();
    }

}
