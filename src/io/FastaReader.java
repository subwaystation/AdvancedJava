package io;

import seq.nucleotide.ANucleotide;
import seq.sequence.ASequence;
import seq.sequence.RnaNucleotide;
import seq.nucleotide.RnaSequence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class for reading FASTA files.
 *
 */
public class FastaReader {

    private String fastaFile;

    public FastaReader(String fastaFile) {
        this.fastaFile = fastaFile;
    }

    public String getFastaFile() {
        return fastaFile;
    }

    public void setFastaFile(String fastaFile) {
        this.fastaFile = fastaFile;
    }

    public List<ASequence> readFasta() {

        BufferedReader bufferedFastaReader = null;
        String line;
        String seqId = "";
        StringBuffer sequenceBuffer = new StringBuffer();
        List<ASequence> sequenceList = new ArrayList<ASequence>();
        int totalLines = 0;

        try {
            bufferedFastaReader = new BufferedReader(new FileReader(this.fastaFile));
            do {
                line = bufferedFastaReader.readLine();
                totalLines++;
                if (line!=null) {
                    line = line.trim();
                    // ignore empty line
                    if (line.isEmpty()) {
                        continue;
                    }
                    char firstChar = line.charAt(0);
                    if (firstChar == '>') {

                        // save the previous sequence read
                        addToSequenceList(seqId, sequenceBuffer, sequenceList, totalLines);

                        // now fetch the new seqId
                        seqId = line.substring(1).trim();

                        // start a new sequenceBuffer buffer
                        sequenceBuffer = new StringBuffer();

                    } else if (firstChar == ';') {
                        // comment line, skip it
                        continue;
                    } else {
                        sequenceBuffer.append(line.trim());
                    }
                } else {
                    addToSequenceList(seqId, sequenceBuffer, sequenceList, totalLines);
                }
            } while (line != null);
        } catch (FileNotFoundException e) {
            System.out.println("File " + fastaFile + " not Found!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("An IO error has occured: " + e.getMessage());
            System.exit(1);
        } finally {
            if (bufferedFastaReader != null) {
                try {
                    bufferedFastaReader.close();
                } catch (IOException e) {
                    // do nothing here
                }
            }
        }
        return sequenceList;
    }

    private void addToSequenceList(String seqId, StringBuffer sequenceBuffer,
                                   List<ASequence> sequenceList, int totalLines) {
        if (sequenceBuffer.length() != 0) {
            ArrayList<ANucleotide> sequenceData = new ArrayList<ANucleotide>();
            for (int i = 0; i < sequenceBuffer.length(); i++) {
                char base = sequenceBuffer.charAt(i);
                if (base == 'T') {
                    System.err.println("Illegal character in RNA FASTA file " + this.fastaFile);
                    System.err.println("Character found: " + String.valueOf(base));
                    System.err.println("Error occured in line: " + totalLines);
                    System.exit(1);
                }
                sequenceData.add(new RnaNucleotide(base));
            }
            RnaSequence sequence = new RnaSequence(seqId, sequenceData);
            sequenceList.add(sequence);
        }
    }
}
