import io.FastaReader;
import seq.Sequence;

import java.util.List;

public class CommandLine {

    public static void main(String[] args) {

        String fastaFile = args[0];
        int seqLineLength = 60;

        try {
            seqLineLength = Integer.valueOf(args[1]);
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
        List<Sequence> sequences = fastaReader.readFasta();

        SequencePrintFormatter sequencePrintFormatter = new SequencePrintFormatter(sequences);
        sequencePrintFormatter.printSequences(seqLineLength);
    }

}