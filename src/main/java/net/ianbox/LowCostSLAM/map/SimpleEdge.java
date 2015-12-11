package net.ianbox.LowCostSLAM.map;

import org.apache.log4j.Logger;

import com.graphhopper.util.EdgeIteratorState;

public class SimpleEdge implements Edge {

	public final int wayId;
	public final int adjId;
	public final int edgeId;
	public final boolean biDirect;

	private static final Logger log = Logger.getLogger(SimpleEdge.class
			.getName());

	public SimpleEdge(EdgeIteratorState edge, final int e) {
		wayId = edge.getEdge();
		if (!edge.isForward(GHMap.ENCODER)) {
			if (edge.isBackward(GHMap.ENCODER)) {
				edge = edge.detach(true);
			} else {
				log.fatal("Way " + edge.getName()
						+ " is not accessible in either directions!");
			}
		}

		if (edge.isForward(GHMap.ENCODER) && edge.isBackward(GHMap.ENCODER)) {
			biDirect = true;
		} else {
			biDirect = false;
		}
		adjId = edge.getAdjNode();
		edgeId = e;
	}

	public SimpleEdge(Edge edge) {
		wayId = edge.getWayId();
		adjId = edge.getAdjNodeId();
		edgeId = edge.getStartNodeId();
		biDirect = edge.isBiDirect();
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

	@Override
	public boolean isBiDirect() {
		return biDirect;
	}

}
