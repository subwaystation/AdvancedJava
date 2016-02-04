package _rna_3d_viewer.model.structure;

import javafx.scene.shape.MeshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 02.02.16.
 */
public class Nucleotide3DStructures {

    private List<ANucleotideStructure> nucleotide3DStructures;

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
}
