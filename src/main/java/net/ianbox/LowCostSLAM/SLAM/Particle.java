package net.ianbox.LowCostSLAM.SLAM;

import java.util.Random;

import net.ianbox.LowCostSLAM.GUI.Weighted;
import net.ianbox.LowCostSLAM.map.Edge;
import net.ianbox.LowCostSLAM.map.Map;
import net.ianbox.LowCostSLAM.map.PointOnEdge;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.log4j.Logger;

public class Particle implements Weighted, PointOnEdge {

	private static final Logger log = Logger
			.getLogger(Particle.class.getName());
	public static final double DEFAULTWEIGHT = 1.0;
	public static final double DEFAULTSPEED = 1.0;

	public final int wayId;
	public final int adjId;
	public final int startNodeId;
	public final boolean biDirect;
	public final double dist;

	public final double weight;

	public final double speed;

	private static final Random rand = new Random();

	public Particle(final Edge edge, final double _dist) {
		this(edge, _dist, DEFAULTWEIGHT, DEFAULTSPEED);
	}

	private Particle(final Edge _edge, final double _dist,
			final double _weight, final double _speed) {
		wayId = _edge.getWayId();
		adjId = _edge.getAdjNodeId();
		startNodeId = _edge.getStartNodeId();
		dist = _dist;
		weight = _weight;
		biDirect = _edge.isBiDirect();
		speed = _speed;
	}

	public Particle move(Map _map, double _time) {
		log.trace("Let's move!");
		double curSpeed = speed;
		while (_time > 0) {
			double spdDiff = speedVariance(curSpeed);
			if (_time > 1.0) {
				_time -= 1.0;
			} else {
				_time = 0.0;
				spdDiff = spdDiff * _time;
			}
			curSpeed += spdDiff;
		}
		return this.setSpeed(curSpeed);
	}

	private static double speedVariance(double _speed) {
		if (rand.nextBoolean()) {
			Gaussian spdUpVar = new Gaussian(0, 5);
			return rand.nextGaussian()
					* (spdUpVar.value(_speed) / spdUpVar.value(0) * 3);
		} else {
			Gaussian spdDnVar = new Gaussian(10, 5);
			return rand.nextGaussian() * -1
					* (spdDnVar.value(_speed) / spdDnVar.value(0) * 3);
		}
	}

	double probability() {
		return 0;
	}

	public Particle setSpeed(double _speed) {
		return new Particle(this, dist, weight, _speed);
	}

	public Particle setWeight(double _weight) {
		return new Particle(this, dist, _weight, speed);
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
		return startNodeId + 1;
	}

	@Override
	public int getAdjNodeId() {
		return adjId;
	}

	@Override
	public boolean isBiDirect() {
		return biDirect;
	}

}
