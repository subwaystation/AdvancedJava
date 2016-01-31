package model.rna_3d_viewer;

import javafx.geometry.Point3D;

import java.util.*;

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

    // set of purine atoms
    private final static Set<String> PURINE_SET = new HashSet<>();
    static {
        PURINE_SET.add("A");
        PURINE_SET.add("G");
        PURINE_SET.add("ADE");
        PURINE_SET.add("GUA");
    }

    public Residue(String residueType, int residueIndex, List<PdbAtom> atoms) {
        this.residueType = residueType;
        this.residueIndex = residueIndex;
        this.atoms = atoms;
        this.fillAtomNameCoordinatesMap();
    }

    public Residue(List<PdbAtom> atoms) {
        PdbAtom pdbAtom = atoms.get(0);
        this.residueType = pdbAtom.getResidueType();
        this.residueIndex = pdbAtom.getResidueIndex();
        this.atoms = atoms;
        this.fillAtomNameCoordinatesMap();
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

    /**
     *
     * @return the 3D coordinates of the nucleotide of this residue
     */
    public float[] getNucleotideCoordinates() {
        if (isPurine()) {
            return PurineCoordinateExtractor.extractPyrimidineCoordinates(this.atomNameCoordinatesMap);
        } else {
            return PyrimidineCoordinateExtractor.extractPyrimidineCoordinates(this.atomNameCoordinatesMap);
        }
    }

    public boolean isPurine() {
        return PURINE_SET.contains(this.residueType);
    }

    public boolean isPyrimidine() {
        return !isPurine();
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

    public float[] getSugarCoordinates() {
        return SugarCoordinateExtractor.extractSugarCoordinates(this.atomNameCoordinatesMap);
    }

    public float[] getNucleotideSugarCoords() {
        return CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(this.atomNameCoordinatesMap, isPurine());
    }
}
