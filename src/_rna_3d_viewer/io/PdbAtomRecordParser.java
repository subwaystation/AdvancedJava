package _rna_3d_viewer.io;

/**
 *
 * Class representing a PDB atom record parser.
 * Provides a method to parse an PDB atom from a given String.
 *
 * Created by heumos on 14.12.15.
 */
public class PdbAtomRecordParser {

    private String atomLine;

    public PdbAtomRecordParser(String atomLine) {
        this.atomLine = atomLine;
    }

    public String getAtomLine() {
        return atomLine;
    }

    public void setAtomLine(String atomLine) {
        this.atomLine = atomLine;
    }

    /**
     * Parses the local string into an PDB atom object.
     * @return PdbAtom the PDB atom
     */
    public PdbAtom parseAtomRecord() {
        // String recordName = this.atomLine.substring(0,6);
        // Integer serial = Integer.valueOf(this.atomLine.substring(6, 11));
        String atomName = this.atomLine.substring(12, 16).trim();
        String residueType = this.atomLine.substring(17, 20).trim();
        int residueIndex = Integer.valueOf(this.atomLine.substring(22, 26).trim());
        float x = Float.valueOf(this.atomLine.substring(30, 38).trim());
        float y = Float.valueOf(this.atomLine.substring(38,46).trim());
        float z = Float.valueOf(this.atomLine.substring(46, 54).trim());
        float[] coordinates = {x,y,z};
        String atomType = this.atomLine.substring(76,78).trim();
        return new PdbAtom(atomName, residueType, residueIndex, coordinates, atomType);
    }
}
