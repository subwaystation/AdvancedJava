package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Class representing a nucleotide in the 2d view.
 *
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

    // default color
    private Color defaultColor = Color.BLACK;

    /**
     *
     * @param residueIndex the residue index
     * @param residueType the residue type
     * @param circle the circle 2d structure
     */
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

    @Override
    public Shape getStructure() {
        return getCircle();
    }

    @Override
    public Color getDefaultColor() {
        return this.defaultColor;
    }

    @Override
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
    }

    @Override
    public void resetColor() {
        this.circle.setFill(this.defaultColor);
    }
}
