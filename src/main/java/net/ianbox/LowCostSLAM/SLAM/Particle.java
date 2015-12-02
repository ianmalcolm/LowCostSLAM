package net.ianbox.LowCostSLAM.SLAM;

import net.ianbox.LowCostSLAM.map.GeoPosition;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.log4j.Logger;

public class Particle {

	public final double weight;
	public final GeoPosition pos;
	public final Vector3D velocity;
	private static final Logger log = Logger
			.getLogger(Particle.class.getName());

	public Particle(double w, GeoPosition p, Vector3D v) {
		weight = w;
		pos = p;
		velocity = v;
	}

	void move() {

	}

	double probability() {
		return 0;
	}

	public Particle setWeight(double w) {
		return new Particle(w, pos, velocity);
	}

}
