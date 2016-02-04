package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Created by heumos on 04.02.16.
 */
public class SecStruct2DCircle extends ANucleotideStructure {

    // the corresponding residue index
    private int residueIndex;

    // the corresponding residue type
    private String residueType;

    // the 2D representation
    private Circle circle;

    // is this structure selected?
    private BooleanProperty isSelected;

    private Color defaultColor;

    public SecStruct2DCircle(int residueIndex, String residueType, Circle circle) {
        this.residueIndex = residueIndex;
        this.residueType = residueType;
        this.circle = circle;
        this.isSelected = new SimpleBooleanProperty(false);
    }

    public int getResidueIndex() {
        return residueIndex;
    }

    public void setResidueIndex(int residueIndex) {
        this.residueIndex = residueIndex;
    }

    public String getResidueType() {
        return residueType;
    }

    public void setResidueType(String residueType) {
        this.residueType = residueType;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public boolean getIsSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }


    @Override
    public Shape getStructure() {
        return getCircle();
    }
}
