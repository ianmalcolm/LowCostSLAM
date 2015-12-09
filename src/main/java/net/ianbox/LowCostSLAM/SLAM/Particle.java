package net.ianbox.LowCostSLAM.SLAM;

import net.ianbox.LowCostSLAM.GUI.Weighted;
import net.ianbox.LowCostSLAM.map.PointOnEdge;
import net.ianbox.LowCostSLAM.map.WeightedEdge;

import org.apache.log4j.Logger;

public class Particle implements Weighted, PointOnEdge {

	private static final Logger log = Logger
			.getLogger(Particle.class.getName());
	public final static double DEFAULTWEIGHT = 1.0;

	public final int wayId;
	public final int edgeId;
	public final double weight;
	public final double dist;

	public Particle(WeightedEdge we, double r) {
		this(we.getWayId(), we.getEdgeId(), r, DEFAULTWEIGHT);
	}

	private Particle(final int way, final int edge, double r, double w) {
		wayId = way;
		edgeId = edge;
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
		return new Particle(wayId, edgeId, dist, w);
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public double getDist() {
		return dist;
	}

	@Override
	public int getWayId() {
		// TODO Auto-generated method stub
		return wayId;
	}

	@Override
	public int getEdgeId() {
		// TODO Auto-generated method stub
		return edgeId;
	}

}
