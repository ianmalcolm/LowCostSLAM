package net.ianbox.LowCostSLAM.data;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;

public class BaroData extends Data {

	public static final String NAME = "Baro";
	public static final String PATTERN = "^" + NAME + ":.+";

	private static final XPathExpression<Attribute> PRESS = xFactory.compile(
			"@Press", Filters.attribute());

	public final double pressure;

	public BaroData(double in, Long t) {
		super(t);
		pressure = in;
	}

	public BaroData(String line) {
		super(line);

		double b = Double.NaN;
		try {
			String[] words = line.split(",");
			for (String word : words) {
				String[] pair = word.split(":");
				if (pair[0].compareTo("Press") == 0) {
					b = Double.parseDouble(pair[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = Double.NaN;
		}

		pressure = b;

	}

	public BaroData(Element data) {
		super(data);

		double b = Double.NaN;
		try {
			b = PRESS.evaluateFirst(data).getDoubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			b = Double.NaN;
		}
		pressure = b;
	}

	@Override
	public boolean isValid() {
		if (Double.isNaN(pressure) || !isTimeValid()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		String s = time.toString() + ":" + NAME + ",Press:" + pressure;
		return s;
	}

}
