package net.ianbox.LowCostSLAM;

import java.awt.EventQueue;

import org.junit.Test;

import com.graphhopper.util.shapes.GHPoint;
import net.ianbox.LowCostSLAM.GUI.JxMap;
import net.ianbox.LowCostSLAM.SLAM.ParticleFilter;
import net.ianbox.LowCostSLAM.map.GHMap;

public class PFTest {

	@Test
	public void initializeParticlesTest() {
		GHMap ghmap = new GHMap("map/malaysia-singapore-brunei-latest.osm.pbf");
		ParticleFilter pf = new ParticleFilter(null, ghmap);
		JxMap jxmap = new JxMap(pf);
		EventQueue.invokeLater(jxmap);

		GHPoint gp = new GHPoint(1.298183, 103.788152);
		while (true) {
			pf.proposeParticles(gp, 100, 1, 100);
			// pf.measure();
			pf.disp();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
