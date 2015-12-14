package model.rna_3d_viewer;

import java.util.*;

/**
 * Created by heumos on 14.12.15.
 */
public class SugarCoordinateExtractor {

    public static Set<String> SUGAR_ATOMS = new HashSet<>();
    static {
        SUGAR_ATOMS.add("C1'");
        SUGAR_ATOMS.add("C2'");
        SUGAR_ATOMS.add("C3'");
        SUGAR_ATOMS.add("C4'");
        SUGAR_ATOMS.add("O4'");
    }

    public static float[] extractSugarCoordinates(List<PdbAtom> pdbAtomList) {
        HashMap<String, float[]> coordinatesMap = new HashMap<>();
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();
            if (SUGAR_ATOMS.contains(atomName)) {
                coordinatesMap.put(atomName, atom.getCoordinates());
            }
        }
        float[] coordinates = new float[15];
        for (Map.Entry<String, float[]> entry: coordinatesMap.entrySet()) {
            String key = entry.getKey();
            switch(key) {
                case "C1'":
                    System.arraycopy(entry.getValue(), 0, coordinates, 0, 3);
                    break;
                case "C2'":
                    System.arraycopy(entry.getValue(), 0, coordinates, 3, 3);
                    break;
                case "C3'":
                    System.arraycopy(entry.getValue(), 0, coordinates, 6, 3);
                    break;
                case "C4'":
                    System.arraycopy(entry.getValue(), 0, coordinates, 9, 3);
                    break;
                case "O4'":
                    System.arraycopy(entry.getValue(), 0, coordinates, 12, 3);
                    break;
                default:
                    break;
            }
        }
        return coordinates;
    }
}
