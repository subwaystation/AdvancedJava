package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * Created by heumos on 04.02.16.
 */
public class SecStruct2DLine extends ANucleotideStructure {

    // the 2D representation is a line
    private Line line;

    // the boolean property if this structure is selected
    private BooleanProperty isSelected;

    // the first residue number of the corresponding nucleotide
    private int residueIndex1;

    // the second residue number of the nucleotide that gets connected by this line
    private int residueIndex2;

    private Color defaultColor = Color.RED;

    public SecStruct2DLine(Line line, int residueIndex1, int residueIndex2) {
        this.line = line;
        this.residueIndex1 = residueIndex1;
        this.residueIndex2 = residueIndex2;
        this.isSelected = new SimpleBooleanProperty(false);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    @Override
    public void resetColor() {
        this.line.setFill(this.defaultColor);
        this.line.setStroke(this.defaultColor);
    }

    public Line getLine() {
        return line;
    }

    public boolean getIsSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public int getResidueIndex1() {
        return residueIndex1;
    }

    public int getResidueIndex2() {
        return residueIndex2;
    }

    @Override
    public Shape getStructure() {
        return getLine();
    }
}
