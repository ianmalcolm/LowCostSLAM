package net.ianbox.LowCostSLAM;

import net.ianbox.LowCostSLAM.map.GeoNode;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Particle {

	double weight = 1;
	GeoNode src = null;
	GeoNode dst = null;
	double dist = 0;
	Vector3D velocity;

	void move() {

	}

	double probability() {
		return 0;
	}



}
