package model.rna_3d_viewer;

import javafx.scene.paint.Color;

/**
 * Created by heumos on 05.01.16.
 */
public class Pyrimidine3DStructureBuilder extends Nucleotide3DStructureBuilder {

    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    // N1, C2, C4
                    0,0,1,1,3,2,
                    0,0,3,2,1,1,
                    // N1, C4, C5
                    0,0,3,1,4,2,
                    0,0,4,2,3,1,
                    // C2, N3, C4
                    1,0,2,1,3,2,
                    1,0,3,2,2,1,
                    // N1, C5, C6
                    0,0,4,1,5,1,
                    0,0,5,1,4,1
            };

    public Pyrimidine3DStructureBuilder(Color color) {
        super(color);
    }

    public Pyrimidine3DStructureBuilder() {
        this(Color.RED);
    }

    public int[] getFaces() {
        return this.faces;
    }

    public float[] getTexCoords() {
        return this.texCoords;
    }
}
