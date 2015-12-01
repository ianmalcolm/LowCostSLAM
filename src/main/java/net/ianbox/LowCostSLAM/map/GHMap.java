package net.ianbox.LowCostSLAM.map;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndex;

import org.apache.log4j.Logger;

public class GHMap {

	GraphHopper hopper = new GraphHopper();
	GraphHopperStorage graph = null;
	LocationIndex index = null;
	private static final CarFlagEncoder encoder = new CarFlagEncoder(5, 5, 1);
	private static final Logger log = Logger.getLogger(GHMap.class.getName());

	public GHMap() {

		hopper.setGraphHopperLocation("graphFolder");
		hopper.setEncodingManager(new EncodingManager(encoder));
		hopper.setCHEnable(false);
		hopper.importOrLoad();

		graph = hopper.getGraphHopperStorage();
		index = hopper.getLocationIndex();

	}

}
