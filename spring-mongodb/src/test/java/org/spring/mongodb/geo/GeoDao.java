package org.spring.mongodb.geo;

import java.util.List;

import org.spring.mongodb.lbs.Loc;
import org.spring.mongodb.lbs.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
public class GeoDao extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private MongoTemplate m;

	public void initIndex() {
		m.indexOps(Location.class)
		.ensureIndex(new GeospatialIndex("loc.coordinates")
		.typed(GeoSpatialIndexType.GEO_2DSPHERE));
	}
	
	public void add(){
		m.save(new Location("中俊大厦", new Loc(new double[]{116.328152,40.109942})));
	}

	public void nearQuery(double lng, double lat,double radius) {
		Point location = new Point(lng,lat);
		NearQuery query = NearQuery.near(location)
				.maxDistance(new Distance(Integer.MAX_VALUE, Metrics.KILOMETERS))
				.spherical(true)
				.num(10);

		GeoResults<Location> result= m.geoNear(query, Location.class,"location");
		
		System.out.println("====================================");
		//System.out.println(result.getAverageDistance());
		List<GeoResult<Location>> list = result.getContent();
		for(GeoResult<Location> t:list){
			System.out.println(t.getDistance()+"\t"+t.getContent());
		}
		System.out.println("====================================");
	}
}
