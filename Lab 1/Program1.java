/*
 * Name: Adithya Ramanathan
 * EID: ar74353
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * <p>
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * <p>
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    private void initmatches(int n, ArrayList<Integer> finalStudMatches) {
        for (int i = 0; i < n; i++)
            finalStudMatches.add(-1);
    }

    private void makeInvStudPrefList(int n, int m, Matching problem, int[][] invStudPrefList) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                invStudPrefList[i][problem.getStudentPreference().get(i).get(j)] = j;
            }
        }
    }

    private void makeInvUniPrefList(int m, int n, Matching problem, int[][] invUniPrefList) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                invUniPrefList[i][problem.getUniversityPreference().get(i).get(j)] = j;
            }
        }
    }

    private boolean chkIfOfferredAll(int[] numUniStudsOfferedTo, int m, ArrayList<Integer> finalStudMatches) {
        for (int i = 0; i < numUniStudsOfferedTo.length; i++) {
            if (numUniStudsOfferedTo[i] < m) {
                if (finalStudMatches.get(i) == -1) return false;
            }
        }
        return true;
    }

    private int getLeastPrefdStud(ArrayList<Integer> finalStudMatches, int uni, int[][] invUniPrefList) {
        int leastId = -1;
        int leastPrefd = -1;
        for (int i = 0; i < finalStudMatches.size(); i++) {
            if (finalStudMatches.get(i) == uni) {
                if (invUniPrefList[uni][i] > leastPrefd) {
                    leastPrefd = invUniPrefList[uni][i];
                    leastId = i;
                }
            }
        }
        return leastId;
    }

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        /* TODO implement this function */

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();

        int[][] invStudPrefList = new int[n][m];
        makeInvStudPrefList(n, m, problem, invStudPrefList);


        for (int i = 0; i < n; i++) {
            int uniMatch1 = problem.getStudentMatching().get(i);
            if (uniMatch1 != -1) {
                for (int stud2 : problem.getUniversityPreference().get(uniMatch1)) {
                    if (stud2 == i) break;

                    int uniMatch2 = problem.getStudentMatching().get(stud2);

                    if (uniMatch2 == -1) return false;
                    if (invStudPrefList[stud2][uniMatch1] < invStudPrefList[stud2][uniMatch2]) return false;
                }
            }
        }

        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_universityoptimal(Matching problem) {
        /* TODO implement this function */

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();
        int totalCombinedUniPositions = problem.totalUniversityPositions();
        int nextUni = 0;
        ArrayList<Integer> finalStudMatches = new ArrayList<Integer>(n);
        ArrayList<Integer> positionsPerUniRemaining = new ArrayList<Integer>(problem.getUniversityPositions());
        int[] numStudsUniOfferedTo = new int[m];
        int[][] invStudPrefList = new int[n][m];


        // init students matches to -1
        initmatches(n, finalStudMatches);
        // create inverse preference list of the students
        makeInvStudPrefList(n, m, problem, invStudPrefList);

        while (totalCombinedUniPositions > 0) {
            // uni full?
            if (positionsPerUniRemaining.get(nextUni) == 0) {
                nextUni = (nextUni + 1) % m;
            } else {
                int nextPrefStud = problem.getUniversityPreference().get(nextUni).get(numStudsUniOfferedTo[nextUni]);
                int nextPrefStudsUni = finalStudMatches.get(nextPrefStud);

                // does stud have match
                if (nextPrefStudsUni == -1) {
                    finalStudMatches.set(nextPrefStud, nextUni);
                    positionsPerUniRemaining.set(nextUni, positionsPerUniRemaining.get(nextUni) - 1);
                    totalCombinedUniPositions--;
                } else {
                    // chk prefs if stud alr matched
                    if (invStudPrefList[nextPrefStud][nextUni] < invStudPrefList[nextPrefStud][nextPrefStudsUni]) {
                        finalStudMatches.set(nextPrefStud, nextUni);
                        positionsPerUniRemaining.set(nextPrefStudsUni, positionsPerUniRemaining.get(nextPrefStudsUni) + 1);
                        positionsPerUniRemaining.set(nextUni, positionsPerUniRemaining.get(nextUni) - 1);
                    }
                }
                numStudsUniOfferedTo[nextUni]++;
            }
        }
        problem.setStudentMatching(finalStudMatches);
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();
        int nextStud = 0;
        int totalStudsNotMatched = n;
        int[] numUniStudsOfferredTo = new int[n];
        int[][] invUniPrefList = new int[m][n];
        ArrayList<Integer> finalStudMatches = new ArrayList<Integer>(n);
        ArrayList<Integer> positionsPerUniRemaining = new ArrayList<Integer>(problem.getUniversityPositions());


        // init student matches to -1
        initmatches(n, finalStudMatches);
        // creat inverse pref list
        makeInvUniPrefList(m, n, problem, invUniPrefList);

        while (totalStudsNotMatched > 0 && !chkIfOfferredAll(numUniStudsOfferredTo, m, finalStudMatches)) {
            // skip student if matched or uni full
            if (finalStudMatches.get(nextStud) != -1 || numUniStudsOfferredTo[nextStud] == m) {
                nextStud = (nextStud + 1) % n;
            } else {
                // uni full?
                int nextPrefUni = problem.getStudentPreference().get(nextStud).get(numUniStudsOfferredTo[nextStud]);
                if (positionsPerUniRemaining.get(nextPrefUni) > 0) {
                    finalStudMatches.set(nextStud, nextPrefUni);
                    positionsPerUniRemaining.set(nextPrefUni, positionsPerUniRemaining.get(nextPrefUni) - 1);
                    totalStudsNotMatched--;
                } else {
                    // chk ranks to add new stud
                    int leastId = getLeastPrefdStud(finalStudMatches, nextPrefUni, invUniPrefList);
                    if (invUniPrefList[nextPrefUni][nextStud] < invUniPrefList[nextPrefUni][leastId]) {
                        finalStudMatches.set(nextStud, nextPrefUni);
                        finalStudMatches.set(leastId, -1);
                    }
                }
                numUniStudsOfferredTo[nextStud]++;
            }
        }
        problem.setStudentMatching(finalStudMatches);
        return problem;
    }


}