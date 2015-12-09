package net.ianbox.LowCostSLAM.map;

import com.graphhopper.storage.index.QueryResult;
import net.ianbox.LowCostSLAM.GUI.Weighted;

public class SimpleWeightedEdge implements WeightedEdge {

	public final int wayId;
	public final int edgeId;
	public final double weight;
	private static final double DEFAULTWEIGHT = 1.0;

	public SimpleWeightedEdge(QueryResult qr) {
		this(qr, DEFAULTWEIGHT);
	}

	public SimpleWeightedEdge(QueryResult qr, double w) {
		wayId = qr.getClosestEdge().getEdge();
		if (qr.getSnappedPosition() == QueryResult.Position.EDGE) {
			edgeId = qr.getWayIndex();
		} else {
			edgeId = qr.getWayIndex() - 1;
		}
		weight = w;
	}

	private SimpleWeightedEdge(final int wid, final int eid, final double w) {
		wayId = wid;
		edgeId = eid;
		weight = w;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public Weighted setWeight(double w) {
		return new SimpleWeightedEdge(wayId, edgeId, w);
	}

	@Override
	public int getWayId() {
		// TODO Auto-generated method stub
		return wayId;
	}

	@Override
	public int getEdgeId() {
		// TODO Auto-generated method stub
		return edgeId;
	}
}
