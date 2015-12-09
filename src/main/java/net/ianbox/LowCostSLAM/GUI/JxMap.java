package net.ianbox.LowCostSLAM.GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JToolTip;
import javax.swing.SwingWorker;

import net.ianbox.LowCostSLAM.SLAM.Localizer;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.apache.log4j.Logger;

/**
 * A simple sample application that uses JXMapKit
 * 
 * @author Martin Steiger
 */
public class JxMap extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4741213149608303867L;
	private final JXMapKit jXMapKit = new JXMapKit();
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(JxMap.class.getName());

	private WaypointPainter<ColoredWeightedWaypoint> waypointPainter = null;
	private final Localizer localizer;

	public JxMap(Localizer _localizer) {

		localizer = _localizer;

		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		jXMapKit.setTileFactory(tileFactory);

		final JToolTip tooltip = new JToolTip();
		tooltip.setTipText("Java");
		tooltip.setComponent(jXMapKit.getMainMap());
		jXMapKit.getMainMap().add(tooltip);

		jXMapKit.setZoom(1);

		// location of Java
		final GeoPosition gp = new GeoPosition(1.299643, 103.787948);
		jXMapKit.setAddressLocation(gp);

		jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				// ignore
			}

			public void mouseMoved(MouseEvent e) {
				JXMapViewer map = jXMapKit.getMainMap();

				// convert to world bitmap
				Point2D worldPos = map.getTileFactory().geoToPixel(gp,
						map.getZoom());

				// convert to screen
				Rectangle rect = map.getViewportBounds();
				int sx = (int) worldPos.getX() - rect.x;
				int sy = (int) worldPos.getY() - rect.y;
				Point screenPos = new Point(sx, sy);

				// check if near the mouse
				if (screenPos.distance(e.getPoint()) < 20) {
					screenPos.x -= tooltip.getWidth() / 2;

					tooltip.setLocation(screenPos);
					tooltip.setVisible(true);
				} else {
					tooltip.setVisible(false);
				}
			}
		});

		waypointPainter = new WaypointPainter<ColoredWeightedWaypoint>();
		waypointPainter.setRenderer(new ColoredWeightedWaypointRenderer());

		// There can be many painter for a map
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(waypointPainter);

		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(
				painters);

		jXMapKit.getMainMap().setOverlayPainter(painter);

		// Display the viewer in a JFrame

		setTitle("JXMapviewer2 Example 6");
		getContentPane().add(jXMapKit);
		setSize(800, 600);
		// setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		SwingWorker<Void, Set<ColoredWeightedWaypoint>> worker = new SwingWorker<Void, Set<ColoredWeightedWaypoint>>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					Set<ColoredWeightedWaypoint> wplist = localizer.read();
					publish(wplist);
				}
			}

			protected void process(List<Set<ColoredWeightedWaypoint>> wplist) {
				setWaypoints(wplist.get(wplist.size() - 1));
			}
		};
		worker.execute();

	}

	public void setWaypoints(Set<ColoredWeightedWaypoint> waypoints) {
		waypointPainter.setWaypoints(waypoints);
		jXMapKit.repaint();
	}

	public void setWaypoints() {
		setWaypoints(new HashSet<ColoredWeightedWaypoint>());
		jXMapKit.repaint();
	}

	public void run() {

	}

}