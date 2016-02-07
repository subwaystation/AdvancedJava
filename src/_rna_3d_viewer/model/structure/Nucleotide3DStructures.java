package _rna_3d_viewer.model.structure;

import javafx.scene.shape.MeshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Class holding all 3D structures.
 *
 * Created by heumos on 02.02.16.
 */
public class Nucleotide3DStructures {

    private List<ANucleotideStructure> nucleotide3DStructures;

    private HashMap<Integer, Nucleotide3DStructure> nucleotideStructureHashMap = new HashMap<>();

    /**
     *
     * @param nucleotide3DStructures the list of nucleotide structures
     */
    public Nucleotide3DStructures(List<ANucleotideStructure> nucleotide3DStructures) {
        this.nucleotide3DStructures = nucleotide3DStructures;
    }

    public Nucleotide3DStructures() {
        this(new ArrayList<>());
    }

    public void addStructure(Nucleotide3DStructure nucleotide3DStructure) {
        this.nucleotide3DStructures.add(nucleotide3DStructure);
    }

    public List<ANucleotideStructure> getNucleotide3DStructures() {
        return this.nucleotide3DStructures;
    }

    public ANucleotideStructure[] getNucleotide3DStructuresAsArray() {
        ANucleotideStructure[] nucleotide3DStructuresAsArray = new Nucleotide3DStructure[this.nucleotide3DStructures.size()];
        for (int i = 0; i < nucleotide3DStructuresAsArray.length; i++) {
            nucleotide3DStructuresAsArray[i] = this.nucleotide3DStructures.get(i);
        }
        return nucleotide3DStructuresAsArray;
    }

    public ANucleotideStructure get(int i) {
        return this.nucleotide3DStructures.get(i);
    }

    public void clear() {
        this.nucleotide3DStructures.clear();
    }

    public List<MeshView> getMeshList() {
        List<MeshView> meshViews = new ArrayList<>();
        for (ANucleotideStructure nucleotide3DStructure : nucleotide3DStructures) {
            meshViews.add((MeshView) nucleotide3DStructure.getStructure());
        }
        return meshViews;
    }

    /**
     *
     * @param residueIndex the residue index
     * @return the nucleotide 3d structure corresponding to the given residue index
     */
    public Nucleotide3DStructure getFast(int residueIndex) {
        if (this.nucleotideStructureHashMap.size() == 0) {
            for (ANucleotideStructure aNucleotideStructure : this.nucleotide3DStructures) {
                Nucleotide3DStructure nucleotide3DStructure = (Nucleotide3DStructure) aNucleotideStructure;
                this.nucleotideStructureHashMap.put(nucleotide3DStructure.getResidueIndex(), nucleotide3DStructure);
            }
        }
        return this.nucleotideStructureHashMap.get(residueIndex);
    }
}
