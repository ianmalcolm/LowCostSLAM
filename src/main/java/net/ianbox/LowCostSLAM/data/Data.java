package net.ianbox.LowCostSLAM.data;

import java.util.Date;

public abstract class Data {
	public final Date time;

	public Data(Long t) {
		time = new Date(t);
	}
}
