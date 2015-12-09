package net.ianbox.LowCostSLAM.SLAM;

import java.util.Set;

import net.ianbox.LowCostSLAM.GUI.ColoredWeightedWaypoint;

public interface Localizer extends Runnable {

	public Set<ColoredWeightedWaypoint> read();

}
