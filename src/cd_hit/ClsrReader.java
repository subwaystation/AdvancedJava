package cd_hit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by heumos on 09.11.15.
 *
 * Java class representing a .clsr reader.
 *
 */
public class ClsrReader {

    // the clsr file to read
    private String clsrFile;

    public ClsrReader(String clsrFile) {
        this.clsrFile = clsrFile;
    }

    public List<ClsrCluster> readClsr() {
        List<ClsrCluster> clsrClusters = new ArrayList<ClsrCluster>();

        List<ClsrEntry> clsrEntryList = new ArrayList<ClsrEntry>();
        List<ClsrEntry> lastClsrEntryList= new ArrayList<ClsrEntry>();
        ClsrEntry mainEntry = null;

        String[] lineSplit;
        String seqId = "";
        String[] interSplit;
        String interSplitS;
        String seqLen = "";
        char lastChar = '*';
        String seqSimilarity;

        BufferedReader bufferedFastaReader = null;
        String line;

        try {
            bufferedFastaReader = new BufferedReader(new FileReader(this.clsrFile));
            do {
                line = bufferedFastaReader.readLine();
                if (line!=null) {
                    line = line.trim();
                    // ignore empty line
                    if (line.isEmpty()) {
                        continue;
                    }
                    char firstChar = line.charAt(0);
                    // we see a new cluster
                    if (firstChar == '>') {
                        addToClusterList(mainEntry, clsrEntryList, clsrClusters);
                        clsrEntryList = new ArrayList<ClsrEntry>();
                        // parse cluster entry line
                    } else {
                        lineSplit = line.split(" ");
                        interSplitS = lineSplit[1].substring(1, lineSplit[1].length() - 4);
                        seqId = interSplitS.split("\\.")[0];
                        interSplit = lineSplit[0].split("\t");
                        interSplitS = interSplit[1];
                        seqLen = interSplitS.substring(0, interSplitS.length() - 3);
                        lastChar = line.charAt(line.length() - 1);
                        // main entry
                        if (lastChar == '*') {
                            mainEntry = new ClsrEntry(Integer.parseInt(seqLen), seqId, 100.0);
                            // not the main entry
                        } else {
                            interSplit  = lineSplit[3].split("/");
                            interSplitS = interSplit[1];
                            seqSimilarity = interSplitS.substring(0, interSplitS.length() - 2);
                            clsrEntryList.add(new ClsrEntry(Integer.parseInt(seqLen), seqId, Double.parseDouble(seqSimilarity)));
                        }
                    }
                } else {
                    // add last cluster to cluster list
                    addToClusterList(mainEntry, clsrEntryList, clsrClusters);
                }
            } while (line != null);
        } catch (FileNotFoundException e) {
            System.out.println("File " + this.clsrFile + " not Found!");
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

        return clsrClusters;
    }

    private List<ClsrEntry> cloneClsrEntryList(List<ClsrEntry> clsrEntryList) {
        List<ClsrEntry> clsrEntryList1 = new ArrayList<ClsrEntry>();
        for (ClsrEntry clsrEntry : clsrEntryList) {
            clsrEntryList1.add(clsrEntry.clone());
        }
        return clsrEntryList1;
    }

    private void addToClusterList(ClsrEntry mainEntry, List<ClsrEntry> clsrEntryList, List<ClsrCluster> clsrClusters) {
        if (mainEntry != null) {
            clsrClusters.add(new ClsrCluster(mainEntry, clsrEntryList));
        }
    }
}
