package seq.sequence;

import seq.nucleotide.DnaNucleotide;
import seq.nucleotide.RnaNucleotide;

/**
 * Created by heumos on 12.11.15.
 */
public class SeqUtils {

    public enum SeqType{
        RNA, DNA, UNKNOWN, BOTH
    }

    public static SeqType checkSeqType(String s) {
        boolean isDna = true;
        boolean isRna = true;
        String[] interSplit = s.split("\n");
        for (String seq : interSplit) {
            for (int i = 0; i < seq.length(); i++) {
                char base = seq.charAt(i);
                if (!DnaNucleotide.ALLOWED_DNA_NUCLEOTIDES.contains(String.valueOf(base))) {
                    isDna = false;
                }
                if (!RnaNucleotide.ALLOWED_RNA_NUCLEOTIDES.contains(String.valueOf(base))) {
                    isRna = false;
                }
            }
        }
        if (isDna && isRna) {
            return SeqType.BOTH;
        } else {
            if (isDna && !isRna) {
                return SeqType.DNA;
            } else {
                if (!isDna && isRna) {
                    return SeqType.RNA;
                } else {
                    return SeqType.UNKNOWN;
                }
            }
        }
    }
}
