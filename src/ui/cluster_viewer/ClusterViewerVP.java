package ui.cluster_viewer;

import cd_hit.ClsrCluster;
import cd_hit.ClsrEntry;
import cd_hit.ClsrReader;
import io.FastaIdStrandMap;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import ui.StageManager;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 09.11.15.
 */
public class ClusterViewerVP {

    private static TreeTableView<ClsrEntry> createTreeTableView(List<ClsrCluster> clsrClusters) {
        TreeItem<ClsrEntry> fakeRoot = new TreeItem<>(new ClsrEntry(0, null, 0.0));
        TreeTableView<ClsrEntry> treeTableView = new TreeTableView<ClsrEntry>(fakeRoot);
        treeTableView.setShowRoot(false);

        for (ClsrCluster clsrCluster : clsrClusters) {
            ClsrEntry mainClsrEntry = clsrCluster.getMainEntry();
            TreeItem<ClsrEntry> clsrMainEntryTreeItem = new TreeItem<>(mainClsrEntry);
            List<ClsrEntry> clsrEntryList = clsrCluster.getClsrEntries();
            for (ClsrEntry clsrEntry : clsrEntryList) {
                TreeItem<ClsrEntry> clsrEntryTreeItem = new TreeItem<>(clsrEntry);
                clsrMainEntryTreeItem.getChildren().add(clsrEntryTreeItem);
            }
            fakeRoot.getChildren().add(clsrMainEntryTreeItem);
        }

        // define the columns
        TreeTableColumn<ClsrEntry, String> seqIdColumn = new TreeTableColumn<>("SeqId");
        seqIdColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClsrEntry, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getSeqId()));
        seqIdColumn.setPrefWidth(192);
        TreeTableColumn<ClsrEntry, String> strainColumn = new TreeTableColumn<>("Strain");
        strainColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClsrEntry, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getStrain()));
        strainColumn.setPrefWidth(448);
        TreeTableColumn<ClsrEntry, String> seqLengthColumn = new TreeTableColumn<>("SeqLength");
        seqLengthColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClsrEntry, String> p) ->
                new ReadOnlyStringWrapper(String.valueOf(p.getValue().getValue().getSeqLength())));
        seqLengthColumn.setPrefWidth(192);
        TreeTableColumn<ClsrEntry, String> seqSimilarityColumn = new TreeTableColumn<>("SeqSimilarity");
        seqSimilarityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClsrEntry, String> p) ->
                new ReadOnlyStringWrapper(String.valueOf(p.getValue().getValue().getSeqSimilarity())));
        treeTableView.getColumns().addAll(seqIdColumn, strainColumn, seqLengthColumn, seqSimilarityColumn);
        seqSimilarityColumn.setPrefWidth(192);

        treeTableView.setTableMenuButtonVisible(true);

        return treeTableView;
    }

    public static void buildTableTree(ClusterViewerView clusterViewerView) {
        String dir = System.getProperty("user.dir") + File.separator;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .clsr file.");
        FileChooser.ExtensionFilter clsrExtensionFilter = new FileChooser.ExtensionFilter("CLSR Files", "*.clsr");
        fileChooser.getExtensionFilters().addAll(clsrExtensionFilter);
        File file = fileChooser.showOpenDialog(StageManager.getInstance().getPrimaryStage());
        if (file != null) {
            String[] split = file.toString().split("_");
            StringBuilder fastaBuilder = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                if (i == split.length - 2) {
                    fastaBuilder.append(split[i]);
                } else {
                    fastaBuilder.append(split[i]).append("_");
                }
            }
            String fastaFilePath = fastaBuilder.toString() + ".fasta";
            File fastaFile = new File(fastaFilePath);
            if (fastaFile.exists() && !fastaFile.isDirectory()) {
                FastaIdStrandMap fastaIdStrandMap = new FastaIdStrandMap(fastaFilePath);
                Map<String, String> idStrandMap = fastaIdStrandMap.parseFastaToMap();
                ClsrReader clsrReader = new ClsrReader(file.toString());
                List<ClsrCluster> clsrClusters = clsrReader.readClsr();
                mapIdToStrand(idStrandMap, clsrClusters);
                TreeTableView treeTableView = createTreeTableView(clsrClusters);
                clusterViewerView.getBorderPane().setCenter(treeTableView);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File not Found Error");
                alert.setHeaderText("File not Found Error");
                alert.setContentText("Could not find FASTA file to corresponding CLSR file." +
                        "Given CLSR file: " + file.toString());
                alert.showAndWait();
            }
        }
    }

    private static void mapIdToStrand(Map<String, String> idStrandMap, List<ClsrCluster> clsrClusters) {
        for (ClsrCluster clsrCluster : clsrClusters) {
            String seqId = clsrCluster.getMainEntry().getSeqId();
            clsrCluster.getMainEntry().setStrain(idStrandMap.get(seqId));
            for (ClsrEntry clsrEntry : clsrCluster.getClsrEntries()) {
                String seqId1 = clsrEntry.getSeqId();
                clsrEntry.setStrain(idStrandMap.get(seqId1));
            }
        }
    }
}
