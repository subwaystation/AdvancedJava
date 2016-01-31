package _rna_3d_viewer.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Created by heumos on 14.12.15.
 */
public class Sugar3DStructureBuilder {

    // the coordinates
    private float[] coordinates;

    // the mesh material
    protected PhongMaterial material = new PhongMaterial(Color.GRAY);

    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    // C2', O4', C1'
                    1,0,4,1,0,2,
                    1,0,0,2,4,1,
                    // C3', O4', C2'
                    2,0,4,1,1,2,
                    2,0,1,2,4,1,
                    // C4', O4', C3'
                    3,0,4,1,2,2,
                    3,0,2,2,4,1
            };


    public Sugar3DStructureBuilder() {

    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public MeshView generateMeshView() {
        MeshView meshView = new MeshView();
        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(this.coordinates);
        triangleMesh.getTexCoords().addAll(this.texCoords);
        triangleMesh.getFaces().addAll(this.faces);
        meshView.setMesh(triangleMesh);
        meshView.setMaterial(this.material);
        return meshView;
    }
}
