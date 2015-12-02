package net.ianbox.LowCostSLAM;

import java.util.Date;

import net.ianbox.LowCostSLAM.data.Reader;
import net.ianbox.LowCostSLAM.data.SensorLogFileReader;
import net.ianbox.LowCostSLAM.data.SensorXMLFileReader;

import org.junit.Test;
import org.junit.Ignore;

public class DataTest {

	@Ignore
	@Test
	public void time() {
		Date t = new Date();
		System.out.println(t.getTime());
	}

	
	@Test
	public void logFileReader() {
		Reader reader = new SensorLogFileReader("sensor/sensorlogexample.txt");
		Thread t = new Thread(reader);
		t.start();
		for (int i = 0; i < 3; i++) {
			System.out.println(reader.read().toString());
		}
	}

	@Test
	public void xmlFileReader() {
		Reader reader = new SensorXMLFileReader("sensor/sensorlogexample.xml");
		Thread t = new Thread(reader);
		t.start();
		for (int i = 0; i < 6; i++) {
			System.out.println(reader.read().toString());
		}
	}
}
