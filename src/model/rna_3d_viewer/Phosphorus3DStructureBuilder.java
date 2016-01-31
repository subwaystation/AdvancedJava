package model.rna_3d_viewer;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;


/**
 * Created by heumos on 08.01.16.
 */
public class Phosphorus3DStructureBuilder {

    // the coordinates
    private float[] coordinates;

    // the mesh material
    protected PhongMaterial material = new PhongMaterial(Color.DARKGRAY);

    public Phosphorus3DStructureBuilder() {

    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public Sphere generatePhosphate() {
        Sphere sphere = new Sphere(1.0);
        sphere.setMaterial(this.material);
        sphere.setTranslateX(this.coordinates[0]);
        sphere.setTranslateY(this.coordinates[1]);
        sphere.setTranslateZ(this.coordinates[2]);
        return sphere;
    }
}
