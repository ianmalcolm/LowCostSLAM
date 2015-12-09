package net.ianbox.LowCostSLAM.map;

import java.util.LinkedList;
import java.util.List;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;

import org.apache.log4j.Logger;

public class GHMap {

	private final GraphHopper hopper = new GraphHopper().forDesktop();
	private final GraphHopperStorage graph;
	private final NodeAccess na;
	private final LocationIndexXSearch index;
	private static final CarFlagEncoder encoder = new CarFlagEncoder(5, 5, 1);
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(GHMap.class.getName());

	private static final GeoCalcTools tools = new GeoCalcTools();

	public GHMap(String osmfile) {

		hopper.setOSMFile(osmfile);
		hopper.setGraphHopperLocation("graphFolder");
		hopper.setEncodingManager(new EncodingManager(encoder));
		hopper.setCHEnable(false);
		hopper.importOrLoad();

		graph = hopper.getGraphHopperStorage();

		na = graph.getNodeAccess();
		index = new LocationIndexXSearch(graph,
				(LocationIndexTree) hopper.getLocationIndex());
	}

	public List<QueryResult> findkClosest(GHPoint p, int k) {
		List<QueryResult> qrs = index.findkClosest(p.lat, p.lon, k,
				EdgeFilter.ALL_EDGES);
		return qrs;
	}

	public List<? extends WeightedEdge> rangeSearch(GHPoint p, double range) {
		List<QueryResult> qrs = index.rangeSearch(p.lat, p.lon, range,
				EdgeFilter.ALL_EDGES);
		List<SimpleWeightedEdge> wes = new LinkedList<SimpleWeightedEdge>();
		for (QueryResult qr : qrs) {
			double w = calcEdgeLength(new SimpleWeightedEdge(qr));
			wes.add(new SimpleWeightedEdge(qr, w));
		}
		return wes;
	}

	private double calcEdgeLength(Edge edge) {
		int wayId = edge.getWayId();
		int edgeId = edge.getEdgeId();

		GHPoint a = getNode(wayId, edgeId);
		GHPoint b = getNode(wayId, edgeId + 1);

		return tools.calcDist(a.lat, a.lon, b.lat, b.lon);
	}

	public GHPoint getPosition(final GHPoint a, final GHPoint b, double r) {
		return tools.calcPointOnEdge(a, b, r);
	}

	public static double calcDist(GHPoint p, GHPoint q) {
		return tools.calcDist(p.lat, p.lon, q.lat, q.lon);
	}

	public GHPoint getTowerNode(int id) {
		double id_lat = na.getLat(id);
		double id_lon = na.getLon(id);
		return new GHPoint(id_lat, id_lon);
	}

	public GHPoint getNode(int w, int e) {

		EdgeIteratorState eis = graph.getEdgeIteratorState(w, Integer.MIN_VALUE);
		PointList pointList = eis.fetchWayGeometry(3);
		double lat = pointList.getLat(e);
		double lon = pointList.getLon(e);

		return new GHPoint(lat, lon);

	}

	public GHPoint getPosition(PointOnEdge poe) {
		int wayId = poe.getWayId();
		int edgeId = poe.getEdgeId();

		GHPoint a = getNode(wayId, edgeId);
		GHPoint b = getNode(wayId, edgeId + 1);

		double dist = poe.getDist();
		return getPosition(a, b, dist);
	}
}
