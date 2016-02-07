package _rna_3d_viewer.model.structure_builder;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;


/**
 * Class providing method to build a phosphate 3d structure molecule.
 *
 * Created by heumos on 08.01.16.
 */
public class Phosphorus3DStructureBuilder {

    // the coordinates
    private float[] coordinates;

    // the mesh material
    protected PhongMaterial material = new PhongMaterial(Color.DARKGRAY);

    private double radius;

    public Phosphorus3DStructureBuilder(double radius) {
        this.radius = radius;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * generate phosphate molecule with radius, material and color
     * @return
     */
    public Sphere generatePhosphate() {
        Sphere sphere = new Sphere(this.radius);
        sphere.setMaterial(this.material);
        if (this.coordinates == null) {
            return new Sphere();
        }
        sphere.setTranslateX(this.coordinates[0]);
        sphere.setTranslateY(this.coordinates[1]);
        sphere.setTranslateZ(this.coordinates[2]);
        return sphere;
    }
}
