package _rna_3d_viewer.model.coordinate_extractor;

import _rna_3d_viewer.io.PdbAtom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a way to extract the nucleotide purine coordinates.
 *
 * Created by heumos on 05.01.16.
 */
public class PurineCoordinateExtractor {

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

    public static float[] extractPurineCoordinates(List<PdbAtom> pdbAtomList) {
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

    /**
     *
     * @param atomNameCoordinatesMap all atom coordinates hashed by their atom name
     * @return float[] the 3D coordinates of the purine
     */
    public static float[] extractPurineCoordinates(Map<String, float[]> atomNameCoordinatesMap) {
        float[] coordinates = new float[27];
        for (Map.Entry<String, Integer> entry : PURIN_ATOMS.entrySet()) {
            String atomName = entry.getKey();
            float[] atomCoords = atomNameCoordinatesMap.get(atomName);
            System.arraycopy(atomCoords, 0, coordinates, PURIN_ATOMS.get(atomName), 3);
        }
        return coordinates;
    }

}
