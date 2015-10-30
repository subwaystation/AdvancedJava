package model;

import io.FastaReader;
import seq.nucleotide.ANucleotide;
import seq.sequence.ASequence;

import java.util.ArrayList;
import java.util.Arrays;
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
public class AlignmentModel {

    // the arguments from the command line
    private String[] args;

    // the list of rna sequences
    private List<ASequence> sequences;

    // the sequence width
    private int seqWidth;

    // boolean array declaring what of the alignment shall be displayed
    private boolean[] alignemntModes;

    /**
     * A rna seq model only needs the command line arguments.
     * @param args the command line arguments from the main method.
     */
    public AlignmentModel(String[] args) {
        this.args = args;
        init();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public List<ASequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<ASequence> sequences) {
        this.sequences = sequences;
    }

    public int getSeqWidth() {
        return seqWidth;
    }

    public void setSeqWidth(int seqWidth) {
        this.seqWidth = seqWidth;
    }

    public boolean[] getAlignemntModes() {
        return alignemntModes;
    }

    public void setAlignemntModes(boolean[] alignemntModes) {
        this.alignemntModes = alignemntModes;
    }

    /**
     * Initialize this class:
     * (1) Parse command line arguments.
     * (2) Read in FASTA file.
     * (3) Set the sequences list.
     */
    private void init() {
        String fastaFile = "";
        try {
            fastaFile = this.args[0];
        } catch (IndexOutOfBoundsException e) {
            System.err.println("You did not specify an input file.  Please chose an appropriate" +
                    "RNA FASTA file.");
            System.exit(1);
        }

        validateFastaEnding(fastaFile);

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
        this.alignemntModes = new boolean[3];
    }

    private void validateFastaEnding(String fastaFile) {
        // allowed endings see wikipedia: https://en.wikipedia.org/wiki/FASTA_format
        // chapter "File extension"
        String[] allowedFastaEndings = {".fa", ".fasta", ".fas", ".fsa"};
        for (String ending : allowedFastaEndings) {
            if (fastaFile.endsWith(ending)) {
                return;
            }
        }
        // no positive validation
        System.err.println(fastaFile  + "is no valid FASTA file.");
        System.err.println("File extensions allowed are: " + Arrays.toString(allowedFastaEndings));
        System.exit(1);
    }

    /**
     * Set the identifier visibility.
     * @param bool boolean specifying if the identifiers shall be included
     *             in the StringRepresentation.
     */
    public void setIdentifierVisibilty(boolean bool) {
        this.getAlignemntModes()[0] = bool;
    }

    /**
     * Set the sequence visibility.
     * @param bool boolean specifying if the sequences shall be included
     *             in the StringRepresentation.
     */
    public void setSequenceVisibility(boolean bool) {
        this.getAlignemntModes()[1] = bool;
    }

    /**
     * Set the numbering visibility.
     * @param bool boolean specifying if the numbering shall be included
     *             in the StringRepresentation.
     */
    public void setNumberingVisibility(boolean bool) {
        this.getAlignemntModes()[2] = bool;
    }

    public String getStringRepresentation() {
        // TODO refactoring!!!
        boolean[] alignmentModes = this.getAlignemntModes();
        boolean includeIdentifiers = alignmentModes[0];
        boolean includeSequences = alignmentModes[1];
        boolean includeNumbering = alignmentModes[2];

        StringBuilder seqBuilder = new StringBuilder();
        String newLine = "\n";
        int maxSeqIdLength = calcMaxSeqIdLength();
        String blankSeqId = String.format("%" + maxSeqIdLength + "s", "");
        int seqLength = this.sequences.get(0).getSequenceData().size();
        int integerPart = seqLength / seqWidth;
        int residualPart = seqLength % seqWidth;
        String numbering;

        int seqStartIndex = 0;
        int seqEndIndex = seqWidth;

        for (int i = 0; i < integerPart; i++) {
            // do we want numbering?
            if (includeNumbering) {
                numbering = String.format("%-" + maxSeqIdLength + "s    %s",
                        blankSeqId,
                        createHeaderCounterString(seqStartIndex + 1, seqEndIndex, this.seqWidth));
                seqBuilder.append(numbering).append(newLine);
            } else {
                seqBuilder.append(newLine);
            }

            for (ASequence seq : this.sequences) {
                // do we want to include the sequences?
                String sequence;
                if (includeSequences) {
                    String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqEndIndex);
                    if (includeIdentifiers) {
                        sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                                seq.getSeqId(),
                                nucleotides);
                    } else {
                        sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                                blankSeqId,
                                nucleotides);
                    }
                } else {
                    if (includeIdentifiers) {
                        sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                                seq.getSeqId(),
                                "") + String.format("%" + this.seqWidth + "s", " ");;
                    } else {
                        sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                                blankSeqId,
                                "");
                    }
                }
                seqBuilder.append(sequence).append(newLine);
            }
            seqStartIndex = seqEndIndex;
            seqEndIndex = seqEndIndex + seqWidth;
        }

        if (includeNumbering) {
            numbering = String.format("%-" + maxSeqIdLength + "s    %s",
                    blankSeqId,
                    createHeaderCounterString(seqStartIndex + 1, seqLength, residualPart));
            seqBuilder.append(numbering).append(newLine);
        } else {
            seqBuilder.append(newLine);
        }

        for (ASequence seq : this.sequences) {
            String sequence;
            if (includeSequences) {
                String nucleotides = fetchNucleotides(seq.getSequenceData(), seqStartIndex, seqLength);
                if (includeIdentifiers) {
                    sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                            seq.getSeqId(),
                            nucleotides);
                } else {
                    sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                            blankSeqId,
                            nucleotides);
                }
            } else {
                if (includeIdentifiers) {
                    sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                            seq.getSeqId(),
                            "") + String.format("%" + residualPart + "s", " ");
                } else {
                    sequence = String.format("%-" + maxSeqIdLength + "s    %s",
                            blankSeqId,
                            "");
                }
            }
            seqBuilder.append(sequence).append(newLine);
        }

        return seqBuilder.toString();
    }

    /**
     * Calculate the maximum length of the identifiers of a given list of sequences.
     * @return the maximum sequence identifier length.
     */
    private int calcMaxSeqIdLength() {
        List<String> seqIds = new ArrayList<String>();
        for (ASequence seq : this.sequences) {
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
    private String fetchNucleotides(List<ANucleotide> rnaNucleotideList, int start, int end) {
        List<ANucleotide> rnaNucleotideSubList = rnaNucleotideList.subList(start, end);
        StringBuilder nucleotideBuilder = new StringBuilder();
        for (ANucleotide rnaNucleotide : rnaNucleotideSubList) {
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
