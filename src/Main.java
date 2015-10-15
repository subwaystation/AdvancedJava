import io.FastaReader;
import seq.Nucleotide;
import seq.Sequence;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FastaReader fastaReader = new FastaReader(args[0]);
        List<Sequence> sequences = fastaReader.readFasta();
/*        List<String> seqIds = new ArrayList<String>();
        for (Sequence seq : sequences) {
            System.out.println(seq.getSeqId());
            seqIds.add(seq.getSeqId());
            System.out.println(seq.getSequenceData());
        }

        int maxIdLength = seqIds.stream()
                .mapToInt(seqId -> seqId.length())
                .max()
                .getAsInt();
        System.out.println(String.format("%-" + maxIdLength + "s %s", "                       1", "                                                            " + "60"));
        for (Sequence seq : sequences) {
            List<Nucleotide> nucleotideList = seq.getSequenceData();
            StringBuilder nucleotideBuilder = new StringBuilder();
            for (Nucleotide base : nucleotideList) {
                nucleotideBuilder.append(base);
            }

            String test = String.format("%-" + maxIdLength + "s %s", seq.getSeqId(), nucleotideBuilder.toString());
            System.out.println(test);
        }

        System.out.println(5/2);
        System.out.println(5 % 2);
        ArrayList<String> ab = new ArrayList<String>();
        ab.add("a");
        ab.add("b");
        ab.add("c");
        System.out.println(ab.subList(0, 1));*/
        SequencePrintFormatter sequencePrintFormatter = new SequencePrintFormatter(sequences);
        sequencePrintFormatter.printSequences(60);
    }

}