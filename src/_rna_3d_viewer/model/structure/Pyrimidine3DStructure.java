package _rna_3d_viewer.model.structure;

import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;

/**
 * Created by heumos on 02.02.16.
 */
public class Pyrimidine3DStructure extends Nucleotide3DStructure{

    private Color defaultColor = Color.RED;

    public Pyrimidine3DStructure(MeshView structure, int iD, int residueNumber, String residueType) {
        super(structure, iD, residueNumber, residueType);
    }

    @Override
    protected Color getDefaultColor() {
        return this.defaultColor;
    }

    @Override
    protected void setDefaultColor(Color color) {
        this.defaultColor = color;
    }

}
