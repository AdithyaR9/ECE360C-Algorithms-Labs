package ProvidedFiles;
//Name: Adithya Ramanathan
//EID: ar74353


public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan


    // my vars
    int[][] opts;   // maximum impact values for subproblems
    int[][] khart;  // corresponding hours of medicine for opt impact

    public Program3() {
        this.calculator = null;
    }


    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3.
     *
     *  DO NOT MODIFY THIS METHOD
     *
     */
    public void initialize(ImpactCalculator ic) {
        this.calculator = ic;
        this.treatment_plan = new int[ic.getNumMedicines()];
    }


    /*
     * This method computes and returns the total impact of the treatment plan. It should
     * also fill in the treatment_plan array with the correct values.
     *
     * Each element of the treatment_plan array should contain the number of hours
     * that medicine i should be administered for. For example, if treatment_plan[2] = 5,
     * then medicine 2 should be administered for 5 hours.
     *
     */

    public int computeImpact() {

        int totalTime = calculator.getTotalTime();
        int numMedicines = calculator.getNumMedicines();

        // Put your code here


        // create and reset tables for solutions
        this.opts = new int[numMedicines][totalTime + 1];
        this.khart = new int[numMedicines][totalTime + 1];
        for (int i = 0; i < numMedicines; i++) {
            for (int j = 0; j <= totalTime; j++) {
                this.opts[i][j] = -1;
                this.khart[i][j] = -1;
            }
        }

        // computes the maximum impact recursively by considering all medicines up to the last one and up to the full available time
        int optimalSol = computeOptimalImpact(numMedicines - 1, totalTime);

        for (int i = 0; i < totalTime; i++) {
            computeOptimalImpact(numMedicines - 1, i);
        }


        int tim = totalTime;
        int meds = numMedicines - 1;
        for (int x = tim; x >= 0; x--) {
            if (this.opts[meds][x] < optimalSol) {
                tim = x + 1;
                break;
            }
        }
        for (int x = meds; x >= 0; x--) {
            if (this.opts[x][tim] < optimalSol) {
                meds = x + 1;
                break;
            }
        }

        recurse(meds, tim);

        return optimalSol;
    }


    // backtracks through the khart table to determine the exact number of hours
    // each medicine should be administered based on the previously computed solutions
    public void recurse(int meds, int tim) {
        int loc = tim;
        for (int a = meds; a >= 0; a--) {
            int kt = this.khart[a][loc];
            this.treatment_plan[a] = kt;
            loc = loc - kt;
        }
    }

    // gets max value from given arr
    public int max(int[] arr) {
        if (arr.length == 0) {
            return -1;
        }

        int locMax = 0;
        int valMax = arr[0];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > valMax) {
                valMax = arr[i];
                locMax = i;
            }
        }

        return locMax;
    }

    // Recursively computes the maximum possible impact for each subproblem
    // for using up to meds medicines within tim hours
    // stores the maximum possible impact and the corresponding hours in opts and khart

    public int computeOptimalImpact(int meds, int tim) {
        if (this.opts[meds][tim] != -1) {
            return this.opts[meds][tim];
        }

        if (meds == 0) {
            this.opts[meds][tim] = calculator.calculateImpact(meds, tim);
            this.khart[meds][tim] = tim;
            return this.opts[meds][tim];
        }

        if (tim == 0) {
            this.opts[meds][tim] = 0;
            this.khart[meds][tim] = 0;
            return 0;
        }

        int[] choces = new int[tim + 1];
        for (int i = 0; i <= tim; i++) {
            choces[i] = calculator.calculateImpact(meds, i) + computeOptimalImpact(meds - 1, tim - i);
        }

        int maxIndex = max(choces);
        int opt = choces[maxIndex];
        this.opts[meds][tim] = opt;
        this.khart[meds][tim] = maxIndex;

        return opt;
    }




    /*
     * This method prints the treatment plan.
     */
    public void printTreatmentPlan() {
        System.out.println("Please administer medicines 1 through n for the following amounts of time:\n");
        int hoursForI = 0;
        int n = calculator.getNumMedicines();
        for (int i = 0; i < n; i++) {
            // retrieve the amount of hours for medicine i
            hoursForI = treatment_plan[i]; // ... fill in here ...
            System.out.println("Medicine " + i + ": " + hoursForI);
        }
    }
}


