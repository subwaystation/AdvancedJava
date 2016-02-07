package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by heumos on 07.02.16.
 */
public class PrimaryStruct extends ANucleotideStructure{

    private Text nucleotideLabel;

    private BooleanProperty isSelected;

    private Color color;

    private int residueIndex;

    public PrimaryStruct(Text nucleotideLabel, int residueIndex) {
        this.nucleotideLabel = nucleotideLabel;
        this.isSelected = new SimpleBooleanProperty(false);
        this.residueIndex = residueIndex;
    }

    @Override
    public BooleanProperty isSelectedProperty() {
        return this.isSelected;
    }

    @Override
    public Node getStructure() {
        return this.nucleotideLabel;
    }

    @Override
    public Color getDefaultColor() {
        return this.color;
    }

    @Override
    public void setDefaultColor(Color color) {
        this.color = color;
    }

    @Override
    public void resetColor() {
        this.nucleotideLabel.setFill(this.color);
    }

    public Text getNucleotideLabel() {
        return nucleotideLabel;
    }

    public boolean getIsSelected() {
        return isSelected.get();
    }

    public Color getColor() {
        return color;
    }

    public int getResidueIndex() {
        return residueIndex;
    }
}
