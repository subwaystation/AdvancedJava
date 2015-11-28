package util;

/**
 * Created by heumos on 28.11.15.
 */
public class ArrayUtils {

    public static double[][] deepCopyDoubleMatrix(double[][] input) {
        if (input == null)
            return null;
        double[][] result = new double[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}
