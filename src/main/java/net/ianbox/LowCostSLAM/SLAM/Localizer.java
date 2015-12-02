package net.ianbox.LowCostSLAM.SLAM;

import java.util.List;

public interface Localizer extends Runnable {

	public List<Particle> read();

}
