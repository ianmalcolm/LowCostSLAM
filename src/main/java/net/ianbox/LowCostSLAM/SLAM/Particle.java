package net.ianbox.LowCostSLAM.SLAM;

import net.ianbox.LowCostSLAM.GUI.Weighted;
import net.ianbox.LowCostSLAM.map.Edge;
import net.ianbox.LowCostSLAM.map.PointOnEdge;
import net.ianbox.LowCostSLAM.map.WeightedEdge;

import org.apache.log4j.Logger;

public class Particle implements Weighted, PointOnEdge {

	private static final Logger log = Logger
			.getLogger(Particle.class.getName());
	public final static double DEFAULTWEIGHT = 1.0;

	public final int wayId;
	public final int startNodeId;
	public final int endNodeId;
	public final double weight;
	public final double dist;

	public Particle(WeightedEdge we, double r) {
		this(we, r, we.getWeight());
	}

	private Particle(final Edge edge, double r, double w) {
		wayId = edge.getWayId();
		startNodeId = edge.getStartNodeId();
		endNodeId = edge.getEndNodeId();
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
		return new Particle(this, dist, w);
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
		return wayId;
	}

	@Override
	public int getStartNodeId() {
		return startNodeId;
	}

	@Override
	public int getEndNodeId() {
		return endNodeId;
	}


}
