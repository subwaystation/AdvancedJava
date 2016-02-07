package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * Class representing a primary structure (single nucleotide)
 * for drawing purposes.
 *
 * Created by heumos on 07.02.16.
 */
public class PrimaryStruct extends ANucleotideStructure{

    private Text nucleotideText;

    private BooleanProperty isSelected;

    private Color color;

    private int residueIndex;

    /**
     *
     * @param nucleotideText the Text in which the residue type is stored
     * @param residueIndex the residue index
     */
    public PrimaryStruct(Text nucleotideText, int residueIndex) {
        this.nucleotideText = nucleotideText;
        this.isSelected = new SimpleBooleanProperty(false);
        this.residueIndex = residueIndex;
    }

    @Override
    public BooleanProperty isSelectedProperty() {
        return this.isSelected;
    }

    @Override
    public Node getStructure() {
        return this.nucleotideText;
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
        this.nucleotideText.setFill(this.color);
    }

    public Text getNucleotideText() {
        return nucleotideText;
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
