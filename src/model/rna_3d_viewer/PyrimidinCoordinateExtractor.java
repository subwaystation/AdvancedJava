package model.rna_3d_viewer;

import javafx.geometry.Point3D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 05.01.16.
 */
public class PyrimidinCoordinateExtractor {

    public final static Map<String, Integer> PYRIMIDIN_ATOMS = new HashMap<>();
    static {
        PYRIMIDIN_ATOMS.put("N1", 0);
        PYRIMIDIN_ATOMS.put("C2", 3);
        PYRIMIDIN_ATOMS.put("N3", 6);
        PYRIMIDIN_ATOMS.put("C4", 9);
        PYRIMIDIN_ATOMS.put("C5", 12);
        PYRIMIDIN_ATOMS.put("C6", 15);
    }

    public static float[] extractPyrimidinCoordinates(List<PdbAtom> pdbAtomList) {
        HashMap<String, float[]> coordinatesMap = new HashMap<>();
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();
            if (PYRIMIDIN_ATOMS.containsKey(atomName)) {
                coordinatesMap.put(atomName, atom.getCoordinates());
            }
        }
        float[] coordinates = new float[18];
        for (Map.Entry<String, float[]> entry: coordinatesMap.entrySet()) {
            String key = entry.getKey();
            System.arraycopy(entry.getValue(), 0, coordinates, PYRIMIDIN_ATOMS.get(key), 3);
        }
        return coordinates;
    }
}
