package lib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by heumos on 09.11.15.
 */
public class FastaIdStrandMap {

    private String fastaFile;

    public FastaIdStrandMap(String fastaFile) {
        this.fastaFile = fastaFile;
    }

    public Map<String, String> parseFastaToMap() {
        BufferedReader bufferedFastaReader = null;
        String line;
        String seqId;
        String strand;
        HashMap<String, String> idStrandMap = new HashMap<String, String>();

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
                        String header = line.substring(1);
                        String[] headerSplit = header.split(";");
                        seqId = headerSplit[0].split(" ")[0].split("\\.")[0];
                        strand = headerSplit[headerSplit.length - 1];
                        idStrandMap.put(seqId, strand);

                    } else if (firstChar == ';') {
                        // comment line, skip it
                        continue;
                    } else {
                    }
                } else {
                    // nothing to do here
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
        return idStrandMap;
    }
}
