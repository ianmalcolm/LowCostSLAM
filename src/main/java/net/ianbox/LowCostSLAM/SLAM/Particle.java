package net.ianbox.LowCostSLAM.SLAM;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.log4j.Logger;


public class Particle {

	double weight = 1;
//	GeoNode src = null;
//	GeoNode dst = null;
	double dist = 0;
	Vector3D velocity;
	private static final Logger log = Logger.getLogger(Particle.class
			.getName());
	
	void move() {

	}

	double probability() {
		return 0;
	}



}
