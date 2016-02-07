package _rna_3d_viewer;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * The two methods in this class were taken over from Sven_Fillinger.
 * sven.fillinger@mailbox.org
 *
 * Created by heumos on 01.02.16.
 */
public class PseudoknotUtils {

    public static int currentBestSolution = 0;

    /**
     * This method resolves pseudo-knots from a given list of pairs, representing
     * the pairing positions in the sequence.
     * The algorithm makes n*n pairwise checks for nestedness condition.
     * If false, then it will copy the list and removes one pair from the first list
     * and the other pair from the copied list. Both lists are both then evaluated
     * themselves for pseudo-knots recursively until no adjustments can be done anymore.
     * As the current best solution is carried with the recursion, a traceback is not
     * necessary here anymore :)
     * @param basePairs The initial list of pairs
     * @return A pseudo-knot free list of pairs.
     */
    public static List<Pair<Integer, Integer>> adjustPseudoknots(List<Pair<Integer, Integer>> basePairs){
        if(basePairs.isEmpty() || basePairs.size() < currentBestSolution){
            return basePairs;
        }

        for(Pair bond : basePairs){
            for(Pair otherBond : basePairs){
                if(isPseudoKnot(bond,otherBond)){
                    List copyBasePairs = new ArrayList<>(basePairs);
                    copyBasePairs.remove(otherBond);
                    basePairs.remove(bond);
                    List list1 = adjustPseudoknots(basePairs);
                    List list2 = adjustPseudoknots(copyBasePairs);
                    if(list1.size()> list2.size()){
                        return list1;
                    } else {
                        return list2;
                    }
                }
            }
        }
        currentBestSolution = basePairs.size();
        return basePairs;
    }

    /**
     * Find out if two pairs of secondary structure nucleotides
     * form a pseudo knot.
     * @param pair1 The first pair1
     * @param pair2 The other pair1 of the comparison
     * @return Forms a pseudo-knot, true or false
     */
    private static boolean isPseudoKnot(Pair<Integer, Integer> pair1,
                                        Pair<Integer, Integer> pair2){

        if(pair1.equals(pair2)){
            return false;
        }

        int     i,
                j,
                iPrime,
                jPrime;

        // sort out pairs' orders
        if (pair1.getKey() > pair2.getKey()){
            iPrime = pair1.getKey();
            jPrime = pair1.getValue();
            i = pair2.getKey();
            j = pair2.getValue();
        } else {
            i = pair1.getKey();
            j = pair1.getValue();
            iPrime = pair2.getKey();
            jPrime = pair2.getValue();
        }


        // i<j<i'<j'
        if (((i<j) && (iPrime<jPrime)) && (j<iPrime)){
            return false;
        }

        // i<i'<j'<j
        if (((i<iPrime) && (jPrime < j)) && (iPrime<jPrime)){
            return false;
        } else {
            // yeah, we found a pseudoknot!
            return true;
        }
    }

}
