package net.ianbox.LowCostSLAM.data;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;

public class AccelerateData extends Data {

	public static final String NAME = "Accelerate";
	public static final String PATTERN = "^" + NAME + ":.+";

	private static final XPathExpression<Attribute> ACCX = xFactory.compile(
			"@AccX", Filters.attribute());
	private static final XPathExpression<Attribute> ACCY = xFactory.compile(
			"@AccY", Filters.attribute());
	private static final XPathExpression<Attribute> ACCZ = xFactory.compile(
			"@AccZ", Filters.attribute());

	public final double accX;
	public final double accY;
	public final double accZ;

	public AccelerateData(double[] a, Long t) {
		super(t);

		double x = Double.NaN;
		double y = Double.NaN;
		double z = Double.NaN;

		try {
			x = a[0];
			y = a[1];
			z = a[2];
		} catch (Exception e) {
			e.printStackTrace();
			x = Double.NaN;
			y = Double.NaN;
			z = Double.NaN;
		}
		accX = x;
		accY = y;
		accZ = z;
	}

	public AccelerateData(String line) {
		super(line);

		double x = Double.NaN;
		double y = Double.NaN;
		double z = Double.NaN;

		try {
			String[] words = line.split(",");

			for (String word : words) {
				String[] pairs = word.split(":");
				if (pairs[0].compareTo("AccX") == 0) {
					x = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("AccY") == 0) {
					y = Double.parseDouble(pairs[1]);
				} else if (pairs[0].compareTo("AccZ") == 0) {
					z = Double.parseDouble(pairs[1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			x = Double.NaN;
			y = Double.NaN;
			z = Double.NaN;
		}

		accX = x;
		accY = y;
		accZ = z;
	}

	public AccelerateData(Element data) {
		super(data);

		double x = Double.NaN;
		double y = Double.NaN;
		double z = Double.NaN;

		try {
			x = ACCX.evaluateFirst(data).getDoubleValue();
			y = ACCY.evaluateFirst(data).getDoubleValue();
			z = ACCZ.evaluateFirst(data).getDoubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			x = Double.NaN;
			y = Double.NaN;
			z = Double.NaN;
		}
		accX = x;
		accY = y;
		accZ = z;
	}

	@Override
	public boolean isValid() {
		if (Double.isNaN(accX) || Double.isNaN(accY) || Double.isNaN(accZ)
				|| !isTimeValid()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		String s = time.toString() + ":" + NAME + ",AccX:" + accX + ",AccY:"
				+ accY + ",AccZ:" + accZ;
		return s;
	}

}
