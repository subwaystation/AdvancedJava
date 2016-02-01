package _rna_3d_viewer.io;

import _rna_3d_viewer.model.PdbAtom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 14.12.15.
 */
public class PdbFileParser {

    private String pdbFile;

    public PdbFileParser(String pdbFile) {
        this.pdbFile = pdbFile;
    }

    public List<PdbAtom> parsePdb() throws IOException {
        BufferedReader pdbReader = new BufferedReader(new FileReader(this.pdbFile));
        String line = "";
        PdbAtomRecordParser pdbAtomRecordParser = new PdbAtomRecordParser(line);
        List<PdbAtom> pdbAtoms = new ArrayList<>();
        while((line = pdbReader.readLine()) != null) {
            if (line.startsWith("ATOM") && !line.isEmpty()) {
                pdbAtomRecordParser.setAtomLine(line);
                pdbAtoms.add(pdbAtomRecordParser.parseAtomRecord());
            }
        }
        pdbReader.close();
        return pdbAtoms;
    }
}
