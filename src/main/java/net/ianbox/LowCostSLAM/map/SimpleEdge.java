package net.ianbox.LowCostSLAM.map;

public class SimpleEdge implements Edge {

	public final int wayId;
	public final int adjId;
	public final int edgeId;

	public SimpleEdge(final int way, final int adj, final int e) {
		wayId = way;
		adjId = adj;
		edgeId = e;
	}

	public SimpleEdge(Edge edge) {
		wayId = edge.getWayId();
		adjId = edge.getAdjNodeId();
		edgeId = edge.getStartNodeId();
	}

	@Override
	public int getWayId() {
		return wayId;
	}

	@Override
	public int getStartNodeId() {
		return edgeId;
	}

	@Override
	public int getEndNodeId() {
		return edgeId + 1;
	}

	@Override
	public int getAdjNodeId() {
		return adjId;
	}

}
