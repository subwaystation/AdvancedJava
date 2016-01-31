package _rna_3d_viewer.model;

import java.util.Arrays;

/**
 * Created by heumos on 14.12.15.
 *
 * Class representing a PDB ATOM record entry.
 * Note, that not the full record is represented here.
 *
 */
public class PdbAtom {

    // the atom name
    private String atomName;

    // the residue type
    // can be A/C/T/G, ADE/GUA/CYT/URA is not allowed yet
    private String residueType;

    // the index of the residue
    private int residueIndex;

    // the coordinates
    private float[] coordinates;

    // the atom type
    private String atomType;

    public PdbAtom(String atomName,String residueType, int residueIndex, float[] coordinates, String atomType) {
        this.atomName = atomName;
        this.residueType = residueType;
        this.residueIndex = residueIndex;
        this.coordinates = coordinates;
        this.atomType = atomType;
    }

    public String getResidueType() {
        return residueType;
    }

    public void setResidueType(String residueType) {
        this.residueType = residueType;
    }

    public int getResidueIndex() {
        return residueIndex;
    }

    public void setResidueIndex(int residueIndex) {
        this.residueIndex = residueIndex;
    }

    public String getAtomType() {
        return atomType;
    }

    public void setAtomType(String atomType) {
        this.atomType = atomType;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getAtomName() {
        return atomName;
    }

    @Override
    public String toString() {
        String tab = "\t";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.atomName).append(tab);
        stringBuilder.append(this.residueType).append(tab);
        stringBuilder.append(this.residueIndex).append(tab);
        stringBuilder.append(Arrays.toString(this.coordinates)).append(tab);
        stringBuilder.append(this.atomType);
        return stringBuilder.toString();
    }
}
