package net.ianbox.LowCostSLAM.map;

import com.graphhopper.util.EdgeIteratorState;

public interface Edge {

	public int getBaseNode();
	public int getAdjNode();
	public EdgeIteratorState getEdge();
}
