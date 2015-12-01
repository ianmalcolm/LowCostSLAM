package net.ianbox.LowCostSLAM.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class SensorLogFileReader implements Reader {

	private static final Logger log = Logger
			.getLogger(SensorLogFileReader.class.getName());
	public static final Map<String, Class<? extends Data>> SENSORDATATYPE = new HashMap<String, Class<? extends Data>>();;
	static {
		SENSORDATATYPE.put(GPSData.PATTERN, GPSData.class);
		SENSORDATATYPE.put(BaroData.PATTERN, BaroData.class);
		SENSORDATATYPE.put(AttitudeData.PATTERN, AttitudeData.class);
		SENSORDATATYPE.put(AccelerateData.PATTERN, AccelerateData.class);
	}

	private final BlockingQueue<Data> dq = new LinkedBlockingQueue<Data>();;
	private BufferedReader dataBuf = null;

	public SensorLogFileReader(String sensorLogFileName) {
		// TODO Auto-generated constructor stub
		try {
			dataBuf = new BufferedReader(new FileReader(sensorLogFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Data read() {

		try {
			return dq.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Data parse(String line) {

		try {

			for (String pattern : SENSORDATATYPE.keySet()) {
				if (!line.matches(pattern)) {
					continue;
				}
				Class<? extends Data> clazz = SENSORDATATYPE.get(pattern);
				Constructor<? extends Data> constructor;
				constructor = clazz.getConstructor(String.class);
				Data data = (Data) constructor.newInstance(line);

				if (data.isValid()) {
					return data;
				} else {
					return null;
				}
			}

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void run() {
		try {
			String line = "";
			while ((line = dataBuf.readLine()) != null) {
				log.trace("Parsing " + line);
				Data data = parse(line);
				if (data == null) {
					continue;
				}
				dq.put(data);
			}
			dataBuf.close();
			// Thread.currentThread().interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.trace("End Execution!");
	}

}
