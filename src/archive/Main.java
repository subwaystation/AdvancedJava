package archive;

import io.PdbFileParser;
import _rna_3d_viewer.model.PdbAtom;

import java.io.IOException;
import java.util.List;

/**
 * Created by heumos on 09.11.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String pdbFile = args[0];
        PdbFileParser pdbFileParser = new PdbFileParser(pdbFile);
        List<PdbAtom> atoms = pdbFileParser.parsePdb();
        for (PdbAtom atom : atoms) {
            System.out.println(atom);
        }
    }
}
