package model.rna_3d_viewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 05.01.16.
 */
public class PurinCoordinateExtractor {

    public static Map<String, Integer> PURIN_ATOMS = new HashMap<>();
    static {
        PURIN_ATOMS.put("N1", 0);
        PURIN_ATOMS.put("C2", 3);
        PURIN_ATOMS.put("N3", 6);
        PURIN_ATOMS.put("C4", 9);
        PURIN_ATOMS.put("C5", 12);
        PURIN_ATOMS.put("C6", 15);
        PURIN_ATOMS.put("N7", 18);
        PURIN_ATOMS.put("C8", 21);
        PURIN_ATOMS.put("N9", 24);
    }

    public static float[] extractPurinCoordinates(List<PdbAtom> pdbAtomList) {
        HashMap<String, float[]> coordinatesMap = new HashMap<>();
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();
            if (PURIN_ATOMS.containsKey(atomName)) {
                coordinatesMap.put(atomName, atom.getCoordinates());
            }
        }
        float[] coordinates = new float[27];
        for (Map.Entry<String, float[]> entry: coordinatesMap.entrySet()) {
            String key = entry.getKey();
            System.arraycopy(entry.getValue(), 0, coordinates, PURIN_ATOMS.get(key), 3);
        }
        return coordinates;
    }
}
