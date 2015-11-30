package net.ianbox.LowCostSLAM.map;

import java.util.Date;

import org.geotools.referencing.GeodeticCalculator;

public class GeoPosition {

	public final double lat;
	public final double lon;
	public final double ele;
	public final Date time;

	public GeoPosition(double _lat, double _lon, double _ele, long _time) {
		lat = _lat;
		lon = _lon;
		ele = _ele;
		time = new Date(_time);
	}

	public GeoPosition(double _lat, double _lon, double _ele, Date _time) {
		this(_lat, _lon, _ele, _time.getTime());

	}

	public GeoPosition(double _lat, double _lon, double _ele) {
		lat = _lat;
		lon = _lon;
		ele = _ele;
		time = null;
	}

	public double dist(GeoPosition o) {
		GeodeticCalculator calc = new GeodeticCalculator();
		calc.setStartingGeographicPoint(lon, lat);
		calc.setDestinationGeographicPoint(o.lon, o.lat);
		double dist = calc.getOrthodromicDistance();
		if (!Double.isNaN(ele) && !Double.isNaN(o.ele)) {
			dist = Math.sqrt(dist * dist + (ele - o.ele) * (ele - o.ele));
		}
		return dist;
	}

	public String toString() {
		return lat + "," + lon + "," + ele;
	}

}
