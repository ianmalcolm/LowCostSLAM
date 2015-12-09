package net.ianbox.LowCostSLAM;

import java.awt.EventQueue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.graphhopper.util.shapes.GHPoint;

import net.ianbox.LowCostSLAM.GUI.ColoredWeightedWaypoint;
import net.ianbox.LowCostSLAM.GUI.JxMap;
import net.ianbox.LowCostSLAM.SLAM.ColoredParticle;
import net.ianbox.LowCostSLAM.SLAM.Particle;
import net.ianbox.LowCostSLAM.SLAM.ParticleFilter;
import net.ianbox.LowCostSLAM.map.GHMap;
import net.ianbox.LowCostSLAM.map.SimpleWeightedEdge;
import net.ianbox.LowCostSLAM.map.WeightedEdge;

public class PFTest {

	@Ignore
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

	@Test
	public void pointOnEdge() {
		GHMap map = new GHMap("map/malaysia-singapore-brunei-latest.osm.pbf");
		GHPoint gp = new GHPoint(1.298183, 103.788152);

		JxMap jxmap = new JxMap(null);
		EventQueue.invokeLater(jxmap);

		List<SimpleWeightedEdge> welist = (List<SimpleWeightedEdge>) map
				.rangeSearch(gp, 100 * 3);
		List<Particle> plist = new LinkedList<Particle>();

		int num = 1;

		while (true) {
			@SuppressWarnings("unchecked")
			List<WeightedEdge> tmplst = (List<WeightedEdge>) ParticleFilter
					.resampling(welist, num);
			plist.clear();
			for (WeightedEdge we : tmplst) {
				Particle ps = new Particle(we, 0.0 * we.getWeight());
				Particle pm = new Particle(we, 0.5 * we.getWeight());
				Particle pe = new Particle(we, 1.0 * we.getWeight());
				plist.add(ps);
				plist.add(pm);
				plist.add(pe);
			}

			Set<ColoredWeightedWaypoint> pset = new HashSet<ColoredWeightedWaypoint>();
			for (Particle p : plist) {
				pset.add(new ColoredParticle(map.getPosition(p)));
			}

			jxmap.setWaypoints(pset);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
