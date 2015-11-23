package model;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerModel {

    public static boolean validateDotBrackets(String secStruct) {
        if (secStruct.length() < 1) {
            return false;
        }
        String dotBrackets = ".()";
        for (int i = 0; i < secStruct.length(); i++) {
            char c = secStruct.charAt(i);
            if (!dotBrackets.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}
