package net.ianbox.LowCostSLAM.map;

import com.graphhopper.util.EdgeIteratorState;

import net.ianbox.LowCostSLAM.GUI.Weighted;

public class SimpleWeightedEdge implements WeightedEdge {

	public final EdgeIteratorState edge;
	public final double weight;

	public SimpleWeightedEdge(EdgeIteratorState e, double w) {
		edge = e;
		weight = w;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public Weighted setWeight(double w) {
		return new SimpleWeightedEdge(edge, w);
	}

	@Override
	public int getBaseNode() {
		return edge.getBaseNode();
	}

	@Override
	public int getAdjNode() {
		return edge.getAdjNode();
	}

	@Override
	public EdgeIteratorState getEdge() {
		// TODO Auto-generated method stub
		return edge;
	}
}
