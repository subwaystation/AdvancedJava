package model.rna_3d_viewer;

import java.util.List;

/**
 * Created by heumos on 08.01.16.
 */
public class PhosphorusMoleculeExtractor {

    public static float[] extractPhosphorusCoordinates(List<PdbAtom> pdbAtomList) {
        float[] coordinates = new float[3];
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();

            if (atomName.equals("P")) {
                System.arraycopy(atom.getCoordinates(), 0, coordinates, 0, 3);
            }
        }
        return coordinates;
    }
}
