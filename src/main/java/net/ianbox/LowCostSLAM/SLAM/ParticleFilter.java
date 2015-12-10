package net.ianbox.LowCostSLAM.SLAM;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.ianbox.LowCostSLAM.GUI.ColoredWeightedWaypoint;
import net.ianbox.LowCostSLAM.GUI.Weighted;
import net.ianbox.LowCostSLAM.data.Data;
import net.ianbox.LowCostSLAM.data.Reader;
import net.ianbox.LowCostSLAM.map.Edge;
import net.ianbox.LowCostSLAM.map.GHMap;
import net.ianbox.LowCostSLAM.map.WeightedEdge;

import org.apache.log4j.Logger;

import com.graphhopper.util.shapes.GHPoint;

public class ParticleFilter implements Localizer {

	public static final int MAXPARTICLENUMBER = 100;
	private static final Logger log = Logger.getLogger(ParticleFilter.class
			.getName());
	private final BlockingQueue<Set<ColoredWeightedWaypoint>> dq = new LinkedBlockingQueue<Set<ColoredWeightedWaypoint>>();;
	private final Reader reader;
	private final GHMap map;
	private List<Particle> parts = null;
	private final Random rand = new Random();

	public ParticleFilter(Reader _reader, GHMap _map) {
		reader = _reader;
		map = _map;
	}

	/**
	 * Resampling wheel algorithm from udacity
	 * 
	 * @param ins
	 * @return
	 */
	public static List<? extends Weighted> resampling(
			List<? extends Weighted> ins, int resamplingNumber) {

		assert ins.size() > 0;

		double maxWeight = 0;
		for (Weighted p : ins) {
			if (maxWeight < p.getWeight()) {
				maxWeight = p.getWeight();
			}
		}

		List<Weighted> outs = new LinkedList<Weighted>();

		Random rand = new Random();
		double beta = 0;
		int index = rand.nextInt(ins.size());

		for (int i = 0; i < resamplingNumber; i++) {
			beta += rand.nextDouble() * 2 * maxWeight;
			while (beta > ins.get(index).getWeight()) {
				beta -= ins.get(index).getWeight();
				index = (index + 1) % ins.size();
			}
			outs.add(ins.get(index));
		}

		return outs;
	}

	/**
	 * Normalize the weights of the particles
	 * 
	 * @param ins
	 * @return
	 */
	public static List<? extends Weighted> normalize(
			List<? extends Weighted> ins) {
		assert ins.size() > 0;

		double totalWeight = 0;
		for (Weighted p : ins) {
			totalWeight += p.getWeight();
		}

		List<Weighted> outs = new LinkedList<Weighted>();

		for (Weighted p : ins) {
			outs.add(p.setWeight(p.getWeight() / totalWeight));
		}

		return outs;
	}

	public void run() {
		while (true) {
		}
	}

	void sense() {

		Data data = reader.read();
		log.trace("Sensing data" + data.toString());
	}

	public Set<ColoredWeightedWaypoint> read() {

		try {
			Set<ColoredWeightedWaypoint> partlist = dq.take();
			return partlist;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void proposeParticles(GHPoint gp, double accH, double accV, int num) {
		parts = new LinkedList<Particle>();

		List<Edge> edges = map.enumerateEdges(gp, accH * 3);
		List<WeightedEdge> weightedEdges = new LinkedList<WeightedEdge>();
		for (Edge edge : edges) {
			weightedEdges.add(map.createWeightedEdge(edge));
		}

		List<Particle> plist = new LinkedList<Particle>();

		while (plist.size() < num) {
			@SuppressWarnings("unchecked")
			List<WeightedEdge> tmplst = (List<WeightedEdge>) resampling(
					weightedEdges, num - plist.size());
			for (WeightedEdge we : tmplst) {
				Particle p = new Particle(we, rand.nextDouble()
						* we.getWeight());

				GHPoint pp = map.getPosition(p);
				if (GHMap.calcDist(pp, gp) > accH * 3) {
					continue;
				} else {
					plist.add(p);
				}
			}
		}
		parts.addAll(plist);
	}

	public void measure() {

	}

	public void disp() {
		Set<ColoredWeightedWaypoint> list = new HashSet<ColoredWeightedWaypoint>();
		for (Particle p : parts) {
			list.add(new ColoredParticle(map.getPosition(p)));
		}
		dq.add(list);
	}
}
