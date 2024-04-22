package ProvidedFiles;

//Name: Adithya Ramanathan
//EID: ar74353


public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan


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
        int[][] dp = new int[numMedicines + 1][totalTime + 1];

        // fill dp table looking at each medicing and each possible allocation
        for (int i = 1; i <= numMedicines; i++) {
            for (int j = 0; j <= totalTime; j++) {
                for (int t = 0; t <= j; t++) {
                    int impCurr = calculator.calculateImpact(i - 1, t) + dp[i - 1][j - t];
                    if (impCurr > dp[i][j]) {
                        dp[i][j] = impCurr;
                        if (j == totalTime) {
                            treatment_plan[i - 1] = t;
                        }
                    }
                }
            }
        }

        // backtrack to get optimal treament time
        recurseBcktrck(dp, numMedicines, totalTime);
        return dp[numMedicines][totalTime];
    }

    //  backtracks to set treatment plan using existing dp table
    private void recurseBcktrck(int[][] dp, int numMedicines, int totalTime) {
        for (int i = numMedicines, j = totalTime; i > 0; i--) {
            for (int t = 0; t <= j; t++) {
                if (dp[i][j] == calculator.calculateImpact(i - 1, t) + dp[i - 1][j - t]) {
                    treatment_plan[i - 1] = t;
                    j -= t;
                    break;
                }
            }
        }
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


