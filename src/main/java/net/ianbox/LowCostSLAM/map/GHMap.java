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

	public List<WeightedEdge> rangeSearch(GHPoint p, double range) {
		List<QueryResult> qrs = index.rangeSearch(p.lat, p.lon, range,
				EdgeFilter.ALL_EDGES);
		List<WeightedEdge> wes = new LinkedList<WeightedEdge>();
		for (QueryResult qr : qrs) {
			double w = calcEdgeLength(qr.getClosestEdge());
			wes.add(new SimpleWeightedEdge(qr.getClosestEdge(), w));
		}
		return wes;
	}

	@SuppressWarnings("unused")
	private double calcEdgeLength(int id) {
		EdgeIteratorState e = getEdge(id);
		return calcEdgeLength(e);
	}

	private double calcEdgeLength(EdgeIteratorState e) {
		int a = e.getBaseNode();
		int b = e.getAdjNode();
		return calcEdgeLength(a, b);
	}

	private double calcEdgeLength(int a, int b) {
		double a_lat = na.getLat(a);
		double a_lon = na.getLon(a);
		double b_lat = na.getLat(b);
		double b_lon = na.getLon(b);
		return tools.calcDist(a_lat, a_lon, b_lat, b_lon);
	}

	public GHPoint getPosition(int a, int b, double r) {
		GHPoint pa = getNode(a);
		GHPoint pb = getNode(b);

		return tools.calcPointOnEdge(pa, pb, r);
	}

	public static double calcDist(GHPoint p, GHPoint q) {
		return tools.calcDist(p.lat, p.lon, q.lat, q.lon);
	}

	public GHPoint getNode(int id) {
		double id_lat = na.getLat(id);
		double id_lon = na.getLon(id);
		return new GHPoint(id_lat, id_lon);
	}

	public EdgeIteratorState getEdge(int id) {
		return graph.getEdgeIteratorState(id, -1);
	}

	public GHPoint getPosition(PointOnEdge poe) {
		int base = poe.getBaseNode();
		int adj = poe.getAdjNode();
		double dist = poe.getDist();
		return getPosition(base, adj, dist);
	}
}
