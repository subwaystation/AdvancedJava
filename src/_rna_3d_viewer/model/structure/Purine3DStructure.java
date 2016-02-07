package _rna_3d_viewer.model.structure;

import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;

/**
 * Purine 3d structure.
 *
 * Created by heumos on 02.02.16.
 */
public class Purine3DStructure  extends Nucleotide3DStructure{

    private Color defaultColor = Color.GREEN;

    /**
     *
     * @param structure structure as MeshView
     * @param iD the id
     * @param residueNumber the residue number
     * @param residueType the residue type
     */
    public Purine3DStructure(MeshView structure, int iD, int residueNumber, String residueType) {
        super(structure, iD, residueNumber, residueType);
    }


    @Override
    public Color getDefaultColor() {
        return defaultColor;
    }

    @Override
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
    }


}
