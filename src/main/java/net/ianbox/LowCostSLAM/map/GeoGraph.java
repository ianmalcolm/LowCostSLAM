package net.ianbox.LowCostSLAM.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

public class GeoGraph extends ListenableDirectedWeightedGraph<GeoNode, GeoEdge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1569575475670431456L;

	public GeoGraph(String filename) {
		super(GeoEdge.class);

		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = (Document) builder.build(filename);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Map<Long, GeoNode> nodes = getNodes(doc);
		List<Way> ways = getWays(doc, nodes);

		for (Way w : ways) {
			for (GeoNode n : w) {
				if (!containsVertex(n)) {
					addVertex(n);
				}
			}
			GeoNode pren = w.getFirst();
			for (GeoNode curn : w) {
				if (pren == curn) {
					continue;
				}
				addEdge(pren, curn);
				double dist = pren.dist(curn);
				setEdgeWeight(getEdge(pren, curn), dist);
				getEdge(pren, curn).setLabel(w.name);
				if (!w.oneway) {
					addEdge(curn, pren);
					setEdgeWeight(getEdge(curn, pren), dist);
					getEdge(curn, pren).setLabel(w.name);
				}
				pren = curn;
			}
		}

	}

	private static Map<Long, GeoNode> getNodes(Document doc) {
		Map<Long, GeoNode> nList = new HashMap<Long, GeoNode>();

		XPathFactory xFactory = XPathFactory.instance();
		XPathExpression<Element> exprElement = xFactory.compile(
				"/osm/node[@id and @lat and @lon]", Filters.element());
		List<Element> nodesEle = exprElement.evaluate(doc);
		for (Element nele : nodesEle) {

			GeoNode n = null;
			try {
				long id = nele.getAttribute("id").getLongValue();
				double lat = nele.getAttribute("lat").getDoubleValue();
				double lon = nele.getAttribute("lon").getDoubleValue();
				Attribute ele = nele.getAttribute("ele");
				n = new GeoNode(id, lat, lon, ele == null ? Double.NaN
						: ele.getDoubleValue());
			} catch (DataConversionException e) {
				e.printStackTrace();
			}
			nList.put(n.id, n);
		}

		return nList;
	}

	private static List<Way> getWays(Document doc, Map<Long, GeoNode> nodes) {
		List<Way> ways = new LinkedList<Way>();

		XPathFactory xFactory = XPathFactory.instance();
		XPathExpression<Element> expway = xFactory.compile(
				"/osm/way[tag/@k='highway']", Filters.element());
		XPathExpression<Element> exponeway = xFactory.compile(
				"tag[@k='oneway' and tag/@v='yes']", Filters.element());
		XPathExpression<Attribute> expnd = xFactory.compile("nd/@ref",
				Filters.attribute());
		XPathExpression<Attribute> expname = xFactory.compile("/@name",
				Filters.attribute());

		List<Element> waysEle = expway.evaluate(doc);
		for (Element wayEle : waysEle) {

			try {
				Element oneway = exponeway.evaluateFirst(wayEle);
				Attribute name = expname.evaluateFirst(wayEle);
				long wayId = wayEle.getAttribute("id").getLongValue();
				List<Attribute> ndAttrs = expnd.evaluate(wayEle);
				List<GeoNode> _ns = new LinkedList<GeoNode>();
				for (Attribute ndAttr : ndAttrs) {
					GeoNode _n = nodes.get(ndAttr.getLongValue());
					_ns.add(_n);
				}
				Way way = new Way(wayId, oneway != null,
						name != null ? name.getValue() : "");
				way.addAll(_ns);
				ways.add(way);
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return ways;

	}

}
