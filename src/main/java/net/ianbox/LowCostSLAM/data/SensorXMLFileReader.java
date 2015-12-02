package net.ianbox.LowCostSLAM.data;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class SensorXMLFileReader implements Reader {

	private static final Logger log = Logger
			.getLogger(SensorXMLFileReader.class.getName());
	public static final Map<String, Class<? extends Data>> SENSORDATATYPE = new HashMap<String, Class<? extends Data>>();;
	static {
		SENSORDATATYPE.put(GPSData.NAME, GPSData.class);
		SENSORDATATYPE.put(BaroData.NAME, BaroData.class);
		SENSORDATATYPE.put(AttitudeData.NAME, AttitudeData.class);
		SENSORDATATYPE.put(AccelerateData.NAME, AccelerateData.class);
	}

	private final BlockingQueue<Data> dq = new LinkedBlockingQueue<Data>();;
	private final List<Element> list;

	public SensorXMLFileReader(String filename) {
		SAXBuilder builder = new SAXBuilder();
		List<Element> l = null;
		try {
			Document doc = builder.build(filename);
			XPathFactory xFactory = XPathFactory.instance();
			XPathExpression<Element> expData = xFactory.compile(
					"/SensorData/Data", Filters.element());
			l = expData.evaluate(doc);
		} catch (Exception e) {
			log.fatal("Cannot load xml file!");
			e.printStackTrace();
			l = null;
			System.exit(1);
		}
		list = l;
	}

	public void run() {
		try {
			for (Element ele : list) {
				Data data = parse(ele);
				if (data == null) {
					continue;
				}
				dq.put(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private Data parse(Element ele) {
		try {
			for (String type : SENSORDATATYPE.keySet()) {
				if (!match(ele, type)) {
					continue;
				}
				Class<? extends Data> clazz = SENSORDATATYPE.get(type);
				Constructor<? extends Data> constructor;
				constructor = clazz.getConstructor(Element.class);
				Data data = (Data) constructor.newInstance(ele);

				if (data.isValid()) {
					return data;
				} else {
					return null;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Cannot construct data from xml element!");
			return null;
		}
	}

	private boolean match(Element ele, String name) {
		Attribute attrName = ele.getAttribute("Sensor");
		if (attrName == null) {
			return false;
		} else if (attrName.getValue().compareTo(name) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public Data read() {
		try {
			return dq.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
