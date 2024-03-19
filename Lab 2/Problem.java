import java.util.ArrayList;

// DO NOT INCLUDE THIS FILE IN YOUR SUBMISSION
public class Problem {
    private ArrayList<Region> regions; // this is a list of all Regions, populated by Driver class

    private Region startRegion;

    private Region endRegion;

    public Problem(){
        regions = new ArrayList<Region>();
    }; // default constructor

    // returns a list of all regions
    public ArrayList<Region> getRegions() {
        return regions;
    }

    // set regions
    // Should only be called by Driver class
    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }


    public void setStartRegion(int start) {
        this.startRegion = regions.get(start);
    }

    public Region getStartRegion(){
        return this.startRegion;
    }

    public void setEndRegion(int end) {
        this.endRegion = regions.get(end);
    }

    public Region getEndRegion() {
        return this.endRegion;
    }

    public void reset_minDist(){
        for(Region region : regions){
            region.setMinDist(Integer.MAX_VALUE);
        }
    }

    public void printMinDist(){
        for(Region region : regions) {
            System.out.println(region.getId() + ": " + region.getValue());
        }
    }

}
