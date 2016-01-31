package model.rna_3d_viewer;

import java.util.*;

/**
 * Created by heumos on 06.01.16.
 */
public class CovalentBondCoordinateExtractor {

    public static HashMap<String, Integer> PYRIMIDIN_BOND = new HashMap<>();
    static {
        PYRIMIDIN_BOND.put("N1", 0);
        PYRIMIDIN_BOND.put("C1'", 3);
    }

    public static HashMap<String, Integer> PURIN_BOND = new HashMap<>();
    static {
        PURIN_BOND.put("N9", 0);
        PURIN_BOND.put("C1'", 3);
    }

    public static float[] extractCovalentBondCoordinates(List<PdbAtom> pdbAtomList, boolean isPurine) {
        HashMap<String, float[]> coordinatesMap = new HashMap<>();
        HashMap<String, Integer> bondSet;
        float[] coordinates = new float[6];
        if (isPurine) {
            bondSet = PURIN_BOND;
        } else {
            bondSet = PYRIMIDIN_BOND;
        }
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();
            if (bondSet.containsKey(atomName)) {
                coordinatesMap.put(atomName, atom.getCoordinates());
            }
        }
        for (Map.Entry<String, float[]> entry: coordinatesMap.entrySet()) {
            String key = entry.getKey();
            System.arraycopy(entry.getValue(), 0, coordinates, bondSet.get(key), 3);
        }
        return coordinates;
    }

    public static float[] extractCovalentBondCoordinates(Map<String, float[]> atomNameCoordinatesMap, boolean isPurine) {
        float[] coordinates = new float[6];
        HashMap<String, Integer> bondSet;
        if (isPurine) {
            bondSet = PURIN_BOND;
        } else {
            bondSet = PYRIMIDIN_BOND;
        }
        for (Map.Entry<String, Integer> entry : bondSet.entrySet()) {
            String atomName = entry.getKey();
            float[] atomCoords = atomNameCoordinatesMap.get(atomName);
            System.arraycopy(atomCoords, 0, coordinates, bondSet.get(atomName), 3);
        }
        return coordinates;
    }
}
