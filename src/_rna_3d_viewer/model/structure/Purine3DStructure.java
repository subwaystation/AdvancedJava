package _rna_3d_viewer.model.structure;

import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;

/**
 * Created by heumos on 02.02.16.
 */
public class Purine3DStructure  extends Nucleotide3DStructure{

    public Purine3DStructure(MeshView structure, int iD, int residueNumber) {
        super(structure, iD, residueNumber);
    }


    @Override
    protected Color getDefaultColor() {
        return Color.GREEN;
    }
}
