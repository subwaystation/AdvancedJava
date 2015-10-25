package ui;

import model.AlignmentModel;

/**
 * Created by heumos on 25.10.15.
 *
 * A class representing a processor complementing MVC pattern.
 * This class provides static methods which are used by
 * the controller of MVC pattern.
 */
public class AlignmentVP {

    public static void setAlignmentViewCheckBoxes(AlignmentView alignmentView, boolean selected) {
        alignmentView.getShowIdentifersCB().setSelected(selected);
        alignmentView.getShowSequencesCB().setSelected(selected);
        alignmentView.getShowNumberingCB().setSelected(selected);
    }

    public static void updateAlignmentView(AlignmentModel alignmentModel, AlignmentView alignmentView) {
        boolean identifiersSelected = alignmentView.getShowIdentifersCB().isSelected();
        boolean sequencesSelected = alignmentView.getShowSequencesCB().isSelected();
        boolean numberingSelected = alignmentView.getShowNumberingCB().isSelected();

        alignmentModel.setIdentifierVisibilty(identifiersSelected);
        alignmentModel.setSequenceVisibility(sequencesSelected);
        alignmentModel.setNumberingVisibility(numberingSelected);

        String alignment = alignmentModel.getStringRepresentation();
        alignmentView.getAlignmentT().setText(alignment);
    }
}
