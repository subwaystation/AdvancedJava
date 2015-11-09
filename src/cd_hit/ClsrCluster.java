package cd_hit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 09.11.15.
 *
 * Java class representing a cluster of a .clsr file originating from CD-HIT.
 *
 */
public class ClsrCluster {

    // the main entry of a cluster corresponding to "*"
    private ClsrEntry mainEntry;

    // the entries of the cluster
    private List<ClsrEntry> clsrEntries;

    public ClsrCluster(ClsrEntry mainEntry, List<ClsrEntry> clsrEntries) {
        this.mainEntry = mainEntry;
        this.clsrEntries = clsrEntries;
    }

    public ClsrEntry getMainEntry() {
        return mainEntry;
    }

    public void setMainEntry(ClsrEntry mainEntry) {
        this.mainEntry = mainEntry;
    }

    public List<ClsrEntry> getClsrEntries() {
        return clsrEntries;
    }

    public void setClsrEntries(List<ClsrEntry> clsrEntries) {
        this.clsrEntries = clsrEntries;
    }

    @Override
    public String toString() {
        return this.mainEntry.toString() + "\t" + this.clsrEntries.toString();
    }

    @Override
    public ClsrCluster clone() {
        List<ClsrEntry> clsrEntryList = new ArrayList<ClsrEntry>();
        for (ClsrEntry clsrEntry : this.getClsrEntries()) {
            clsrEntryList.add(clsrEntry.clone());
        }
        return new ClsrCluster(this.getMainEntry().clone(), clsrEntryList);
    }
}
