package net.ianbox.LowCostSLAM.map;

public class SimpleEdge implements Edge {

	public final int wayId;
	public final int edgeId;

	public SimpleEdge(final int w, final int e) {
		wayId = w;
		edgeId = e;
	}

	public SimpleEdge(Edge edge) {
		wayId = edge.getWayId();
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
		// TODO Auto-generated method stub
		return edgeId + 1;
	}

}
