package net.ianbox.LowCostSLAM.data;

import java.util.Date;

public abstract class Data {
	public final Date time;

	public Data(Long t) {
		time = new Date(t);
	}

	public Data(String line) {
		time = parse(line);
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
