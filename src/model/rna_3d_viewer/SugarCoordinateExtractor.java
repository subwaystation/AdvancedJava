package model.rna_3d_viewer;

import java.util.*;

/**
 * Created by heumos on 14.12.15.
 */
public class SugarCoordinateExtractor {

    public static Map<String, Integer> SUGAR_ATOMS = new HashMap<>();
    static {
        SUGAR_ATOMS.put("C1'", 0);
        SUGAR_ATOMS.put("C2'", 3);
        SUGAR_ATOMS.put("C3'", 6);
        SUGAR_ATOMS.put("C4'", 9);
        SUGAR_ATOMS.put("O4'", 12);
    }

    public static float[] extractSugarCoordinates(List<PdbAtom> pdbAtomList) {
        HashMap<String, float[]> coordinatesMap = new HashMap<>();
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();
            if (SUGAR_ATOMS.containsKey(atomName)) {
                coordinatesMap.put(atomName, atom.getCoordinates());
            }
        }
        float[] coordinates = new float[15];
        for (Map.Entry<String, float[]> entry: coordinatesMap.entrySet()) {
            String key = entry.getKey();
            System.arraycopy(entry.getValue(), 0, coordinates, SUGAR_ATOMS.get(key), 3);
        }
        return coordinates;
    }

    public static float[] extractSugarCoordinates(Map<String, float[]> atomNameCoordinatesMap) {
        float[] coordinates = new float[15];
        for (Map.Entry<String, Integer> entry : SUGAR_ATOMS.entrySet()) {
            String atomName = entry.getKey();
            float[] atomCoords = atomNameCoordinatesMap.get(atomName);
            System.arraycopy(atomCoords, 0, coordinates, SUGAR_ATOMS.get(atomName), 3);
        }
        return coordinates;
    }
}
