package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

/**
 * Created by heumos on 02.02.16.
 */
public abstract class Nucleotide3DStructure {

    // the id of the structure
    private int iD;

    // the residue number of the structure
    private int residueNumber;

    // the 3D representation of the nucleotide
    private MeshView structure;

    // a boolean property stating, if the structure is selected or not in the view
    private SimpleBooleanProperty isSelected;

    public Nucleotide3DStructure(MeshView structure, int iD, int residueNumber) {
        this.iD = iD;
        this.residueNumber = residueNumber;
        this.structure = structure;
        this.isSelected = new SimpleBooleanProperty(false);
    }

    protected abstract Color getDefaultColor();

    public void resetColor() {
        this.structure.setMaterial(new PhongMaterial(getDefaultColor()));
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getResidueNumber() {
        return residueNumber;
    }

    public void setResidueNumber(int residueNumber) {
        this.residueNumber = residueNumber;
    }

    public MeshView getStructure() {
        return structure;
    }

    public void setStructure(MeshView structure) {
        this.structure = structure;
    }

    public boolean getIsSelected() {
        return isSelected.get();
    }

    public SimpleBooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
}