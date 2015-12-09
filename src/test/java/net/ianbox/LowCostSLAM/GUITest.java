package net.ianbox.LowCostSLAM;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.ianbox.LowCostSLAM.GUI.JxMap;
import net.ianbox.LowCostSLAM.GUI.ColoredWeightedWaypoint;
import net.ianbox.LowCostSLAM.SLAM.ColoredParticle;
import net.ianbox.LowCostSLAM.SLAM.Localizer;

import org.junit.Test;
import com.graphhopper.util.shapes.GHPoint;

public class GUITest {

	@Test
	public void JxMapKitTest() {
		Localizer localizer = null;
		JxMap map = new JxMap(localizer);
		EventQueue.invokeLater(map);

		Random rand = new Random();
		Set<ColoredWeightedWaypoint> waypoints = new HashSet<ColoredWeightedWaypoint>();

		while (true) {
			for (int i = 0; i < 100; i++) {
				double x = rand.nextGaussian() * 1e-3;
				double y = rand.nextGaussian() * 1e-3;
				GHPoint p = new GHPoint(1.301680 + x, 103.786897 + y);
				waypoints.add( new ColoredParticle(
						p,1.0, Color.RED));
			}

			map.setWaypoints(waypoints);
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
