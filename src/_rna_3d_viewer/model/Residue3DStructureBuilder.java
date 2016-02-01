package _rna_3d_viewer.model;

import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;

/**
 * Created by heumos on 31.01.16.
 */
public class Residue3DStructureBuilder {

    private Residue residue;

    private Purine3DStructureBuilder purine3DStructureBuilder;

    private Pyrimidine3DStructureBuilder pyrimidine3DStructureBuilder;

    private Sugar3DStructureBuilder sugar3DStructureBuilder;

    private Molecule3DConnectionBuilder molecule3DConnectionBuilder;

    private Phosphorus3DStructureBuilder phosphorus3DStructureBuilder;

    public Residue3DStructureBuilder() {
        this.purine3DStructureBuilder = new Purine3DStructureBuilder();
        this.pyrimidine3DStructureBuilder = new Pyrimidine3DStructureBuilder();
        this.sugar3DStructureBuilder = new Sugar3DStructureBuilder();
        this.molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.05);
        this.phosphorus3DStructureBuilder = new Phosphorus3DStructureBuilder(1.1);
    }

    public void setResidue(Residue residue) {
        this.residue = residue;
    }

    public MeshView buildNucleotide3DStructure() {
        if (this.residue.isPurine()) {
            this.purine3DStructureBuilder.setCoordinates(this.residue.getNucleotideCoordinates());
            return this.purine3DStructureBuilder.
                    generateMeshView(this.residue.getResidueType(), this.residue.getResidueIndex());
        } else {
            this.pyrimidine3DStructureBuilder.setCoordinates(this.residue.getNucleotideCoordinates());
            return this.pyrimidine3DStructureBuilder.
                    generateMeshView(this.residue.getResidueType(), this.residue.getResidueIndex());
        }
    }

    public MeshView buildSugar3DStructure() {
        this.sugar3DStructureBuilder.setCoordinates(this.residue.getSugarCoordinates());
        return this.sugar3DStructureBuilder.generateMeshView();
    }

    public Cylinder buildNucleotideSugarConnection3DStructure() {
        this.molecule3DConnectionBuilder.setPoints(this.residue.getNucleotideSugarCoords());
        return this.molecule3DConnectionBuilder.createConnection();
    }

    public Sphere buildPhosphorus3DStructure() {
        this.phosphorus3DStructureBuilder.setCoordinates(this.residue.getPhosphorusCoords());
        return this.phosphorus3DStructureBuilder.generatePhosphate();
    }

    public Cylinder buildSugarPhosphorusConnection3DStructure() {
        float[] phosphorusCoordinates = this.residue.getPhosphorusCoords();
        if (this.residue.getPhosphorusCoords() == null ||
                (phosphorusCoordinates[0] == 0.0 && phosphorusCoordinates[1] == 0.0 && phosphorusCoordinates[2] == 0.0)) {
            return null;
        } else {
            this.molecule3DConnectionBuilder.setEndPoint(this.residue.getPhosphorusCoords());
            this.molecule3DConnectionBuilder.setInitPoint(this.residue.getAtomCoords("C4'"));
            return this.molecule3DConnectionBuilder.createConnection();
        }
    }

    public float[] getPhosphorusCoords() {
        return this.residue.getPhosphorusCoords();
    }
}
