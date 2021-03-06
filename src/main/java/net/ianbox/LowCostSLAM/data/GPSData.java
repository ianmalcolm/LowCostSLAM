package net.ianbox.LowCostSLAM.data;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;

public class GPSData extends Data {

	public static final String NAME = "GPS";
	public static final String PATTERN = "^" + NAME + ":.+";

	private static final XPathExpression<Attribute> LAT = xFactory.compile(
			"@Lat", Filters.attribute());
	private static final XPathExpression<Attribute> LON = xFactory.compile(
			"@Lon", Filters.attribute());
	private static final XPathExpression<Attribute> ALT = xFactory.compile(
			"@Alt", Filters.attribute());
	private static final XPathExpression<Attribute> ACCH = xFactory.compile(
			"@AccHor", Filters.attribute());
	private static final XPathExpression<Attribute> ACCV = xFactory.compile(
			"@AccVer", Filters.attribute());
	private static final XPathExpression<Attribute> SPEED = xFactory.compile(
			"@Speed", Filters.attribute());

	public final double lat;
	public final double lon;
	public final double alt;
	public final double accH;
	public final double accV;
	public final double speed;

	public GPSData(double[] in, Long t) {
		super(t);

		double _lat = Double.NaN;
		double _lon = Double.NaN;
		double _alt = Double.NaN;
		double _accH = Double.NaN;
		double _accV = Double.NaN;
		double _speed = Double.NaN;

		try {
			_lat = in[0];
			_lon = in[1];
			_alt = in[2];
			_accH = in[3];
			_accV = in[4];
			_speed = in[5];
		} catch (Exception e) {
			e.printStackTrace();
			_lat = Double.NaN;
			_lon = Double.NaN;
			_alt = Double.NaN;
			_accH = Double.NaN;
			_accV = Double.NaN;
			_speed = Double.NaN;
		}

		lat = _lat;
		lon = _lon;
		alt = _alt;
		accH = _accH;
		accV = _accV;
		speed = _speed;

	}

	public GPSData(String line) {
		super(line);

		double _lat = Double.NaN;
		double _lon = Double.NaN;
		double _alt = Double.NaN;
		double _accH = Double.NaN;
		double _accV = Double.NaN;
		double _speed = Double.NaN;

		try {
			String[] words = line.split(",");

			for (String word : words) {
				String[] pairs = word.split(":");
				if (pairs[0].compareTo("Lat") == 0) {
					_lat = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("Lon") == 0) {
					_lon = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("Alt") == 0) {
					_alt = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("AccHor") == 0) {
					_accH = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("AccVer") == 0) {
					_accV = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("Speed") == 0) {
					_speed = Double.parseDouble(pairs[1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			_lat = Double.NaN;
			_lon = Double.NaN;
			_alt = Double.NaN;
			_accH = Double.NaN;
			_accV = Double.NaN;
			_speed = Double.NaN;
		}

		lat = _lat;
		lon = _lon;
		alt = _alt;
		accH = _accH;
		accV = _accV;
		speed = _speed;
	}

	public GPSData(Element data) {
		super(data);

		double _lat = Double.NaN;
		double _lon = Double.NaN;
		double _alt = Double.NaN;
		double _accH = Double.NaN;
		double _accV = Double.NaN;
		double _speed = Double.NaN;

		try {

			_lat = LAT.evaluateFirst(data).getDoubleValue();
			_lon = LON.evaluateFirst(data).getDoubleValue();
			_accH = ACCH.evaluateFirst(data).getDoubleValue();

			Attribute attralt = ALT.evaluateFirst(data);
			Attribute attraccV = ACCV.evaluateFirst(data);
			Attribute attrspd = SPEED.evaluateFirst(data);

			_alt = attralt != null ? attralt.getDoubleValue() : Double.NaN;
			_accV = attraccV != null ? attraccV.getDoubleValue() : Double.NaN;
			_speed = attrspd != null ? attrspd.getDoubleValue() : Double.NaN;
		} catch (Exception e) {
			e.printStackTrace();
			_lat = Double.NaN;
			_lon = Double.NaN;
			_accH = Double.NaN;
			_alt = Double.NaN;
			_accV = Double.NaN;
			_speed = Double.NaN;
		}

		lat = _lat;
		lon = _lon;
		alt = _alt;
		accH = _accH;
		accV = _accV;
		speed = _speed;
	}

	@Override
	public boolean isValid() {
		if (Double.isNaN(lat) || Double.isNaN(lon) || Double.isNaN(accH)
				|| !isTimeValid()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		String s = time.toString() + ":" + NAME + ",Lat:" + lat + ",Lon:" + lon
				+ ",Alt:" + alt + ",AccHor:" + accH + ",AccVer:" + accV
				+ ",Speed:" + speed;
		return s;
	}
}
