package net.ianbox.LowCostSLAM.SLAM;

import net.ianbox.LowCostSLAM.GUI.Weighted;
import net.ianbox.LowCostSLAM.map.PointOnEdge;
import org.apache.log4j.Logger;

import com.graphhopper.util.EdgeIteratorState;

public class Particle implements Weighted, PointOnEdge {

	private static final Logger log = Logger
			.getLogger(Particle.class.getName());
	public final static double DEFAULTWEIGHT = 1.0;

	public final EdgeIteratorState edge;
	public final double weight;
	public final double dist;

	public Particle(EdgeIteratorState e, double r) {
		this(e, r, DEFAULTWEIGHT);
	}

	private Particle(EdgeIteratorState e, double r, double w) {
		edge = e;
		dist = r;
		weight = w;
	}

	void move() {
		log.trace("Let's move!");
	}

	double probability() {
		return 0;
	}

	public Particle setWeight(double w) {
		return new Particle(edge, dist, w);
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int getBaseNode() {
		return edge.getBaseNode();
	}

	@Override
	public int getAdjNode() {
		return edge.getAdjNode();
	}

	@Override
	public EdgeIteratorState getEdge() {
		return edge;
	}

	@Override
	public double getDist() {
		return dist;
	}

}
