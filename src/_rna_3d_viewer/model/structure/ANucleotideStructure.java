package _rna_3d_viewer.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

/**
 * Created by heumos on 04.02.16.
 */
public abstract class ANucleotideStructure {

    public ANucleotideStructure() {

    }

    public abstract BooleanProperty isSelectedProperty();

    public abstract Node getStructure();

}
