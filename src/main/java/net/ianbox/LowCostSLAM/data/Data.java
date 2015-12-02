package net.ianbox.LowCostSLAM.data;

import java.util.Date;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public abstract class Data {

	protected static final XPathFactory xFactory = XPathFactory.instance();
	private static final XPathExpression<Attribute> TIME = xFactory.compile(
			"@Time", Filters.attribute());

	public final Date time;

	public Data(Long t) {
		time = new Date(t);
	}

	public Data(String line) {
		time = parse(line);
	}

	public Data(Element data) {
		
		long longTime = Long.MIN_VALUE;
		try {
			longTime = TIME.evaluateFirst(data).getLongValue();
		} catch (Exception e) {
			e.printStackTrace();
			longTime = Long.MIN_VALUE;
		}
		if (longTime < 0) {
			time = null;
		} else {
			time = new Date(longTime);
		}
	}

	private static Date parse(String line) {
		try {
			String[] words = line.split(",");
			String[] pair = words[0].split(":");
			Long timeLong = Long.parseLong(pair[1]);
			return new Date(timeLong);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract boolean isValid();

	boolean isTimeValid() {
		if (time == null) {
			return false;
		} else {
			return true;
		}
	}

	public abstract String toString();
}
