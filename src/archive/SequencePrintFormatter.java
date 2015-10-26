package archive;

import seq.RnaNucleotide;
import seq.Sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 15.10.15.
 *
 * Java class providing a function to print out a list of sequences.
 *
 */
public class SequencePrintFormatter {

    // the sequence list one wants to print out
    private List<Sequence> sequences;

    public SequencePrintFormatter(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    /**
     * The main method of this class.
     * This methods prints the list of sequences in this class to stdout.
     *
     * @param seqWidth the length of the sequence line.
     */
    public void printSequences(int seqWidth) {
        int maxSeqIdLength = calcMaxSeqIdLength();
        String blankSeqId = String.format("%" + maxSeqIdLength + "s", "");
        int seqLength = this.sequences.get(0).getSequenceData().size();
        int integerPart = seqLength / seqWidth;
        int residualPart = seqLength % seqWidth;

        int seqStartIndex = 0;
        int seqEndIndex = seqWidth;

        for (int i = 0; i < integerPart; i++) {
            System.out.println(String.format("%-" + maxSeqIdLength + "s    %s",
                    blankSeqId,
                    createHeaderCounterString(seqStartIndex + 1, seqEndIndex, seqWidth)));
            for (Sequence seq : this.sequences) {
                String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqEndIndex);
                String printout = String.format("%-" + maxSeqIdLength + "s    %s",
                        seq.getSeqId(),
                        nucleotides);
                System.out.println(printout);
            }
            seqStartIndex = seqEndIndex;
            seqEndIndex = seqEndIndex + seqWidth;
        }

        System.out.println(String.format("%-" + maxSeqIdLength + "s    %s",
                blankSeqId,
                createHeaderCounterString(seqStartIndex + 1, seqLength + 1, residualPart)));

        for (Sequence seq : this.sequences) {
            String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqLength);
            String printout = String.format("%-" + maxSeqIdLength + "s    %s",
                    seq.getSeqId(),
                    nucleotides);
            System.out.println(printout);
        }
    }

    /**
     * Calculate the maximum length of the identifiers of a given list of sequences.
     * @return the maximum sequence identifier length.
     */
    private int calcMaxSeqIdLength() {
        List<String> seqIds = new ArrayList<String>();
        for (Sequence seq : this.sequences) {
            seqIds.add(seq.getSeqId());
        }

        int maxSeqIdLength = seqIds.stream()
                .mapToInt(seqId -> seqId.length())
                .max()
                .getAsInt();
        return maxSeqIdLength;
    }

    /**
     * Fetches nucleotides from a nucleotide list.
     * Note, that the end index is exclusively.
     *
     * @param rnaNucleotideList the nucleotide list one wants to fetch from.
     * @param start the start index in the nucleotide list.
     * @param end the end index in the nucleotide list.
     * @return the fetched nucleotides as one String.
     */
    private String fetchNucleotides(ArrayList<RnaNucleotide> rnaNucleotideList, int start, int end) {
        List<RnaNucleotide> rnaNucleotideSubList = rnaNucleotideList.subList(start, end);
        StringBuilder nucleotideBuilder = new StringBuilder();
        for (RnaNucleotide rnaNucleotide : rnaNucleotideSubList) {
            nucleotideBuilder.append(rnaNucleotide);
        }
        return nucleotideBuilder.toString();
    }

    /**
     * Creates the String with nucleotide counters.
     *
     * @param start the current start nucleotide position.
     * @param end the current end nucleotide position.
     * @param width the current sequence width.
     * @return
     */
    private String createHeaderCounterString(int start, int end, int width) {
        int startLength = String.valueOf(start).length();
        int endLength = String.valueOf(end).length();
        int numberOfSpaces = width - startLength - endLength;
        String space = String.format("%" + numberOfSpaces + "s", " ");
        return String.valueOf(start) + space + String.valueOf(end);
    }
}
