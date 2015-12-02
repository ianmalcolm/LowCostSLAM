package net.ianbox.LowCostSLAM;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.ianbox.LowCostSLAM.GUI.JxMap;
import net.ianbox.LowCostSLAM.GUI.SimpleWaypoint;
import net.ianbox.LowCostSLAM.SLAM.Localizer;

import org.junit.Test;
import org.jxmapviewer.viewer.GeoPosition;

public class GUITest {

	@Test
	public void GUITest() {
		Localizer localizer = null;
		JxMap map = new JxMap(localizer);
		EventQueue.invokeLater(map);

		Random rand = new Random();
		Set<SimpleWaypoint> waypoints = new HashSet<SimpleWaypoint>();

		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 100; i++) {
				double x = rand.nextGaussian() * 1e-3;
				double y = rand.nextGaussian() * 1e-3;
				GeoPosition p = new GeoPosition(1.301680 + x, 103.786897 + y);
				waypoints.add(new SimpleWaypoint(1.0, Color.RED, p));
			}

			map.setParticles(waypoints);
			waypoints.clear();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
