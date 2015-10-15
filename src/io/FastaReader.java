package io;

import seq.Nucleotide;
import seq.Sequence;

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

    public List<Sequence> readFasta() {

        BufferedReader bufferedFastaReader = null;
        String line;
        String seqId = "";
        StringBuffer sequenceBuffer = new StringBuffer();
        List<Sequence> sequenceList = new ArrayList<Sequence>();

        try {
            bufferedFastaReader = new BufferedReader(new FileReader(this.fastaFile));
            do {
                line = bufferedFastaReader.readLine();
                if (line!=null) {
                    line = line.trim();
                    // ignore empty line
                    if (line.isEmpty()) {
                        continue;
                    }
                    char firstChar = line.charAt(0);
                    if (firstChar == '>') {

                        // save the previous sequence read
                        addToSequenceList(seqId, sequenceBuffer, sequenceList);

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
                    addToSequenceList(seqId, sequenceBuffer, sequenceList);
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

    private void addToSequenceList(String seqId, StringBuffer sequenceBuffer, List<Sequence> sequenceList) {
        if (sequenceBuffer.length() != 0) {
            ArrayList<Nucleotide> sequenceData = new ArrayList<Nucleotide>();
            for (int i = 0; i < sequenceBuffer.length(); i++) {
                char base = sequenceBuffer.charAt(i);
                sequenceData.add(new Nucleotide(base));
            }
            Sequence sequence = new Sequence(seqId, sequenceData);
            sequenceList.add(sequence);
        }
    }
}
