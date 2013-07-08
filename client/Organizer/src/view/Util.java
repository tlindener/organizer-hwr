package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Util {
	/**
	 * Creates the gridBagContraints with the submitted values.
	 * 
	 * @param gWidth
	 * @param gHeight
	 * @param gridx
	 * @param gridy
	 * @param anchor
	 * @param fill
	 * @return
	 */
	public static GridBagConstraints createGridBagContraints(int gWidth, int gHeight,
			int gridx, int gridy, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = anchor;
		gbc.gridwidth = gWidth;
		gbc.gridheight = gHeight;
		gbc.fill = fill;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}
	public static GridBagConstraints createGridBagContraints(
			int gridx, int gridy, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = anchor;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = fill;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}
	
	public static GridBagConstraints createGridBagContraints(int gWidth, int gHeight,
			int gridx, int gridy,  int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = gWidth;
		gbc.gridheight = gHeight;
		gbc.fill = fill;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}
	
}
