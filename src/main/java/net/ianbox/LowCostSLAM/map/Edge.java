package net.ianbox.LowCostSLAM.map;

public interface Edge {

	public int getWayId();
	public int getAdjNodeId();

	public int getStartNodeId();
	public int getEndNodeId();
	public boolean isBiDirect();
}
