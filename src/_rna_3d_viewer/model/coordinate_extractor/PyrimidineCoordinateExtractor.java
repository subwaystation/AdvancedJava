package _rna_3d_viewer.model.coordinate_extractor;

import _rna_3d_viewer.io.PdbAtom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a way to extract pyrimidine coordinates from a given
 * nucleotide.
 *
 * Created by heumos on 05.01.16.
 */
public class PyrimidineCoordinateExtractor {

    public final static Map<String, Integer> PYRIMIDIN_ATOMS = new HashMap<>();
    static {
        PYRIMIDIN_ATOMS.put("N1", 0);
        PYRIMIDIN_ATOMS.put("C2", 3);
        PYRIMIDIN_ATOMS.put("N3", 6);
        PYRIMIDIN_ATOMS.put("C4", 9);
        PYRIMIDIN_ATOMS.put("C5", 12);
        PYRIMIDIN_ATOMS.put("C6", 15);
    }

    public static float[] extractPyrimidineCoordinates(List<PdbAtom> pdbAtomList) {
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

    /**
     * extract the 3D pyrimidine coordinates from the given nucleotides hashmap
     * @param atomNameCoordinatesMap all atom coordinates hashed by their atom name
     * @return float[] the 3D coordinates
     */
    public static float[] extractPyrimidineCoordinates(Map<String, float[]> atomNameCoordinatesMap) {
        float[] coordinates = new float[18];
        for (Map.Entry<String, Integer> entry : PYRIMIDIN_ATOMS.entrySet()) {
            String atomName = entry.getKey();
            float[] atomCoords = atomNameCoordinatesMap.get(atomName);
            System.arraycopy(atomCoords, 0, coordinates, PYRIMIDIN_ATOMS.get(atomName), 3);
        }
        return coordinates;
    }
}
