package model.rna_3d_viewer;

import javafx.scene.shape.Shape3D;

/**
 * Created by heumos on 31.01.16.
 */
public class Residue3DStructureBuilder {

    private Residue residue;

    private Purine3DStructureBuilder purine3DStructureBuilder;

    private Pyrimidine3DStructureBuilder pyrimidine3DStructureBuilder;

    private Sugar3DStructureBuilder sugar3DStructureBuilder;

    private Molecule3DConnectionBuilder molecule3DConnectionBuilder;

    public Residue3DStructureBuilder() {
        this.purine3DStructureBuilder = new Purine3DStructureBuilder();
        this.pyrimidine3DStructureBuilder = new Pyrimidine3DStructureBuilder();
        this.sugar3DStructureBuilder = new Sugar3DStructureBuilder();
        this.molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.01);
    }

    public void setResidue(Residue residue) {
        this.residue = residue;
    }

    public Shape3D getNucleotide3DStructure() {
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

    public Shape3D getSugar3DStructure() {
        this.sugar3DStructureBuilder.setCoordinates(this.residue.getSugarCoordinates());
        return this.sugar3DStructureBuilder.generateMeshView();
    }

    public Shape3D getNucleotideSugarConnection3DStructure() {
        this.molecule3DConnectionBuilder.setPoints(this.residue.getNucleotideSugarCoords());
        return this.molecule3DConnectionBuilder.createConnection();
    }
}
