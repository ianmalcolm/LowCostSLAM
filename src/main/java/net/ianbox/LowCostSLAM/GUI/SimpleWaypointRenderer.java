package net.ianbox.LowCostSLAM.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.apache.log4j.Logger;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

class SimpleWaypointRenderer implements WaypointRenderer<SimpleWaypoint> {
	private static final Logger log = Logger
			.getLogger(SimpleWaypointRenderer.class.getName());
	private static final double MAXPIXELSIZE = 10.0;

	public void paintWaypoint(Graphics2D g, JXMapViewer viewer, SimpleWaypoint w) {
		g = (Graphics2D) g.create();

		// g.clearRect(0, 0, viewer.getWidth(), viewer.getHeight());

		g.setColor(w.getColor());

		Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(),
				viewer.getZoom());

		double size = w.getSize() * MAXPIXELSIZE;
		double x = point.getX() + size / 2;
		double y = point.getY() + size / 2;

		g.drawOval((int) x, (int) y, (int) size, (int) size);
		g.fillOval((int) x, (int) y, (int) size, (int) size);

		g.dispose();
	}
}
