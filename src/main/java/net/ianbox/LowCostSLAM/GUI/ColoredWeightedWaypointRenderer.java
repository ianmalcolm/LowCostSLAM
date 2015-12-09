package net.ianbox.LowCostSLAM.GUI;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.apache.log4j.Logger;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

class ColoredWeightedWaypointRenderer implements
		WaypointRenderer<ColoredWeightedWaypoint> {
	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(ColoredWeightedWaypointRenderer.class.getName());
	private static final double MAXPIXELSIZE = 10.0;

	public void paintWaypoint(Graphics2D g, JXMapViewer viewer,
			ColoredWeightedWaypoint w) {
		g = (Graphics2D) g.create();

		// g.clearRect(0, 0, viewer.getWidth(), viewer.getHeight());

		g.setColor(w.getColor());

		Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(),
				viewer.getZoom());

		double size = w.getWeight() * MAXPIXELSIZE;
		size = size < 1 ? 1.0 : size;
		double x = point.getX() + size / 2;
		double y = point.getY() + size / 2;

		g.drawOval((int) x, (int) y, (int) size, (int) size);
		g.fillOval((int) x, (int) y, (int) size, (int) size);

		g.dispose();
	}
}
