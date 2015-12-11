package net.ianbox.LowCostSLAM.map;

import java.util.LinkedList;
import java.util.List;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;

import org.apache.log4j.Logger;

public class GHMap implements Map{

	private final GraphHopper hopper = new GraphHopper().forDesktop();
	private final GraphHopperStorage graph;
	private final LocationIndexXSearch index;
	public static final CarFlagEncoder ENCODER = new CarFlagEncoder(5, 5, 1);
	private static final Logger log = Logger.getLogger(GHMap.class.getName());

	private static final GeoCalcTools tools = new GeoCalcTools();

	public GHMap(String osmfile) {

		log.trace("Initializing GraphHpooer map...");
		hopper.setOSMFile(osmfile);
		hopper.setGraphHopperLocation("graphFolder");
		hopper.setEncodingManager(new EncodingManager(ENCODER));
		hopper.setCHEnable(false);
		hopper.importOrLoad();

		graph = hopper.getGraphHopperStorage();

		index = new LocationIndexXSearch(graph,
				(LocationIndexTree) hopper.getLocationIndex());
	}

	public List<Edge> enumerateEdges(GHPoint p, double range) {
		List<QueryResult> qrs = index.rangeSearch(p.lat, p.lon, range,
				EdgeFilter.ALL_EDGES);
		List<Edge> wes = new LinkedList<Edge>();
		for (QueryResult qr : qrs) {
			EdgeIteratorState eis = qr.getClosestEdge();

			int waySize = eis.fetchWayGeometry(3).getSize();

			if (eis.isForward(ENCODER)) {
				for (int i = 0; i < waySize - 1; i++) {
					SimpleEdge edge = new SimpleEdge(eis, i);
					double dist = calcPointToEdgeMinDist(p, edge);
					if (dist < range) {
						wes.add(edge);
					}
				}
			}

			if (eis.isBackward(ENCODER)) {
				eis = eis.detach(true);
				for (int i = 0; i < waySize - 1; i++) {
					SimpleEdge edge = new SimpleEdge(eis, i);
					double dist = calcPointToEdgeMinDist(p, edge);
					if (dist < range) {
						wes.add(edge);
					}
				}
			}
		}
		return wes;
	}

	public WeightedEdge createWeightedEdge(Edge edge) {
		double length = calcEdgeLength(edge);
		SimpleWeightedEdge swe = new SimpleWeightedEdge(edge, length);
		return swe;
	}

	private double calcPointToEdgeMinDist(GHPoint p, Edge edge) {
		final int wayId = edge.getWayId();
		final int adjId = edge.getAdjNodeId();
		GHPoint a = getNode(wayId, adjId, edge.getStartNodeId());
		GHPoint b = getNode(wayId, adjId, edge.getEndNodeId());

		if (tools.validEdgeDistance(p.lat, p.lon, a.lat, a.lon, b.lat, b.lon)) {
			double normalizedDist = tools.calcNormalizedEdgeDistance(p.lat,
					p.lon, a.lat, a.lon, b.lat, b.lon);
			double dist = tools.calcDenormalizedDist(normalizedDist);
			return dist;
		} else {
			double normDist2a = tools.calcNormalizedDist(p.lat, p.lon, a.lat,
					a.lon);
			double normDist2b = tools.calcNormalizedDist(p.lat, p.lon, b.lat,
					b.lon);
			if (normDist2a > normDist2b) {
				double distb = tools.calcDenormalizedDist(normDist2b);
				return distb;
			} else {
				double dista = tools.calcDenormalizedDist(normDist2a);
				return dista;
			}
		}
	}

	public double calcEdgeLength(Edge edge) {
		final int wayId = edge.getWayId();
		final int adjId = edge.getAdjNodeId();
		GHPoint a = getNode(wayId, adjId, edge.getStartNodeId());
		GHPoint b = getNode(wayId, adjId, edge.getEndNodeId());

		return tools.calcDist(a.lat, a.lon, b.lat, b.lon);
	}

	public GHPoint getPosition(final GHPoint a, final GHPoint b, double r) {
		return tools.calcPointOnEdge(a, b, r);
	}

	public static double calcDist(GHPoint p, GHPoint q) {
		return tools.calcDist(p.lat, p.lon, q.lat, q.lon);
	}

	public GHPoint getNode(final int wayId, final int adjId, final int edgeId) {

		EdgeIteratorState eis = graph.getEdgeIteratorState(wayId, adjId);
		PointList pointList = eis.fetchWayGeometry(3);
		double lat = pointList.getLat(edgeId);
		double lon = pointList.getLon(edgeId);

		return new GHPoint(lat, lon);

	}

	public GHPoint getPosition(PointOnEdge poe) {
		final int wayId = poe.getWayId();
		final int adjId = poe.getAdjNodeId();
		GHPoint a = getNode(wayId, adjId, poe.getStartNodeId());
		GHPoint b = getNode(wayId, adjId, poe.getEndNodeId());

		double dist = poe.getDist();
		return getPosition(a, b, dist);
	}
}
