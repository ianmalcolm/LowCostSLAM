package net.ianbox.LowCostSLAM.data;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;

public class AttitudeData extends Data {

	public static final String NAME = "Attitude";
	public static final String PATTERN = "^" + NAME + ":.+";

	private static final XPathExpression<Attribute> Q0 = xFactory.compile(
			"@Q0", Filters.attribute());
	private static final XPathExpression<Attribute> Q1 = xFactory.compile(
			"@Q1", Filters.attribute());
	private static final XPathExpression<Attribute> Q2 = xFactory.compile(
			"@Q2", Filters.attribute());
	private static final XPathExpression<Attribute> Q3 = xFactory.compile(
			"@Q3", Filters.attribute());

	public final double q0;
	public final double q1;
	public final double q2;
	public final double q3;

	/**
	 * 
	 * @param a
	 * @param t
	 */

	public AttitudeData(double[] a, Long t) {
		super(t);

		double _q0 = Double.NaN;
		double _q1 = Double.NaN;
		double _q2 = Double.NaN;
		double _q3 = Double.NaN;

		try {
			_q0 = a[0];
			_q1 = a[1];
			_q2 = a[2];
			_q3 = a[3];

		} catch (Exception e) {
			e.printStackTrace();
			_q0 = Double.NaN;
			_q1 = Double.NaN;
			_q2 = Double.NaN;
			_q3 = Double.NaN;
		}

		q0 = _q0;
		q1 = _q1;
		q2 = _q2;
		q3 = _q3;
	}

	public AttitudeData(String line) {
		super(line);

		double _q0 = Double.NaN;
		double _q1 = Double.NaN;
		double _q2 = Double.NaN;
		double _q3 = Double.NaN;

		try {
			String[] words = line.split(",");

			for (String word : words) {
				String[] pair = word.split(":");
				if (pair[0].compareTo("Q0") == 0) {
					_q0 = Double.parseDouble(pair[1]);
				} else if (pair[0].compareTo("Q1") == 0) {
					_q1 = Double.parseDouble(pair[1]);
				} else if (pair[0].compareTo("Q2") == 0) {
					_q2 = Double.parseDouble(pair[1]);
				} else if (pair[0].compareTo("Q3") == 0) {
					_q3 = Double.parseDouble(pair[1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			_q0 = Double.NaN;
			_q1 = Double.NaN;
			_q2 = Double.NaN;
			_q3 = Double.NaN;
		}

		q0 = _q0;
		q1 = _q1;
		q2 = _q2;
		q3 = _q3;
	}

	public AttitudeData(Element data) {
		super(data);

		double _q0 = Double.NaN;
		double _q1 = Double.NaN;
		double _q2 = Double.NaN;
		double _q3 = Double.NaN;

		try {
			_q0 = Q0.evaluateFirst(data).getDoubleValue();
			_q1 = Q1.evaluateFirst(data).getDoubleValue();
			_q2 = Q2.evaluateFirst(data).getDoubleValue();
			_q3 = Q3.evaluateFirst(data).getDoubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			_q0 = Double.NaN;
			_q1 = Double.NaN;
			_q2 = Double.NaN;
			_q3 = Double.NaN;
		}

		q0 = _q0;
		q1 = _q1;
		q2 = _q2;
		q3 = _q3;
	}

	@Override
	public boolean isValid() {
		if (!isNormed(q0, q1, q2, q3) || !isTimeValid()) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isNormed(double a, double b, double c, double d) {
		double sum = a * a + b * b + c * c + d * d;
		return Math.abs(sum - 1.0) < 1e-4;
	}

	@Override
	public String toString() {
		String s = time.toString() + ":" + NAME + ",Q0:" + q0 + ",Q1:" + q1
				+ ",Q2:" + q2 + ",Q3:" + q3;
		return s;
	}
}
