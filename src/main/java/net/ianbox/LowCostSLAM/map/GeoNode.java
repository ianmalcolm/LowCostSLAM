package net.ianbox.LowCostSLAM.map;

public class GeoNode extends GeoPosition {
	public final long id;

	public GeoNode(long _id, double _lat, double _lon, double _ele) {
		super(_lat, _lon, _ele);
		id = _id;

	}

	public double dist(GeoNode o) {
		return super.dist(o);
	}

	public String toString() {
		return Long.toString(id);
	}
}
