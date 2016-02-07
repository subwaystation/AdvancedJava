package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Class representing a nucleotide structure.
 *
 * Created by heumos on 04.02.16.
 */
public abstract class ANucleotideStructure {

    public ANucleotideStructure() {

    }

    /**
     *
     * @return
     */
    public abstract BooleanProperty isSelectedProperty();

    /**
     * get the structure of the nucleotide representation
     * @return
     */
    public abstract Node getStructure();

    /**
     * get the default color of the nucleotide representation
     * @return
     */
    protected abstract Color getDefaultColor();

    protected abstract void setDefaultColor(Color color);

    protected abstract void resetColor();
}
