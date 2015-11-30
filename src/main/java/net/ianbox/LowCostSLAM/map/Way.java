package net.ianbox.LowCostSLAM.map;

import java.util.LinkedList;

class Way extends LinkedList<GeoNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6399796541750293158L;
	public final boolean oneway;
	public final long id;
	public final String name;

	public Way(long _id, boolean _oneway, String _n) {
		id = _id;
		oneway = _oneway;
		name = _n;
	}

	public boolean containsNode(int nid) {
		for (GeoNode n : this) {
			if (n.id == nid) {
				return true;
			}
		}
		return false;
	}
}
