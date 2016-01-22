package model.rna_3d_viewer;

import javafx.geometry.Point3D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 22.01.16.
 *
 * Class representing a residue extracted from several
 * PDB ATOM records. Can be either A,U,C,G.
 * Only RNA residues are supported by now.
 *
 */
public class Residue {

    // the residue type
    // can be A/C/T/G, ADE/GUA/CYT/URA
    private String residueType;

    // the index of the residue
    private int residueIndex;

    // the atom records
    private List<PdbAtom> atoms;

    // the atoms' coordinates hashed by their atom name for faster access
    private Map<String, float[]> atomNameCoordinatesMap;

    public Residue(String residueType, int residueIndex, List<PdbAtom> atoms) {
        this.residueType = residueType;
        this.residueIndex = residueIndex;
        this.atoms = atoms;
        fillAtomNameCoordinatesMap();
    }

    private void fillAtomNameCoordinatesMap() {
        this.atomNameCoordinatesMap = new HashMap<>();
        for (PdbAtom atom : this.atoms) {
            this.atomNameCoordinatesMap.put(atom.getAtomName(), atom.getCoordinates());
        }
    }

    public float[] getAtomCoords(String atomName) {
        return this.atomNameCoordinatesMap.get(atomName);
    }

    public Point3D getAtomPoint3D(String atomName) {
        float[] atomCoords = this.getAtomCoords(atomName);
        return new Point3D(atomCoords[0], atomCoords[1], atomCoords[2]);
    }
}
