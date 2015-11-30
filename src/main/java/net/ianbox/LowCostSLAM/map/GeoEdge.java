package net.ianbox.LowCostSLAM.map;

import org.jgrapht.graph.DefaultWeightedEdge;

public class GeoEdge extends DefaultWeightedEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2696208611244824212L;
	private String label = "";

	public String getLabel() {
		return label;
	}

	public void setLabel(String l) {
		label = l;
	}
}
