package model;

import io.FastaReader;
import seq.Nucleotide;
import seq.Sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 23.10.15.
 *
 * A java class representing the model of MVC pattern.
 * Here command line arguments are parsed and relevant data
 * is read in. Furthermore, this class provides methods for
 * formatting the given rna sequences in specific ways.
 *
 */
public class RnaSeqsModel {

    // the arguments from the command line
    private String[] args;

    // the list of rna sequences
    private List<Sequence> sequences;

    // the sequence width
    private int seqWidth;

    /**
     * A rna seq model only needs the command line arguments.
     * @param args the command line arguments from the main method.
     */
    public RnaSeqsModel(String[] args) {
        this.args = args;
        init();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    /**
     * Initialize this class:
     * (1) Parse command line arguments.
     * (2) Read in FASTA file.
     * (3) Set the sequences list.
     */
    private void init() {
        String fastaFile = this.args[0];
        int seqLineLength = 60;

        try {
            seqLineLength = Integer.valueOf(this.args[1]);
            if (seqLineLength < 10) {
                System.err.println("The specified length for sequence printing is too short.");
                System.err.println("Please enter a length of at least 10.");
                System.exit(1);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("You did not specify a length for sequence printing.");
            System.out.println("The default length of 60 will be used.");
            System.out.println();
        }

        FastaReader fastaReader = new FastaReader(fastaFile);
        this.sequences = fastaReader.readFasta();
        this.seqWidth = seqLineLength;
    }

    public String getFullStringRepresentation() {
        StringBuilder seqBuilder = new StringBuilder();
        String newLine = "\n";
        int maxSeqIdLength = calcMaxSeqIdLength();
        String blankSeqId = String.format("%" + maxSeqIdLength + "s", "");
        int seqLength = this.sequences.get(0).getSequenceData().size();
        int integerPart = seqLength / seqWidth;
        int residualPart = seqLength % seqWidth;
        String numbering = "";

        int seqStartIndex = 0;
        int seqEndIndex = seqWidth;

        for (int i = 0; i < integerPart; i++) {
            numbering = String.format("%-" + maxSeqIdLength + "s    %s",
                    blankSeqId,
                    createHeaderCounterString(seqStartIndex + 1, seqEndIndex, seqWidth));
            seqBuilder.append(numbering).append(newLine);
            for (Sequence seq : this.sequences) {
                String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqEndIndex);
                String sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                        seq.getSeqId(),
                        nucleotides);
                seqBuilder.append(sequence).append(newLine);
            }
            seqStartIndex = seqEndIndex;
            seqEndIndex = seqEndIndex + seqWidth;
        }

        numbering = String.format("%-" + maxSeqIdLength + "s    %s",
                blankSeqId,
                createHeaderCounterString(seqStartIndex + 1, seqLength + 1, residualPart));
        seqBuilder.append(numbering).append(newLine);

        for (Sequence seq : this.sequences) {
            String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqLength);
            String sequences = String.format("%-" + maxSeqIdLength + "s    %s",
                    seq.getSeqId(),
                    nucleotides);
            seqBuilder.append(sequences).append(newLine);
        }

        return seqBuilder.toString();
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
     * @param nucleotideList the nucleotide list one wants to fetch from.
     * @param start the start index in the nucleotide list.
     * @param end the end index in the nucleotide list.
     * @return the fetched nucleotides as one String.
     */
    private String fetchNucleotides(ArrayList<Nucleotide> nucleotideList, int start, int end) {
        List<Nucleotide> nucleotideSubList = nucleotideList.subList(start, end);
        StringBuilder nucleotideBuilder = new StringBuilder();
        for (Nucleotide nucleotide : nucleotideSubList) {
            nucleotideBuilder.append(nucleotide);
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
