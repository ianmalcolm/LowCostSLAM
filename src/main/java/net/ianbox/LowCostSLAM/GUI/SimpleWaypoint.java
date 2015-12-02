package net.ianbox.LowCostSLAM.GUI;

import java.awt.Color;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class SimpleWaypoint extends DefaultWaypoint {
	private final double size;
	private final Color color;

	/**
	 * @param label
	 *            the text
	 * @param color
	 *            the color
	 * @param coord
	 *            the coordinate
	 */
	public SimpleWaypoint(double s, Color color, GeoPosition coord) {
		super(coord);
		this.size = s;
		this.color = color;
	}

	/**
	 * @return the label text
	 */
	public double getSize() {
		return size;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

}
