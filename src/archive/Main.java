package archive;

import cd_hit.ClsrCluster;
import cd_hit.ClsrReader;
import lib.FastaIdStrandMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 09.11.15.
 */
public class Main {

    public static void main(String[] args) {
        String clsrFile = args[0];
        ClsrReader clsrReader = new ClsrReader(clsrFile);
        List<ClsrCluster> clsrClusterList = clsrReader.readClsr();
        for (ClsrCluster clsrCluster : clsrClusterList) {
            System.out.println(clsrCluster);
        }
        String[] split = clsrFile.split("_");
        StringBuilder fastaBuilder = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            if (i == split.length - 2) {
                fastaBuilder.append(split[i]);
            } else {
                fastaBuilder.append(split[i]).append("_");
            }
        }
        String fastaFile = fastaBuilder.toString() + ".fasta";
        FastaIdStrandMap fastaIdStrandMap = new FastaIdStrandMap(fastaFile);
        Map<String, String> idStrandMap = fastaIdStrandMap.parseFastaToMap();
        System.out.println(idStrandMap);
    }
}
