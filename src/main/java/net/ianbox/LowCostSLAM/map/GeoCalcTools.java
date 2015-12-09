package net.ianbox.LowCostSLAM.map;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

import com.graphhopper.util.DistancePlaneProjection;
import com.graphhopper.util.shapes.GHPoint;

public class GeoCalcTools extends DistancePlaneProjection {

	public GHPoint calcPointOnEdge(double a_lat_deg, double a_lon_deg,
			double b_lat_deg, double b_lon_deg, double r_dist2d) {

		double shrinkFactor = cos(toRadians((a_lat_deg + b_lat_deg) / 2));
		double a_lat = a_lat_deg;
		double a_lon = a_lon_deg * shrinkFactor;

		double b_lat = b_lat_deg;
		double b_lon = b_lon_deg * shrinkFactor;

		double delta_lon = b_lon - a_lon;
		double delta_lat = b_lat - a_lat;

		double edgeDist = calcDist(a_lat_deg, a_lon_deg, b_lat_deg, b_lon_deg);

		assert edgeDist <= r_dist2d;
		assert r_dist2d >= 0;

		// double factor = calcNormalizedDist(r_dist2d)
		// / calcNormalizedDist(edgeDist);
		double factor = r_dist2d / edgeDist;

		// x,y is projection of r onto segment a-b
		double c_lat = a_lat + factor * delta_lat;
		double c_lon = a_lon + factor * delta_lon;
		GHPoint c = new GHPoint(c_lat, c_lon / shrinkFactor);
		return c;
	}

	public GHPoint calcPointOnEdge(GHPoint a, GHPoint b, double r_dist2d) {
		return calcPointOnEdge(a.lat, a.lon, b.lat, b.lon, r_dist2d);
	}

}
