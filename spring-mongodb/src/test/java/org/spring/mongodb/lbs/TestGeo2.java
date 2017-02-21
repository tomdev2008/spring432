//package org.spring.mongodb.lbs;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.geo.Circle;
//import org.springframework.data.geo.Distance;
//import org.springframework.data.geo.GeoResults;
//import org.springframework.data.geo.Metrics;
//import org.springframework.data.geo.Point;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
//import org.springframework.data.mongodb.core.index.GeospatialIndex;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.NearQuery;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
//public class TestGeo2 extends AbstractJUnit4SpringContextTests {
//	
//	@Autowired
//	private MongoTemplate m;
//
//	@Test //初始化
//	public void init() {
//		
//		m.save(new Location("中俊大厦", 116.328152,40.109942));
//		m.save(new Location("小7路", 116.327721,40.109155));
//		m.save(new Location("皖江公寓", 116.329688,40.108455));
//		m.save(new Location("五合公寓", 116.328952,40.108214));
//		m.save(new Location("金飞红木", 116.33038,40.10749));
//		m.save(new Location("未知路", 116.331162,40.109035));
//		m.save(new Location("朱辛庄地铁站", 116.319771,40.10999));
//		
//		//创建索引
//		m.indexOps(Location.class).ensureIndex(new GeospatialIndex("position").typed(GeoSpatialIndexType.GEO_2DSPHERE));
//	}
//
//	@Test
//	public void test1() {
//
//		Circle circle = new Circle(116.329688,40.108455, 0.01);
//		List<Location> list = m.find(new Query(Criteria.where("location").within(circle)).limit(5), Location.class);
//		for(Location l:list){
//			System.out.println(l);
//		}
//	}
//
//	@Test
//	public void test2() {
//		Point location = new Point(118.783799, 31.979234);
//		NearQuery query = NearQuery.near(location)
//				.maxDistance(new Distance(Integer.MAX_VALUE, Metrics.KILOMETERS))
//				.spherical(true)
//				.num(3);
//
//		GeoResults<Location> list= m.geoNear(query, Location.class,"mapinfo");
//		System.out.println(list);
//	}
//}
