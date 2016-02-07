package _rna_3d_viewer.model.structure_builder;

import javafx.scene.paint.Color;

/**
 * Providing important fields to generate purine 3d molecule structure.
 *
 * Created by heumos on 05.01.16.
 */
public class Purine3DStructureBuilder extends Nucleotide3DStructureBuilder {

    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    // C4, C8, N9
                    3,0,7,1,8,2,
                    3,0,8,2,7,1,
                    // C4, C5, C8
                    3,0,4,1,7,2,
                    3,0,7,2,4,1,
                    // C5, N7, C8
                    4,0,6,1,7,2,
                    4,0,7,2,6,1,

                    // N1, C2, C5
                    0,0,1,1,4,2,
                    0,0,4,2,1,1,
                    // N1, C5, C6
                    0,0,4,1,5,2,
                    0,0,5,2,4,1,
                    // C2, N3, C4
                    1,0,2,1,3,2,
                    1,0,3,2,2,1,
                    // C2, C4, C5
                    1,0,3,1,4,2,
                    1,0,4,2,3,1
            };


    public Purine3DStructureBuilder(Color color) {
        super(color);
    }

    public Purine3DStructureBuilder() {
        this(Color.GREEN);
    }

    public int[] getFaces() {
        return this.faces;
    }

    public float[] getTexCoords() {
        return this.texCoords;
    }
}
