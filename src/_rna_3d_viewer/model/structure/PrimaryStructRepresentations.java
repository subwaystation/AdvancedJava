package _rna_3d_viewer.model.structure;

import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.List;

/**
 * Created by heumos on 07.02.16.
 */
public class PrimaryStructRepresentations {

    private List<PrimaryStruct> primaryStructList;

    private HashMap<Integer, PrimaryStruct> primaryStructHashMap;

    private TextFlow textFlow;

    public PrimaryStructRepresentations(List<PrimaryStruct> primaryStructList, TextFlow textFlow) {
        this.primaryStructList = primaryStructList;
        this.textFlow = textFlow;
    }

    public void createPrimaryStructHashMap() {
        this.primaryStructHashMap = new HashMap<>();
        for (PrimaryStruct primaryStruct : this.primaryStructList) {
            int residueIndex = primaryStruct.getResidueIndex();
            this.primaryStructHashMap.put(residueIndex, primaryStruct);
        }
    }

    public List<PrimaryStruct> getPrimaryStructList() {
        return primaryStructList;
    }

    public void setPrimaryStructList(List<PrimaryStruct> primaryStructList) {
        this.primaryStructList = primaryStructList;
    }

    public HashMap<Integer, PrimaryStruct> getPrimaryStructHashMap() {
        return primaryStructHashMap;
    }

    public void setPrimaryStructHashMap(HashMap<Integer, PrimaryStruct> primaryStructHashMap) {
        this.primaryStructHashMap = primaryStructHashMap;
    }

    public TextFlow getTextFlow() {
        return textFlow;
    }

    public PrimaryStruct[] getPrimarySructAsArray() {
        PrimaryStruct[] primaryStructs = new PrimaryStruct[this.primaryStructList.size()];
        for (int i = 0; i < primaryStructs.length; i++) {
            primaryStructs[i] = this.primaryStructList.get(i);
        }
        return primaryStructs;
    }

    public PrimaryStruct getStruct(int i) {
        return this.primaryStructHashMap.get(i);
    }
}
