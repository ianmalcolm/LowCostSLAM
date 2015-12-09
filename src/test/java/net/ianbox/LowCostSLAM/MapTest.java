package net.ianbox.LowCostSLAM;

import net.ianbox.LowCostSLAM.map.GHMap;

import org.junit.Test;

public class MapTest {

	@Test
	public void GHMapTest() {
		GHMap ghmap = new GHMap("map/malaysia-singapore-brunei-latest.osm.pbf");
		System.out.println(ghmap.toString());
	}
}
