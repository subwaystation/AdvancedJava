package lib;

/**
 * Created by heumos on 30.10.15.
 */
public class StringFormatter {

    public static String formatStringSequence(String s, int lineWidth) {
        StringBuilder result = new StringBuilder();
        int len = s.length();
        int full = len/lineWidth;
        int rest = len%lineWidth;
        int begin = 0;
        int end = lineWidth;
        for(int i = 0; i < full; i++) {
            result.append(s.substring(begin, end));
            result.append("\n");
            begin += lineWidth;
            end += lineWidth;
        }
        if (rest != 0) {
            result.append(s.substring(len-rest, len));
        } else {
            if (result.length() > 0) {
                result.deleteCharAt(result.length()-1);
            }
        }
        return result.toString();
    }
}
